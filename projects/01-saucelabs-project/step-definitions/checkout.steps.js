const { Given, When, Then } = require('@cucumber/cucumber');

Given('I am logged in', async function () {
  await this.page.goto('https://www.saucedemo.com/');
  await this.page.fill('#user-name', 'standard_user');
  await this.page.fill('#password', 'secret_sauce');
  await this.page.click('#login-button');
});

Given('I have added {string} to the cart', async function (itemName) {
  const addSelector = `button[id^="add-to-cart"][id*="${itemName.toLowerCase().replace(/\s+/g,'-')}"]`;
  const found = await this.page.$(addSelector);
  if (found) {
    await this.page.click(addSelector);
  } else {
    // fallback: search by product name and click its add button
    const product = await this.page.locator('.inventory_item').filter({ hasText: itemName }).first();
    if ((await product.count()) === 0) throw new Error(`Product not found: ${itemName}`);
    await product.locator('button').click();
  }
});

When('I proceed to checkout and submit info {string},{string},{string}', async function (first, last, postal) {
  await this.page.click('.shopping_cart_link');
  await this.page.click('#checkout');
  await this.page.fill('#first-name', first);
  await this.page.fill('#last-name', last);
  await this.page.fill('#postal-code', postal);
  await this.page.click('#continue');
  await this.page.click('#finish');
});

Then('I should see the order confirmation', async function () {
  const header = await this.page.textContent('.complete-header');
  if (!header || !/thank you/i.test(header)) throw new Error('Order confirmation not found');
});