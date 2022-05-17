import { User } from 'api/types'
import { AxiosRequestConfig } from 'axios'
import api, { responseErrorCheck } from './api'

class Auth {
  protected endpoint = '/auth'

  setHeaderAccessToken(token: string) {
    return api.setHeaders({
      // set headers to 'api' directly
      Accept: 'application/json',
      'Content-Type': 'application/json',
      Authorization: `Bearer ${token}`,
    })
  }

  async loggedUser(axiosConfig?: AxiosRequestConfig) {
    const res = await api.get<User>(`${this.endpoint}/loggedUser`, axiosConfig)
    return responseErrorCheck(res)
  }
}

const auth = new Auth()
export { auth as default }
