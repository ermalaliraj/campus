import { __, T } from 'config/i18n'

export const AuthorizedRoutes = {}

export const getPublicRoutes = () => {
  return {
    '/': { component: require('pages/Dashboard').default, breadcrumbName: __(T.buttons.dashboard) },
    '/companies': {
      component: require('pages/companies/CompaniesReport').default,
      breadcrumbName: __(T.buttons.company_management),
    }
  }
}

export type Route = {
  component: React.ComponentClass | React.FC
  breadcrumbName?: string
}

const PublicRoutes = getPublicRoutes()
export type routes = keyof typeof AuthorizedRoutes | keyof typeof PublicRoutes

export const getRoutesMap = (): RoutesMap => {
  return { ...getPublicRoutes(), ...AuthorizedRoutes }
}

export type RoutesMap = Record<routes, Route>
