// hooks.js
const { BeforeAll, AfterAll, Before, After } = require('@cucumber/cucumber');
const { chromium } = require('playwright');

BeforeAll(async function () {
  // Jalankan sekali sebelum semua test
  console.log('🚀 Starting browser...');
  global.browser = await chromium.launch({ 
    headless: false,  // true untuk CI/CD, false untuk lihat browser
  });
});

Before(async function () {
  // Jalankan sebelum tiap scenario
  console.log('📄 Creating new page...');
  const context = await global.browser.newContext();
  this.page = await context.newPage();
});

After(async function () {
  // Jalankan setelah tiap scenario
  console.log('✅ Scenario selesai');
  if (this.page) {
    await this.page.close();
  }
});

AfterAll(async function () {
  // Jalankan sekali setelah semua test
  console.log('👋 Closing browser...');
  await global.browser.close();
});