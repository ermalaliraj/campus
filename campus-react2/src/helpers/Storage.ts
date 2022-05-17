class Storage {
	preKey = 'tcare.'

	async save(key: string, data: any): Promise<void> {
		if (data === undefined) throw new Error('undefined data')
		return localStorage.setItem(`${this.preKey}${key}`, JSON.stringify(data))
	}

	load(key: string, defaultValue?: any): Promise<any | undefined> {
		const data = localStorage.getItem(`${this.preKey}${key}`)
		return data ? JSON.parse(data) : defaultValue
	}

	async remove(key: string): Promise<void> {
		return localStorage.removeItem(`${this.preKey}${key}`)
	}

	async purgeAllData(): Promise<void> {
		return localStorage.clear()
	}
}

export default new Storage()
