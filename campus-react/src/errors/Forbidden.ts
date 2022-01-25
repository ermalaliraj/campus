import {__, T} from 'config/i18n'

class Forbidden extends Error {
	statusCode = 403
	message = __(T.error.forbidden)
}

export default Forbidden
