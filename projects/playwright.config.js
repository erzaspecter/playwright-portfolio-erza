// projects/playwright.config.ts
import { defineConfig, devices } from '@playwright/test';

export default defineConfig({
  // Konfigurasi global untuk semua project di bawahnya
  testDir: './', // Cari dari folder projects/
  timeout: 30000,
  fullyParallel: true,
  forbidOnly: !!process.env.CI,
  retries: process.env.CI ? 2 : 0,
  workers: process.env.CI ? 1 : undefined,
  reporter: 'html',
  use: {
    trace: 'on-first-retry',
  },

  // Daftarkan project-projek tes di sini
  projects: [
    {
      name: 'ui-saucelabs', // Nama project untuk UI
      testDir: './01-saucelabs-project/tests', // ARAH ke folder UI
      use: {
        ...devices['Desktop Chrome'], // Bisa pakai browser apapun
        baseURL: 'https://www.saucedemo.com'
      },
    },
    {
      name: 'api-testing', // Nama project untuk API
      testDir: './02-api-testing/tests', // ARAH ke folder API
      use: {
        // Untuk API, kita tidak perlu browser, jadi bisa pakai konfigurasi khusus
        baseURL: 'https://restful-booker.herokuapp.com'
      },
    },
  ],
});