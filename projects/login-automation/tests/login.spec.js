const { test, expect } = require('@playwright/test')
const { LoginPage } = require('../pages/LoginPage')

test('login success with valid credentials', async ({ page }) => {
  const loginPage = new LoginPage(page)
  await page.goto('https://www.saucedemo.com/')
  await loginPage.login('standard_user', 'secret_sauce')
  await expect(page).toHaveURL('https://www.saucedemo.com/inventory.html')
})

test('login fails with wrong password', async ({ page }) => {
  const loginPage = new LoginPage(page)
  await page.goto('https://www.saucedemo.com/')
  await loginPage.login('standard_user', 'wrong_password')
  await expect(page.locator('[data-test="error"]')).toHaveText(/Username and password do not match/)
})