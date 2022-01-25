export class PaginationHelper {
  pageSize: number
  totalElements: number
  currentPage: number

  constructor(pageSize?: number) {
    this.pageSize = pageSize ?? 5
    this.totalElements = 0
    this.currentPage = 1
  }

  getPaginationApiParams() {
    return { page: this.currentPage - 1, size: this.pageSize }
  }

  setCurrentPage(page: number) {
    this.currentPage = page
  }

  setTotalElements(tot: number) {
    this.totalElements = tot
  }
}
