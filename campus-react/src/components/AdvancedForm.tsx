import React, {CSSProperties, RefObject, useEffect, useState} from 'react'
import {
	AutoComplete,
	Button,
	Checkbox,
	Col,
	DatePicker,
	Form,
	Input,
	InputNumber,
	Radio,
	Row,
	Select,
	Spin,
	Switch,
	Tooltip,
} from 'antd'
import PlacesAutocomplete, {geocodeByAddress} from 'react-places-autocomplete'
import {FormItemProps, Rule} from 'antd/lib/form'
import TextArea from 'antd/lib/input/TextArea'
import {ColSize} from 'antd/lib/col'
import Text from 'antd/lib/typography/Text'
import api from 'api/api'
import {JsonSchema} from 'types'
import {QuestionCircleOutlined} from '@ant-design/icons'
import {__, T} from 'config/i18n'
import {FormInstance} from 'antd/es/form'

const { Option } = Select

export type InputType =
	| 'checkbox'
	| 'text'
	| 'password'
	| 'number'
	| 'textarea'
	| 'select'
	| 'autocomplete'
	| 'date'
	| 'daterange'
	| 'datetime'
	| 'multiple-file'
	| 'group'
	| 'form'
	| 'button'
	| 'radio'
	| 'switch'
	| 'input-autocomplete'
	| 'google-places-autocomplete'

interface ColProps {
	span?: number
	order?: number
	offset?: number
	push?: number
	pull?: number
	xs?: number | ColSize
	sm?: number | ColSize
	md?: number | ColSize
	lg?: number | ColSize
	xl?: number | ColSize
	xxl?: number | ColSize
	prefixCls?: string
	flex?: number
}

export interface Schema extends Partial<FormItemProps> {
	name: string
	type?: InputType
	decimal?: number
	defaultValue?: any
	label?: string
	placeholder?: string
	dateRangePlaceholders?: [string, string]
	col?: ColProps | number
	rules?: Rule[]
	options?: { label: string; value: any; tooltip?: string }[]
	fields?: any[]
	onClick?: () => void
	buttonType?: 'text' | 'link' | 'ghost' | 'default' | 'primary' | 'dashed'
	render?: () => JSX.Element
	hide?: boolean
	disabled?: boolean
	notInput?: boolean
	urlSearch?: string
	descriptionKey?: string
	secondaryDescriptionKey?: string
	multipleAutocomplete?: boolean
	showSearch?: boolean
	inputAddonBefore?: string
	onInputBlur?: () => void
}

export interface ButtonProps {
	text: string
	col?: number
	offset?: number
	style?: React.CSSProperties
	disabled?: boolean
	onPress?: () => void
}

interface Props {
	schema: Schema[]
	values?: any
	onValuesChange?: (changedValues: any, values: any) => void
	submitButton?: ButtonProps
	resetButton?: ButtonProps
	secondaryButton?: ButtonProps
	className?: string
	style?: CSSProperties
	title?: string
	ref?: (r: any) => void
	formRef?: RefObject<FormInstance>
}

export const convertJsonSchema = (jsonSchema: JsonSchema) => {
	let schema: Schema[] = []

	function getType(type: any): InputType {
		switch (type) {
			case 'integer':
				return 'number'
			case 'date-time':
				return 'datetime'
			case 'array':
				return 'select'
			case 'boolean':
				return 'checkbox'
			default:
				return type
		}
	}

	try {
		const { properties, required } = jsonSchema
		if (properties) {
			Object.keys(properties).forEach(key => {
				const prop = properties[key] as any
				schema.push({
					name: key,
					type: getType(prop.type),
					label: prop.label ?? key,
					placeholder: prop.description,
					defaultValue: prop.default,
					options: prop.items?.enum
						? prop.items?.enum.map(x => {
							return { value: x, label: x }
						})
						: undefined,
					rules: required?.filter(r => r === key).length === 1 ? [{ required: true }] : undefined,
				})
			})
		}
	} catch (err) {}
	return schema
}

export default function AdvancedForm({
																			 schema,
																			 values,
																			 onValuesChange,
																			 submitButton,
																			 resetButton,
																			 secondaryButton,
																			 style,
																			 className,
																			 title,
																			 formRef,
																			 ...props
																		 }: Props) {
	const onFinishFailed = () => {
		console.log('Failed:', {})
	}

	const [form] = Form.useForm()
	const [refresh, setrefresh] = useState(0)

	const [autocompleteFields, setautocompleteFields] = useState(
		schema
			.filter(f => f.type === 'autocomplete')
			.map(field => {
				return {
					name: field.name,
					fetching: false,
					fetch: async (x: any) => {
						let data = await api.get<any>(field.urlSearch!.replace('-X-', x))
						let newAutocompleteFields = [...autocompleteFields]
						let newData: any[] = []

						if (data.status === 200 && Array.isArray(data.data.content)) {
							newData.push(...data.data?.content)
							newAutocompleteFields.find(f => f.name === field.name)!.data = newData
							setautocompleteFields(newAutocompleteFields)
							return data.data.content || ([] as any[])
						} else if (data.status === 200 && Array.isArray(data.data)) {
							newData.push(...data.data)
							newAutocompleteFields.find(field => field.name)!.data = newData
							setautocompleteFields(newAutocompleteFields)
							return data.data || ([] as any[])
						}
						newAutocompleteFields.find(field => field.name)!.data = newData as any[]
						setautocompleteFields(newAutocompleteFields)
						return newData as any[]
					},
					onChange: (x: any) => {
						let newAutocompleteFields = [...autocompleteFields]
						newAutocompleteFields.find(field => field.name)!.data = []
						setautocompleteFields(newAutocompleteFields)
					},
					data: [] as any[],
				}
			})
	)

	const [inputAutocompleteFields, setinputAutocompleteFields] = useState(
		schema
			.filter(f => f.type === 'input-autocomplete')
			.map(field => {
				return {
					name: field.name,
					inputFetching: false,
					inputFetch: async (x: any) => {
						let data = await api.get<any>(field.urlSearch!.replace('-X-', x))
						let newAutocompleteFields = [...inputAutocompleteFields]
						let newData: any[] = []

						if (data.status === 200 && Array.isArray(data.data.content)) {
							newData.push(...data.data?.content)
							newAutocompleteFields.find(field => field.name)!.inputData = newData
							setinputAutocompleteFields(newAutocompleteFields)
							return data.data.content || ([] as any[])
						} else if (data.status === 200 && Array.isArray(data.data)) {
							newData.push(...data.data)
							newAutocompleteFields.find(field => field.name)!.inputData = newData
							setinputAutocompleteFields(newAutocompleteFields)
							return data.data || ([] as any[])
						}
						newAutocompleteFields.find(field => field.name)!.inputData = newData as any[]
						setinputAutocompleteFields(newAutocompleteFields)
						return newData as any[]
					},
					inputOnChange: (x: any) => {
						let newAutocompleteFields = [...inputAutocompleteFields]
						newAutocompleteFields.find(field => field.name)!.inputData = []
						setinputAutocompleteFields(newAutocompleteFields)
					},
					inputData: [] as any[],
				}
			})
	)

	const [placesAutocompleteFields, setPlacesAutocompleteFields] = useState(
		schema
			.filter(f => f.type === 'google-places-autocomplete')
			.map(field => {
				return {
					name: field.name,
					input: '',
					googleOnChange: async (selectedPlace: any, formValues: any) => {
						const result = await geocodeByAddress(selectedPlace)
						let newValues = { ...formValues }
						newValues[field.name] = selectedPlace
						newValues[field.name + '-result'] = result[0]
						onValuesChange && onValuesChange({ [field.name]: selectedPlace }, newValues)
					},
				}
			})
	)

	useEffect(() => {
		const loadInitialData = async () => {
			const promises = autocompleteFields.map(a => async () => {
				a.data = await a.fetch('')
			})
			await Promise.all(promises.map(p => p()))
			setautocompleteFields(autocompleteFields)
			setrefresh(refresh + 1)
		}
		if (refresh === 0) {
			loadInitialData()
		}
	}, [autocompleteFields, setrefresh, refresh, setautocompleteFields])

	const getInput = (schema: Schema) => {
		if (schema.disabled) {
			if (schema.type === 'password') return <Input.Password disabled />
			return <Input disabled />
		}

		switch (schema.type) {
			case 'button':
				return (
					<Button type={schema.buttonType} onClick={schema.onClick}>
						{schema.placeholder}
					</Button>
				)
			case 'text':
				return (
					<Input
						placeholder={schema.placeholder || schema.label}
						value={schema.initialValue}
						disabled={schema.disabled}
						onBlur={schema.onInputBlur}
						addonBefore={schema.inputAddonBefore}
					/>
				)
			case 'password':
				return <Input.Password placeholder={schema.placeholder} defaultValue={schema.defaultValue} />
			case 'checkbox':
				return (
					<Checkbox disabled={schema.disabled} defaultChecked={schema.defaultValue}>
						{schema.placeholder}
					</Checkbox>
				)
			case 'number':
				if (schema.disabled) return <Text children={schema.defaultValue} />
			//@ts-ignore
			case 'integer':
				return (
					<InputNumber
						defaultValue={schema.defaultValue || 0}
						placeholder={schema.placeholder}
						style={{ width: '100%' }}
					/>
				)
			case 'textarea':
				return <TextArea rows={4} disabled={schema.disabled} placeholder={schema.placeholder} />
			case 'date':
				return <DatePicker format="DD.MM.YYYY" style={{ width: '100%' }} placeholder={schema.placeholder} />
			case 'datetime':
			//@ts-ignore
			case 'date-time':
				return (
					<DatePicker
						format="DD.MM.YYYY HH:mm"
						// showTime={{ defaultValue: moment('00:00:00', 'HH:mm:ss') }}
						disabled={schema.disabled}
						style={{ width: '100%' }}
						placeholder={schema.placeholder}
					/>
				)
			case 'daterange':
				return (
					<DatePicker.RangePicker
						format="DD.MM.YYYY"
						style={{ width: '100%' }}
						placeholder={schema.dateRangePlaceholders}
					/>
				)
			case 'select':
				return (
					<Select
						defaultValue={schema.defaultValue}
						placeholder={schema.placeholder}
						allowClear
						showSearch={schema.showSearch}
						filterOption={(input, option) => {
							if (option) return option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
							return false
						}}
					>
						{schema.options && schema.options.map(opt => <Option value={opt.value}>{opt.label}</Option>)}
					</Select>
				)
			case 'radio':
				return (
					<Radio.Group defaultValue={schema.defaultValue} disabled={schema.disabled}>
						{schema.options?.map(option => {
							return (
								<Radio value={option.value}>
									{option.label}
									{option.tooltip && (
										<Tooltip title={option.tooltip}>
											<QuestionCircleOutlined style={{ marginLeft: 5 }} />
										</Tooltip>
									)}
								</Radio>
							)
						})}
					</Radio.Group>
				)

			case 'autocomplete':
				const { fetching, fetch, onChange, data } = autocompleteFields.find(f => f.name === schema.name)!
				return (
					<Select
						labelInValue
						allowClear
						showSearch={true}
						mode={schema.multipleAutocomplete ? 'multiple' : undefined}
						placeholder={schema.placeholder}
						notFoundContent={fetching ? <Spin size="small" /> : null}
						filterOption={false}
						onSearch={fetch}
						onChange={onChange}
						style={{ width: '100%' }}
						onFocus={event => {
							fetch('')
						}}
					>
						{data.map(d => (
							<Option key={d.id} value={JSON.stringify(d)}>
								{schema.descriptionKey
									? schema.secondaryDescriptionKey
										? d[schema.descriptionKey][schema.secondaryDescriptionKey]
										: d[schema.descriptionKey]
									: d.description}
							</Option>
						))}
					</Select>
				)
			case 'input-autocomplete':
				const { inputFetching, inputFetch, inputOnChange, inputData } = inputAutocompleteFields.find(
					f => f.name === schema.name
				)!
				return (
					<AutoComplete
						allowClear
						showSearch={true}
						placeholder={schema.placeholder}
						notFoundContent={inputFetching ? <Spin size="small" /> : null}
						filterOption={false}
						onSearch={inputFetch}
						onChange={inputOnChange}
						style={{ width: '100%' }}
						onFocus={event => {
							inputFetch('')
						}}
					>
						{inputData.map(d => (
							<Option key={d.id} value={JSON.stringify(d)}>
								{schema.descriptionKey
									? schema.secondaryDescriptionKey
										? d[schema.descriptionKey][schema.secondaryDescriptionKey]
										: d[schema.descriptionKey]
									: d.description}
							</Option>
						))}
					</AutoComplete>
				)
			case 'google-places-autocomplete':
				const { googleOnChange, input } = placesAutocompleteFields.find(f => f.name === schema.name)!
				const searchPlaceholder = __(T.misc.insert)
				const loadingPlaceholder = __(T.misc.loading)
				return (
					<PlacesAutocomplete
						value={input}
						onSelect={selectedPlace => {
							googleOnChange(selectedPlace, values)
						}}
						searchOptions={{ types: ['(cities)'] }}
						debounce={1200}
						key={schema.name}
					>
						{({ getInputProps, suggestions, getSuggestionItemProps, loading }) => (
							<div>
								<input
									{...getInputProps({
										placeholder: searchPlaceholder,
										className: 'location-search-input ant-input',
									})}
								/>
								<div className="autocomplete-dropdown-container">
									{loading && <div>{loadingPlaceholder}</div>}
									{suggestions.map(suggestion => {
										const className = suggestion.active ? 'suggestion-item--active' : 'suggestion-item'
										// inline style for demonstration purpose
										const style = suggestion.active
											? { backgroundColor: '#fafafa', cursor: 'pointer' }
											: { backgroundColor: '#ffffff', cursor: 'pointer' }
										return (
											<div
												{...getSuggestionItemProps(suggestion, {
													className,
													style,
												})}
											>
												<span>{suggestion.description}</span>
											</div>
										)
									})}
								</div>
							</div>
						)}
					</PlacesAutocomplete>
				)
			case 'switch':
				return <Switch defaultChecked={schema.defaultValue} disabled={schema.disabled} style={schema.style} />
			default:
				return <Input placeholder={schema.placeholder || schema.label} defaultValue={schema.defaultValue} />
		}
	}

	values && form.setFieldsValue(values)

	return (
		<Form
			ref={formRef}
			form={form}
			layout="vertical"
			onFinish={submitButton?.onPress}
			onFinishFailed={onFinishFailed}
			onValuesChange={onValuesChange}
			className={className}
			style={style}
			{...props}
		>
			{title && <h2>{title}</h2>}
			<Row gutter={24}>
				{schema
					.filter(s => !s.hide)
					.map((s, index) => {
						const span = typeof s.col === 'number' ? s.col : 24
						const colProps: ColProps = s.col && typeof s.col === 'object' ? s.col : {}
						return (
							<Col span={span} {...colProps} key={index}>
								{s.notInput && s.render && s.render()}
								{!s.notInput && (
									<Form.Item key={index} {...s} valuePropName={s.type === 'checkbox' ? 'checked' : undefined}>
										{s.render ? s.render() : getInput(s)}
									</Form.Item>
								)}
							</Col>
						)
					})}
			</Row>

			{(submitButton || secondaryButton || resetButton) && (
				<Row gutter={24}>
					{secondaryButton && (
						<Col span={secondaryButton.col ?? 24} offset={secondaryButton.offset ?? 0}>
							<Form.Item style={{ marginBottom: 0 }}>
								<Button
									type="default"
									htmlType="button"
									style={{ ...{ width: '100%' }, ...secondaryButton.style }}
									onClick={secondaryButton.onPress}
									disabled={secondaryButton.disabled}
								>
									{secondaryButton.text}
								</Button>
							</Form.Item>
						</Col>
					)}
					{resetButton && (
						<Col span={resetButton.col ?? 24} offset={resetButton.offset ?? 0}>
							<Form.Item style={{ marginBottom: 0 }}>
								<Button
									type="default"
									htmlType="reset"
									style={{ ...{ width: '100%' }, ...resetButton.style }}
									onClick={resetButton.onPress}
									disabled={resetButton.disabled}
								>
									{resetButton.text}
								</Button>
							</Form.Item>
						</Col>
					)}
					{submitButton && (
						<Col span={submitButton.col ?? 24} offset={submitButton.offset ?? 0}>
							<Form.Item style={{ marginBottom: 0 }}>
								<Button
									type="primary"
									htmlType="submit"
									style={{ ...{ width: '100%' }, ...submitButton.style }}
									disabled={submitButton.disabled}
								>
									{submitButton.text}
								</Button>
							</Form.Item>
						</Col>
					)}
				</Row>
			)}
		</Form>
	)
}
