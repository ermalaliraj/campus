import React from 'react'
// import { Layout, AdvancedForm, View, Text } from 'components'
import { Layout, View, Text } from 'components'
// import { Schema } from 'components/AdvancedForm'
import { __, T } from 'config/i18n'
import { Table, Space, Button, Tabs } from 'antd'
import { navigate } from 'router'
import { Company } from 'api/types'
import { PaginationHelper } from 'PaginationHelper'
import { Companies, HardCoded, Users } from 'api'
import { describeCompanyType, describeCategory } from 'helpers/describer'
import api from 'api/api'
import moment from 'moment'

interface Props {}
interface State {
	filteredCompanies: Company[]
  activeTabKey: '1' | '2' | '3' | '4'
  paginationHelper: PaginationHelper
  filters: any
}

export default class CompaniesReport extends React.Component<Props, State> {
  state: State = {
		filteredCompanies: [],
    activeTabKey: '1',
    paginationHelper: new PaginationHelper(5),
    filters: {},
  }

  async componentDidMount() {
    try {
      await this.applyFilters()
    } catch (error) {
      console.log(error.message)
    }
  }

  async applyFilters(filters: {} = {}, page?: number, tab?: '1' | '2' | '3' | '4') {
    this.state.paginationHelper.setCurrentPage(page ?? 1)
    let params = {}
    if (filters['free_search'] && filters['free_search'] !== '') {
      params['fullText'] = filters['free_search']
    }
    if (filters['type'] && filters['type'] !== '') {
      params['companyType'] = filters['type']
    }
    const updatedTab = tab ?? this.state.activeTabKey
    try {
      if (updatedTab === '1' || updatedTab === '4') {
        params['enabled'] = updatedTab === '4' ? false : true
      }
      if (updatedTab === '2') {
        params['expiring'] = true
      }
      if (updatedTab === '3') {
        params['expired'] = true
      }
      const companiesPage = await Companies.search({
        ...this.state.paginationHelper.getPaginationApiParams(),
        ...params,
        sort: 'creationDate,desc',
      })
      this.state.paginationHelper.setTotalElements(companiesPage?.totalElements ?? 0)

      this.setState({
        filteredCompanies: companiesPage?.content ?? [],
        activeTabKey: updatedTab,
        filters,
      })
    } catch (error) {
      console.log(error.message)
      this.setState({
        filteredCompanies: [],
        activeTabKey: updatedTab,
        filters,
      })
    }
  }

  onTabChangeFilter = async (activeKey: string) => {
    if (activeKey === '1' || activeKey === '2' || activeKey === '3' || activeKey === '4') {
      await this.applyFilters(this.state.filters, 1, activeKey)
    }
  }

  render() {
    const { filteredCompanies, activeTabKey } = this.state

    const columns = [
      {
        title: __(T.fields.email),
        dataIndex: 'name',
        render: (text, record: Company) => <Text>{record?.name}</Text>,
      },
      {
        title: __(T.fields.phone),
        dataIndex: 'phoneNumber',
        render: (text, record: Company) => <Text>{record?.address}</Text>,
      },
      {
        title: __(T.fields.email),
        dataIndex: 'email',
        render: (text, record: Company) => <Text>{record?.address}</Text>,
      }
    ]

    const pagination = {
      onChange: async page => {
        await this.applyFilters(this.state.filters, page)
      },
      total: this.state.paginationHelper.totalElements,
      current: this.state.paginationHelper.currentPage,
      pageSize: this.state.paginationHelper.pageSize,
      showSizeChanger: false,
    }
    const scroll: { x: true } = { x: true }

    return (
      <Layout>
        <View style={{ padding: 20 }}>
          <View className="filter-box">
          </View>

          <View className="page-table">
            <Tabs defaultActiveKey={this.state.activeTabKey} onChange={this.onTabChangeFilter}>
              <Tabs.TabPane
                tab={
                  activeTabKey === '1'
                    ? `${__(T.active)} (${this.state.paginationHelper.totalElements})`
                    : __(T.active)
                }
                key="1"
              >
                <Table
                  columns={columns}
                  dataSource={filteredCompanies}
                  bordered
                  pagination={pagination}
                  scroll={scroll}
                />
              </Tabs.TabPane>

              <Tabs.TabPane
                tab={
                  activeTabKey === '2'
                    ? `${__(T.disabled)} (${this.state.paginationHelper.totalElements})`
                    : __(T.disabled)
                }
                key="2"
              >
                <Table
                  columns={columns}
                  dataSource={filteredCompanies}
                  bordered
                  pagination={pagination}
                  scroll={scroll}
                />
              </Tabs.TabPane>


              <Tabs.TabPane
                tab={
                  activeTabKey === '4'
                    ? `${__(T.disabled)} (${this.state.paginationHelper.totalElements})`
                    : __(T.disabled)
                }
                key="4"
              >
                <Table
                  columns={columns}
                  dataSource={filteredCompanies}
                  bordered
                  pagination={pagination}
                  scroll={scroll}
                />
              </Tabs.TabPane>
            </Tabs>
          </View>
        </View>
      </Layout>
    )
  }
}
