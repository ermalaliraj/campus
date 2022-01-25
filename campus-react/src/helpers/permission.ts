import { role } from 'types'
import AppStore from 'AppStore'

export const getUserRole = (): role | undefined => {
  return 'admin'
}

export const isUserConfirmed = () => {
  return AppStore.loggedUser?.enabled === true
}

