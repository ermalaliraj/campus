import { User, Page, QueryParams } from 'api/types'
import { AxiosRequestConfig } from 'axios'
import api, { responseErrorCheck } from './api'

class Users<T = User> {
  protected endpoint = '/users'
  // protected endpoint = './mock/users.json'

  async findAll(axiosConfig?: AxiosRequestConfig) {
    const res = await api.get<Page<T>>(`${this.endpoint}`, axiosConfig)
    return responseErrorCheck(res)
  }

  async select(input: string, axiosConfig?: AxiosRequestConfig) {
    const res = await api.get<T[]>(`${this.endpoint}/select?${this.getAutocompleteQueryParam()}=${input}`, axiosConfig)
    return responseErrorCheck(res)
  }

  async get(id: string, axiosConfig?: AxiosRequestConfig) {
    const res = await api.get<T>(`${this.endpoint}/${id}`, axiosConfig)
    return responseErrorCheck(res)
  }

  async update(id: string, user: User, axiosConfig?: AxiosRequestConfig) {
    const res = await api.put<T>(`${this.endpoint}/${id}`, user, axiosConfig)
    return responseErrorCheck(res)
  }

  async search(params: QueryParams, axiosConfig?: AxiosRequestConfig) {
    const res = await api.get<Page<T>>(`${this.endpoint}`, params, axiosConfig)
    return responseErrorCheck(res)
  }

  async enable(id: string, axiosConfig?: AxiosRequestConfig) {
    const res = await api.put<T>(`${this.endpoint}/${id}/enabled`, axiosConfig)
    return responseErrorCheck(res)
  }

  async disable(id: string, axiosConfig?: AxiosRequestConfig) {
    const res = await api.delete<T>(`${this.endpoint}/${id}`, axiosConfig)
    return responseErrorCheck(res)
  }

  async create(data: Partial<T>, axiosConfig?: AxiosRequestConfig) {
    const res = await api.post<T>(`${this.endpoint}`, data, axiosConfig)
    return responseErrorCheck(res)
  }

  getEndpoint() {
    return this.endpoint
  }

  getAutocompleteQueryParam() {
    return 'fullText'
  }
}

const users = new Users()
export { users as default }
