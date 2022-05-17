import React from 'react'
import {Layout, Text, View} from 'components'
import {navigate} from 'router'
import {__, T} from 'config/i18n'
import {Card} from 'antd'
// import AppStore from 'AppStore'

const Dashboard: React.FC = () => {
  // AppStore.setGoToDashboard(false)

  return (
    <Layout>
      <View
        style={{
          flexDirection: 'row',
          flexWrap: 'wrap',
          justifyContent: 'flex-start',
          flex: 1,
          display: 'flex',
          marginBottom: 50,
          marginRight: 30,
        }}
      >

        <Card hoverable style={c.card} onClick={() => navigate('/companies')}>
          <img src={require('assets/images/company_config.svg')} alt={__(T.buttons.confirm)} style={c.image}/>
          <Text style={c.text}>{__(T.buttons.confirm)}</Text>
        </Card>

        <View style={c.emptyCard}/>
        <View style={c.emptyCard}/>
      </View>
    </Layout>
  )
}

export default Dashboard

const c: Record<string, React.CSSProperties> = {
  card: {
    margin: 30,
    display: 'flex',
    borderRadius: '5px',
    boxShadow: '0px 0px 10px rgba(0, 0, 0, 0.1)',
    justifyContent: 'center',
    flex: 1,
    minWidth: 400,
    height: 300,
    alignItems: 'center',
    marginBottom: 0,
    marginRight: 0,
  },
  emptyCard: {
    flex: 1,
    margin: 15,
    minWidth: 400,
  },
  text: {
    fontSize: 30,
    fontWeight: 600,
    color: '#000000',
  },
  image: {
    width: 95,
    height: 95,
    display: 'block',
    marginLeft: 'auto',
    marginRight: 'auto',
  },
}
