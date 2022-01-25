import { describeCategory } from 'helpers/describer'
import { category, CompanyFields, UserFields } from 'types'

class HardCoded {
  getCategoryKeys(): category[] {
    return ['A', 'B', 'C']
  }

  getCategoryOptions() {
    return this.getCategoryKeys().map(value => {
      return { label: describeCategory(value), value: value }
    })
  }


  getCompany(): CompanyFields {
    return {
      name: 'aaa',
      address: '4334'
    }
  }

  getUser(): UserFields {
    return {
      name: 'Ermal ',
      surname: 'Aliraj',
      email: 'ermal.aliraj@aaa.com',
      phone: '333',
    }
  }
}

const hardCoded = new HardCoded()
export { hardCoded as default }
