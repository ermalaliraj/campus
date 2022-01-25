import {JSONSchema7} from 'json-schema'

export type category = 'a' | 'b' | 'c' | string
export type role = 'admin' | 'guest' | 'user'

export interface UserFields {
	name?: string
	surname?: string
	phone?: string
	email?: string
	password?: string
	confirmPassword?: string
}

export interface DocumentsFields {
	category?: category | string
}

export interface CompanyFields extends DocumentsFields {
	country?: string
	city?: string
	postalCode?: string
	address?: string
}

export type JsonSchema = JSONSchema7
