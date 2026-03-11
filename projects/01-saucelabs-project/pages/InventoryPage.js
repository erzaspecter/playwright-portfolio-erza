class InventoryPage {
  constructor(page) {
    this.page = page
    this.cartButton = '.shopping_cart_link'
  }

  async addItemToCart(itemName) {
    await this.page.locator('[data-test="add-to-cart-sauce-labs-backpack"]').click();
  }

  async goToCart() {
    await this.page.click(this.cartButton)
  }
}

module.exports = { InventoryPage }