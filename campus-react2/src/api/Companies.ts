import {Company, Page, QueryParams} from 'api/types'
import {AxiosRequestConfig} from 'axios'
import api, {responseErrorCheck} from './api'

class Companies<T = Company> {
	protected endpoint = '/companies'

	async findAll(axiosConfig?: AxiosRequestConfig) {
		const res = await api.get<Page<T>>(`${this.endpoint}`, axiosConfig)
		return responseErrorCheck(res)
	}

	async get(id: string, axiosConfig?: AxiosRequestConfig) {
		const res = await api.get<T>(`${this.endpoint}/${id}`, axiosConfig)
		return responseErrorCheck(res)
	}

	async search(params: QueryParams, axiosConfig?: AxiosRequestConfig) {
		const res = await api.get<Page<T>>(`${this.endpoint}`, params, axiosConfig)
		return responseErrorCheck(res)
	}

	getEndpoint() {
		return this.endpoint
	}

	getAutocompleteQueryParam() {
		return 'fullText'
	}
}

const companies = new Companies()
export {companies as default}
