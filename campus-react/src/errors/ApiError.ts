import {ApiErrorResponse} from 'apisauce'

class ApiError extends Error {
	response: ApiErrorResponse<any>
	statusCode?: number

	constructor(response: ApiErrorResponse<any>) {
		super()
		this.response = response
	}
}

export default ApiError
