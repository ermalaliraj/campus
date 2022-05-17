import React from 'react'
// import { Layout, AdvancedForm, View, Text } from 'components'
// import { Layout, View, Text } from 'components'
// import { Schema } from 'components/AdvancedForm'
// import { __, T } from 'config/i18n'
import { Table, Space, Button, Tabs } from 'antd'
import { navigate } from 'router'
// import { Company } from 'api/types'
import { PaginationHelper } from 'PaginationHelper'
// import { Companies, HardCoded, Users } from 'api'
// import { describeCompanyType, describeCategory } from 'helpers/describer'
// import api from 'api/api'
import moment from 'moment'

interface Props {}
interface State {
	// filteredCompanies: Company[]
  activeTabKey: '1' | '2' | '3' | '4'
  paginationHelper: PaginationHelper
  filters: any
}

export default class CompaniesReport extends React.Component<Props, State> {
  state: State = {
		// filteredCompanies: [],
    activeTabKey: '1',
    paginationHelper: new PaginationHelper(5),
    filters: {},
  }

  render() {

    return (
     <>
       CompaniesReport
     </>
    )
  }
}
