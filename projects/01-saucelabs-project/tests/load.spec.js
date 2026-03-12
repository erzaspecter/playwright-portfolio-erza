// tests/performance/load.spec.js
const { test, expect } = require('@playwright/test');

test.describe('Performance Tests', () => {
  
  test('login page load performance', async ({ page }) => {
    const startTime = Date.now();
    
    await page.goto('https://www.saucedemo.com', {
      waitUntil: 'networkidle'
    });
    
    const loadTime = Date.now() - startTime;
    console.log(`✅ Login page loaded in ${loadTime}ms`);
    
    // Performance budget
    expect(loadTime).toBeLessThan(3000); // Max 3 seconds
  });

  test('Core Web Vitals measurement', async ({ page }) => {
    await page.goto('https://www.saucedemo.com');
    
    // Measure LCP (Largest Contentful Paint)
    const lcp = await page.evaluate(() => {
      return new Promise(resolve => {
        new PerformanceObserver((list) => {
          const entries = list.getEntries();
          const lastEntry = entries[entries.length - 1];
          resolve(lastEntry.startTime);
        }).observe({ type: 'largest-contentful-paint', buffered: true });
      });
    });
    
    console.log(`LCP: ${lcp}ms`);
    expect(lcp).toBeLessThan(2500); // Good LCP
    
    // Measure CLS (Cumulative Layout Shift)
    const cls = await page.evaluate(() => {
      return new Promise(resolve => {
        let clsValue = 0;
        new PerformanceObserver((list) => {
          for (const entry of list.getEntries()) {
            if (!entry.hadRecentInput) {
              clsValue += entry.value;
            }
          }
          resolve(clsValue);
        }).observe({ type: 'layout-shift', buffered: true });
      });
    });
    
    console.log(`CLS: ${cls}`);
    expect(cls).toBeLessThan(0.1); // Good CLS
  });

  test('inventory page performance after login', async ({ page }) => {
    // Login
    await page.goto('https://www.saucedemo.com');
    await page.fill('#user-name', 'standard_user');
    await page.fill('#password', 'secret_sauce');
    
    // Measure navigation to inventory
    const navigationPromise = page.waitForNavigation();
    await page.click('#login-button');
    
    const startTime = Date.now();
    await navigationPromise;
    const navigationTime = Date.now() - startTime;
    
    console.log(`Navigation to inventory: ${navigationTime}ms`);
    expect(navigationTime).toBeLessThan(2000);
  });
});