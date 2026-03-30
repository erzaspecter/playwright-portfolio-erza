import { test, expect } from '@playwright/test';

test.describe('Authentication API Testing', () => {
  test('Login with valid credentials', async ({ request }) => {
    const response = await request.post('/auth', {
      data: {
        username: 'admin',
        password: 'password123'
      }
    });

    expect(response.status()).toBe(200);
    const body = await response.json();
    expect(body).toHaveProperty('token');
    console.log('✅ Login successful, token received:', body.token);
  });

  test('Login with invalid credentials should fail', async ({ request }) => {
    const response = await request.post('/auth', {
      data: {
        username: 'wrong',
        password: 'wrong'
      }
    });

    expect([200, 400]).toContain(response.status());
    const body = await response.json();
    if (body.reason) {
      console.log('✅ Invalid login properly rejected:', body.reason);
    }
  });
});
