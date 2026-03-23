import { test, expect } from '@playwright/test';

test('mock API response', async ({ page }) => {
  await page.route('**/api/users', async route => {
    await route.fulfill({
      status: 200,
      contentType: 'application/json',
      body: JSON.stringify([
        { id: 1, name: 'Mock User A' },
        { id: 2, name: 'Mock User B' }
      ])
    });
  });

  await page.goto('http://localhost:3000');
  await expect(page.locator('#users')).toContainText('Mock User A');
});
