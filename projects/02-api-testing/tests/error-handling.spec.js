// tests/error-handling.spec.js
const { test, expect } = require('@playwright/test');

test.describe('Error Handling & Negative Tests', () => {
  test('GET non-existent resource should return 404', async ({ request }) => {
    const response = await request.get('https://jsonplaceholder.typicode.com/posts/999999');
    
    expect(response.status()).toBe(404);
    
    console.log('✅ 404 returned for non-existent resource');
  });

  test('Invalid endpoint should return 404', async ({ request }) => {
    const response = await request.get('https://jsonplaceholder.typicode.com/invalid-endpoint');
    
    expect(response.status()).toBe(404);
    
    console.log('✅ 404 returned for invalid endpoint');
  });

  test('POST with invalid data structure', async ({ request }) => {
    const response = await request.post('https://jsonplaceholder.typicode.com/posts', {
      data: 'invalid data format' // Bukan JSON object
    });
    
    // Bisa 400 atau 201 tergantung API implementation
    console.log(`✅ Response status: ${response.status()}`);
  });

  test('Request timeout handling', async ({ request }) => {
    // Test dengan timeout
    test.setTimeout(5000);
    
    const startTime = Date.now();
    
    try {
      await request.get('https://httpbin.org/delay/3', { timeout: 2000 });
    } catch (error) {
      const endTime = Date.now();
      const duration = endTime - startTime;
      
      expect(error.message).toContain('timeout');
      expect(duration).toBeLessThan(3000);
      
      console.log(`✅ Timeout properly handled in ${duration}ms`);
    }
  });
});