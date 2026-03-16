class CartPage {
  constructor(page) {
    this.page = page
    this.checkoutButton = '#checkout'
  }

  async checkout() {
    await this.page.click(this.checkoutButton)
  }
}

module.exports = CartPage