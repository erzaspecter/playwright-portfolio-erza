const { Given, When, Then } = require('@cucumber/cucumber');

Given('user berada di halaman login', async function () {
  await this.page.goto('http://localhost:3000/login');
});

When('user memasukkan username dan password yang benar', async function () {
  await this.page.fill('#username', 'admin');
  await this.page.fill('#password', '12345');
  await this.page.click('#submit');
});

Then('user berhasil masuk ke dashboard', async function () {
  const content = await this.page.textContent('h1');
  if (!content.includes('Dashboard')) {
    throw new Error('Login gagal');
  }
});
