export interface BaseEntity {
  id: any
  creationDate: number
  lastUpdate: number
}

export interface User extends BaseEntity {
  userName: string
  email: string
  firstName: string
  lastName: string
  phone?: string
  enabled: boolean
  password?: string
}

export interface Company {
	name: string
	address: string
}

export interface Page<T> {
  content: T[]
  totalElements: number
  page: number
  totalPages: number
}

export interface PaginationParams {
  page: number
  size: number
}

export interface QueryParams extends PaginationParams {
  [index: string]: any
}
