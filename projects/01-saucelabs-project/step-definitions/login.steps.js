// login.steps.js
const { Given, When, Then } = require('@cucumber/cucumber');
const { expect } = require('@playwright/test');
const LoginPage = require('../pages/LoginPage');
const InventoryPage = require('../pages/InventoryPage');

Given('I am on the login page', async function () {
  console.log('📍 Navigating to login page...');
  this.loginPage = new LoginPage(this.page);
  await this.loginPage.goto();
});

When('I enter username {string} and password {string}', async function (username, password) {
  console.log(`🔑 Logging in with: ${username}`);
  await this.loginPage.login(username, password);
});

When('I click the login button', async function () {
  // Sudah dilakukan di method login()
  console.log('🖱️ Clicking login button...');
});

Then('I should be redirected to the inventory page', async function () {
  console.log('🔍 Verifying redirect to inventory...');
  await expect(this.page).toHaveURL(/.*inventory.html/);
});

Then('I should see the products list', async function () {
  console.log('📦 Checking products list...');
  const inventoryPage = new InventoryPage(this.page);
  const count = await inventoryPage.getItemCount();
  expect(count).toBe(6);
  console.log(`✅ Found ${count} products`);
});

Then('I should see error message {string}', async function (expectedError) {
  console.log(`❌ Verifying error message: ${expectedError}`);
  const errorMessage = await this.loginPage.getErrorMessage();
  expect(errorMessage).toContain(expectedError);
  console.log(`✅ Error message verified: "${errorMessage}"`);
});