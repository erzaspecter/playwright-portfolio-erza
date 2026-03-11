// tests/auth-api.spec.js
const { test, expect } = require('@playwright/test');

test.describe('Authentication API Testing', () => {
  test('Login with valid credentials', async ({ request }) => {
    const response = await request.post('https://reqres.in/api/login', {
      data: {
        email: 'eve.holt@reqres.in',
        password: 'cityslicka'
      }
    });
    
    const status = response.status();
    const rawBody = await response.text();
    let body = {};
    try {
      body = JSON.parse(rawBody);
    } catch {
      body = { error: `Non-JSON response received: ${rawBody.slice(0, 120)}` };
    }

    expect(status).toBe(200);
    expect(body).toHaveProperty('token');
    console.log('✅ Login successful, token received:', body.token);
  });

  test('Login with invalid credentials should fail', async ({ request }) => {
    const response = await request.post('https://reqres.in/api/login', {
      data: {
        email: 'invalid@email.com',
        password: 'wrong'
      }
    });
    
    expect([400, 401]).toContain(response.status());
    const rawBody = await response.text();
    let body = {};
    try {
      body = JSON.parse(rawBody);
    } catch {
      body = { error: `Non-JSON response received: ${rawBody.slice(0, 120)}` };
    }
    expect(body.error).toBeDefined();
    
    console.log('✅ Invalid login properly rejected');
  });
});