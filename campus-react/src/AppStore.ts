import { changeLanguage } from 'config/i18n'
import Storage from 'helpers/Storage'
import { Auth } from 'api'
import { User } from 'api/types'

type AppLanguage = 'it' | 'en'

class AppStore {
  collapsedBarMenu: boolean = false
  language: AppLanguage = 'it'
  loggedUser?: User
  goToDashboard?: boolean

  async loadInitalData() {
      try {
        this.loggedUser = await Auth.loggedUser()
      } catch (error) {
        // this.keyCloak.logout()
        console.log(error.message)
      }
    // }
    await this.loadLanguage()
    await this.loadGoToDashboard()
    this.collapsedBarMenu = await this.loadCollapsedMenuBar()
  }

  async setCollapsedMenuBar(collapsed: boolean) {
    this.collapsedBarMenu = collapsed
    await Storage.save('collapsedBarMenu', collapsed)
  }

  async loadCollapsedMenuBar() {
    return await Storage.load('collapsedBarMenu', false)
  }

  async setLanguage(lang: AppLanguage) {
    this.language = lang
    await Storage.save('language', lang)
    changeLanguage(lang)
  }

  async loadLanguage() {
    this.language = await Storage.load('language', 'it')
    changeLanguage(this.language)
    return this.language
  }

  async setGoToDashboard(goToDashboard: boolean) {
    this.goToDashboard = goToDashboard
    await Storage.save('goToDashboard', goToDashboard)
  }

  async loadGoToDashboard() {
    this.goToDashboard = await Storage.load('goToDashboard', false)
    return this.goToDashboard
  }
}

export default new AppStore()
