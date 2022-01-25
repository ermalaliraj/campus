import { __, T } from 'config/i18n'
import ApiError from './ApiError'

class Unauthorized extends ApiError {
  statusCode = 401
  message = __(T.errors.unauthorized)
}

export default Unauthorized
