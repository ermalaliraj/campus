import {notification} from 'antd'

type NotificationType = 'success' | 'error' | 'info' | 'warning'
export const showNotification = (
  title: string,
  message?: string,
  type: NotificationType = 'success',
  duration?: number
) => {
  notification[type]({
    message: title,
    description: message,
    duration: duration ?? 4
  })
}

export const showError = (title: string, message?: string) => {
  notification['error']({
    message: title,
    description: message,
  })
}
