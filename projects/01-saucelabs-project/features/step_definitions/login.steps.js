const { Given, When, Then } = require('@cucumber/cucumber');

Given('I am on the login page', async function () {
  await this.page.goto('https://www.saucedemo.com/');
});

When('I enter username {string} and password {string}', async function (username, password) {
  await this.page.fill('#user-name', username);
  await this.page.fill('#password', password);
});

When('I click the login button', async function () {
  await this.page.click('#login-button');
});

Then('I should be redirected to the inventory page', async function () {
  const url = this.page.url();
  if (!url.includes('inventory')) throw new Error('Not redirected to inventory page');
});

Then('I should see the products list', async function () {
  const items = await this.page.$$('.inventory_item');
  if (!items || items.length === 0) throw new Error('Products list not found');
});
