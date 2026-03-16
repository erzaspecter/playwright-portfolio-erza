class InventoryPage {
  constructor(page) {
    this.page = page;
    this.cartButton = '.shopping_cart_link';
  }

  async addItemToCart(itemName) {
    await this.page.locator('[data-test="add-to-cart-sauce-labs-backpack"]').click();
  }

  async goToCart() {
    await this.page.click(this.cartButton);
  }

  async getItemCount() {
    // hitung jumlah produk di halaman inventory
    return await this.page.locator('.inventory_item').count();
  }
}

module.exports = { InventoryPage }  // ✅ tambah kurung kurawal