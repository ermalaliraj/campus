import React from 'react'
import {Route, Router, Switch} from 'react-router-dom'
import {history} from 'router'
import {getPublicRoutes} from 'routes'
import 'assets/css/antd-custom.less'
import 'assets/css/app.less'

const App: React.FC = () => {

	return (
		<div className="container">hgrhrthrthtr
			<>
				<Router history={history}>
					<>
						<Switch>
							{Object.entries<{ component: any }>(getPublicRoutes()).map(([path, route]) => (
								<Route path={path} component={route.component} key={path} exact={true}/>
							))}
						</Switch>
					</>
				</Router>
			</>
		</div>
	)
}

export default App
