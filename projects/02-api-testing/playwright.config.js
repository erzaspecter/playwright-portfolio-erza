// @ts-check
const { defineConfig, devices } = require('@playwright/test');

module.exports = defineConfig({
  // API testing biasanya lebih cepat
  timeout: 10000,
  
  // Jalankan parallel
  fullyParallel: true,
  
  // Reporter yang menarik untuk portfolio
  reporter: [
    ['html', { outputFolder: 'playwright-report' }],
    ['json', { outputFile: 'test-results.json' }],
    ['allure-playwright']
  ],
  
  use: {
    // Base URL untuk API
    baseURL: 'https://jsonplaceholder.typicode.com',
    
    // Extra HTTP headers
    extraHTTPHeaders: {
      'Accept': 'application/json',
      'Content-Type': 'application/json',
      // 'Authorization': 'Bearer token123' // Untuk API yang perlu auth
    },
  },
  
  projects: [
    {
      name: 'API Tests',
      testMatch: '**/*.spec.js',
    }
  ],
});