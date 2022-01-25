import qs from 'qs'
import { create, ApiResponse, ApiErrorResponse, ApisauceInstance } from 'apisauce'
import { Unauthorized, NotFound } from 'errors'
import ApiError from 'errors/ApiError'
import { showError } from 'helpers/Notifications'
import { __, T } from 'config/i18n'


export const paramsSerializer = (params: any) =>
  qs.stringify(params, {
    arrayFormat: 'repeat',
    serializeDate: Date => Date.getTime().toString(),
  })

let api: ApisauceInstance

if (api! === undefined) {
  let backendUrl = '/'

  api = create({
    baseURL: backendUrl,
    timeout: 10000,
    headers: {
      Accept: 'application/json',
      'Content-Type': 'application/json',
    },
    paramsSerializer,
  })
}

api.axiosInstance.interceptors.response.use(
  response => response,
  error => {
    // if login page responds with 401
    if (error.request.responseURL.includes('/login')) {
      return Promise.reject(error)
    } else if (error.response.status === 401) {
      // alert('not authorized')
    } else {
      return Promise.reject(error)
    }
  }
)

export const responseErrorCheck = <T = any>(res: ApiResponse<T, any>) => {
  if (res && res.status === 0) {
    showError('No internet connection', undefined)
    if (!res.ok) {
      throw new ApiError(res as ApiErrorResponse<any>)
    } else {
      throw new Error('No internet connection')
    }
  }

  if (res && (res.status === 200 || res.status === 204)) {
    return res.data as T
  }

  if (res.status === 403) {
    showError(String(res.status), __(T.error.unauthorized))
    if (!res.ok) {
      throw new Unauthorized(res as ApiErrorResponse<any>)
    } else {
      throw new Error(__(T.error.unauthorized))
    }
  }

  if (res.status === 404) {
    showError(String(res.status), __(T.error.not_found))
    if (!res.ok) {
      throw new NotFound(res as ApiErrorResponse<any>)
    } else {
      throw new Error(__(T.error.not_found))
    }
  }

  if (res.data && res.data.error?.message) {
    showError(res.data.error.message, undefined)
    if (!res.ok) {
      throw new ApiError(res as ApiErrorResponse<any>)
    }
  }

  if (!res || !res.data) {
    showError(__(T.error.generic_be_error), undefined)
    if (!res.ok) {
      throw new ApiError(res as ApiErrorResponse<any>)
    } else {
      throw new Error(__(T.error.generic_be_error))
    }
  }

  if (res.problem) {
    showError(res.data?.message || res.problem, undefined)
    if (!res.ok) {
      throw new ApiError(res as ApiErrorResponse<any>)
    }
  }
}

export default api
