import i18n, { TOptions } from 'i18next'
import { initReactI18next } from 'react-i18next'

import en from './translations/en'
import it from './translations/it'

const resources = {
  en: { translation: en },
  it: { translation: it },
}

i18n
  .use(initReactI18next) // passes i18n down to react-i18next
  .init({
    resources: resources,
    lng: 'en',
    fallbackLng: 'en',

    interpolation: {
      escapeValue: false,
    },
  })

// languages auto-completion
interface TranslationCategory {
  [key: string]: string
}

let TRANSLATION_MAP = en

// replace object values with keys
Object.keys(en).forEach((catKey, idx) => {
  const subCat: TranslationCategory = {}
  // @ts-ignore
  Object.keys(en[catKey]).forEach(key => {
    subCat[key] = catKey + '.' + key
  })

  if (idx === 0) {
    // @ts-ignore
    TRANSLATION_MAP = {}
  }

  // @ts-ignore
  TRANSLATION_MAP[catKey] = subCat
})
export { TRANSLATION_MAP as T }
// END languages auto-completion

export const __ = (key: string, interpolationMap?: TOptions) => i18n.t(key, interpolationMap)

export const __UP = (key: string, interpolationMap?: TOptions) => i18n.t(key, interpolationMap).toUpperCase()

export const changeLanguage = (locale: string) => {
  i18n.changeLanguage(locale)
}

export default i18n
