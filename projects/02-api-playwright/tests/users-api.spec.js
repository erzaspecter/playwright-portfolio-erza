// tests/users-api.spec.js
import { test, expect } from '@playwright/test';

const ApiHelper = require('../helpers/api-helper');
const DataGenerator = require('../helpers/data-generator');

test.describe('Users API Testing', () => {
  let apiHelper;

  test.beforeEach(async ({ request }) => {
    apiHelper = new ApiHelper(request);
  });

  test('GET /users - Should return list of users', async () => {
    const response = await apiHelper.get('/users');
    
    // Assertions
    expect(response.status).toBe(200);
    expect(Array.isArray(response.body)).toBeTruthy();
    expect(response.body.length).toBeGreaterThan(0);
    
    // Validate structure of first user
    const firstUser = response.body[0];
    expect(firstUser).toHaveProperty('id');
    expect(firstUser).toHaveProperty('name');
    expect(firstUser).toHaveProperty('email');
    expect(firstUser).toHaveProperty('address');
    
    console.log(`✅ GET /users returned ${response.body.length} users`);
  });

  test('GET /users/:id - Should return specific user', async () => {
    const userId = 1;
    const response = await apiHelper.get(`/users/${userId}`);
    
    expect(response.status).toBe(200);
    expect(response.body.id).toBe(userId);
    expect(response.body.name).toBe('Leanne Graham');
    expect(response.body.email).toBe('Sincere@april.biz');
    
    console.log(`✅ GET /users/${userId} returned user: ${response.body.name}`);
  });

  test('POST /users - Should create new user', async () => {
    const newUser = DataGenerator.randomUser();
    
    const response = await apiHelper.post('/users', newUser);
    
    expect(response.status).toBe(201);
    expect(response.body).toHaveProperty('id');
    expect(response.body.name).toBe(newUser.name);
    expect(response.body.email).toBe(newUser.email);
    
    console.log(`✅ POST /users created user with ID: ${response.body.id}`);
  });

  test('PUT /users/:id - Should update existing user', async () => {
    const userId = 1;
    const updatedUser = {
      name: 'Updated Name',
      email: 'updated@email.com'
    };
    
    const response = await apiHelper.put(`/users/${userId}`, updatedUser);
    
    expect(response.status).toBe(200);
    expect(response.body.name).toBe(updatedUser.name);
    expect(response.body.email).toBe(updatedUser.email);
    
    console.log(`✅ PUT /users/${userId} updated successfully`);
  });

  test('DELETE /users/:id - Should delete user', async () => {
    const userId = 1;
    const response = await apiHelper.delete(`/users/${userId}`);
    
    expect(response.status).toBe(200);
    
    console.log(`✅ DELETE /users/${userId} successful`);
  });
});