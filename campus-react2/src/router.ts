import { createBrowserHistory } from 'history'
import { stringify, parse } from 'qs'
import { routes } from './routes'

interface RouteParams {
  [key: string]: any
}

interface NavigationOptions {
  replace?: boolean
  props?: { [key: string]: any }
}

// navigation history
export const history = createBrowserHistory()

export function navigate(_path: routes, routeParams?: RouteParams, options?: NavigationOptions): void {
  // add query params to path
  const route = routeParams ? replaceMatchParams(_path, routeParams) : _path
  const stringParams = routeParams ? stringify(routeParams, { arrayFormat: 'repeat' }) : ''
  const path = `${route}${stringParams ? `?${stringParams}` : ''}`
  const props = options ? options.props : undefined

  options && options.replace ? history.replace(path, props) : history.push(path, props)
}

export function navigateBack(): void {
  history.goBack()
}

export function refresh(queryParams?: RouteParams, options?: NavigationOptions) {
  navigate(history.location.pathname as routes, queryParams)
}

export function getMatchParams(props: any) {
  return props.match.params
}

export function getQueryParams(): Record<string, any> {
  const search = history.location.search
  const queryParams = search && search.charAt(0) === '?' ? search.substring(1) : search
  return parse(queryParams)
}

export const replaceMatchParams = (page: routes, params: any) => {
  Object.keys(params).forEach(key => {
    if (page.includes(`:${key}`)) {
      page = page.replace(`:${key}`, params[key]) as routes
      delete params[key]
      //remove params insert
    }
  })
  return page
}

export function setQueryParams(newParams: any) {
  addQueryParams(newParams, true)
}

export function addQueryParams(newParams: object, reset = false): void {
  const search = history.location.search
  const queryParams = reset ? {} : parse(search && search.charAt(0) === '?' ? search.substring(1) : search)
  const params = {
    ...queryParams,
    ...newParams,
  }
  let paramsString = ''
  Object.keys(params).map(key => (paramsString += `${key}=${params[key]}&`))
  if (paramsString.length > 0) paramsString = paramsString.substring(0, paramsString.length - 1)
  const encoded = paramsString ? `?${encodeURI(paramsString)}` : ''
  if (search !== encoded) history.push(encoded)
}
