import { __, T } from 'config/i18n'
import ApiError from './ApiError'

class NotFound extends ApiError {
  statusCode = 404
  message = __(T.error.not_found)
}

export default NotFound
