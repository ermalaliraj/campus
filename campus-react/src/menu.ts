import {routes} from 'routes'
import {__, T} from 'config/i18n'

export interface MenuItem {
  label: string
  icon?: string
  routePath?: routes
  submenu?: MenuItem[]
  onClick?: () => void
  customRender?: () => void
}

export const getMenuItems = () => {
  const menu: MenuItem[] = []
  menu.push({label: __(T.buttons.dashboard), icon: 'icon', routePath: '/'})
  // menu.push({label: __(T.buttons.company_management), icon: 'icon', routePath: '/company'})
  menu.push({label: __(T.buttons.companies_management), icon: 'icon', routePath: '/companies'})

  return menu
}
