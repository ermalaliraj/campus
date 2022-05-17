import React, { useState } from 'react'
import { Text, View } from 'components'
import { Menu, Layout, Breadcrumb, Button, Avatar, Dropdown } from 'antd'
import { getMenuItems, MenuItem } from 'menu'
import { navigate } from 'router'
import { useLocation } from 'react-router'
import {
	SettingOutlined,
	QuestionCircleOutlined,
	MenuFoldOutlined,
	MenuUnfoldOutlined,
	DownOutlined,
	UserOutlined,
} from '@ant-design/icons'
import AppStore from 'AppStore'
import { __, T } from 'config/i18n'
import { getRoutesMap, routes } from 'routes'
import { getUserRole, isUserConfirmed } from 'helpers/permission'

const { Header } = Layout

const renderMenuItem = (item: MenuItem) => {
	if (item.submenu) {
		return (
			<Menu.SubMenu
				key={item.label}
				title={
					<span>
            <span>{item.label}</span>
          </span>
				}
			>
				{item.submenu.map(renderMenuItem)}
			</Menu.SubMenu>
		)
	}

	return (
		<Menu.Item key={item.routePath ?? item.label} onClick={item.onClick ?? (() => navigate(item.routePath!))}>
			<SettingOutlined style={{ color: '#d0ff2e' }} />
			<Text>{item.label}</Text>
		</Menu.Item>
	)
}

interface Props {
	children
	hideTopBar?: boolean
	hideSideBar?: boolean
	hideCrumbs?: boolean
	fullScreen?: boolean
	style?: any
}

const menuIsActive = (menu: MenuItem[], activePaths: string[]): boolean => {
	return activePaths.some(path =>
		menu.some(({ routePath, submenu }) => path === routePath || (submenu && menuIsActive(submenu, [path])))
	)
}

const SidebarLayout: React.FC<Props> = ({ children, hideTopBar, hideSideBar, hideCrumbs, fullScreen, style }) => {
	const { pathname } = useLocation()
	const selectedKeys = [pathname]
	const [, setCollapsed] = useState(AppStore.collapsedBarMenu)
	const defaultOpenKeys = getMenuItems()
		.filter(({ submenu }) => submenu && !AppStore.collapsedBarMenu && menuIsActive(submenu, selectedKeys))
		.map(({ label }) => label)

	const toggleCollapsedButton = async () => {
		const newCollapsed = !AppStore.collapsedBarMenu
		await AppStore.setCollapsedMenuBar(newCollapsed)
		setCollapsed(newCollapsed)
	}
	const renderCollapsedButton = (collapsed?: boolean) =>
		AppStore.collapsedBarMenu ? (
			<MenuUnfoldOutlined onClick={toggleCollapsedButton} />
		) : (
			<MenuFoldOutlined onClick={toggleCollapsedButton} />
		)

	const crumbs = Object.entries(getRoutesMap())
	// Get all routes that contain the current one.
		.filter(([key, value]) => pathname.includes(key) && value.breadcrumbName)
		.map(([key, value]) => ({
			path: key,
			breadcrumbName: value.breadcrumbName,
		})) as { path: routes; breadcrumbName: string }[]
	if (fullScreen)
		return (
			<View style={{ minHeight: '100vh', display: 'flex' }}>
				{children} {}
			</View>
		)

	const logout = async () => {
		await AppStore.setGoToDashboard(true)
	}

	const dropdownMenu = (
		<Menu>
			<Menu.Item disabled>
				{__(T.role)}: {getUserRole()}
			</Menu.Item>
			<Menu.Item
				onClick={async () => {
					await AppStore.setLanguage('it')
					window.location.reload(false)
				}}
			>
				Italiano
			</Menu.Item>
			<Menu.Item
				onClick={async () => {
					await AppStore.setLanguage('en')
					window.location.reload(false)
				}}
			>
				English
			</Menu.Item>
			<Menu.Item danger onClick={logout}>
				{__(T.buttons.logout)}
			</Menu.Item>
		</Menu>
	)

	return (
		<Layout style={{ height: '100vh', ...style }}>
			{!hideSideBar && (
				<Layout.Sider width={'auto'} collapsedWidth={'auto'} collapsed={AppStore.collapsedBarMenu}>
					{!AppStore.collapsedBarMenu && (
						<img
							src={require('assets/images/logo.svg')}
							alt={__(T.logo)}
							style={{ width: '135px', height: 'auto', marginBottom: 25, marginTop: 21, marginLeft: 24 }}
							onClick={() => navigate('/')}
						/>
					)}
					{AppStore.collapsedBarMenu && (
						<img
							src={require('assets/images/logo_small.svg')}
							alt={__(T.logo)}
							style={{ width: '31px', height: 'auto', marginBottom: 25, marginTop: 21, marginLeft: 24 }}
							onClick={() => navigate('/')}
						/>
					)}
					<Menu theme="dark" mode="inline" selectedKeys={selectedKeys} defaultOpenKeys={defaultOpenKeys}>
						{getMenuItems().map(renderMenuItem)}
					</Menu>
				</Layout.Sider>
			)}

			<Layout className="site-layout">
				{!hideTopBar && (
					<Header
						style={{
							backgroundColor: '#FFFFFF',
							display: 'inline-flex',
							alignItems: 'center',
						}}
					>
						<View>{renderCollapsedButton(AppStore.collapsedBarMenu)}</View>
						<View style={{ display: 'flex', flex: 1, justifyContent: 'flex-end', alignItems: 'center' }}>
							<Avatar
								style={{ color: '#f56a01', backgroundColor: '#fde3d0', marginLeft: 25, marginRight: 5 }}
								icon={<UserOutlined />}
							/>
							<Dropdown overlay={dropdownMenu}>
                <span className="ant-dropdown-link" style={{ marginLeft: 8 }} onClick={e => e.preventDefault()}>
                  {/*{AppStore.loggedUser?.userName} <DownOutlined />*/}
                </span>
							</Dropdown>
						</View>
					</Header>
				)}
				{crumbs.length > 1 && !hideCrumbs && (
					<Breadcrumb style={{ marginLeft: 10, marginTop: 10 }}>
						{crumbs.map(crumb => (
							<Breadcrumb.Item key={crumb.path}>
								<Button type="link" onClick={() => navigate(crumb.path)}>
									{crumb.breadcrumbName}
								</Button>
							</Breadcrumb.Item>
						))}
					</Breadcrumb>
				)}
				<Layout.Content>{children}</Layout.Content>
			</Layout>
		</Layout>
	)
}

export default SidebarLayout
