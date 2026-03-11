// tests/posts-api.spec.js
const { test, expect } = require('@playwright/test');
const ApiHelper = require('../helpers/api-helper');
const DataGenerator = require('../helpers/data-generator');

test.describe('Posts API Advanced Testing', () => {
  let apiHelper;

  test.beforeEach(async ({ request }) => {
    apiHelper = new ApiHelper(request);
  });

  test('GET /posts - Should filter posts by userId', async () => {
    const userId = 1;
    const response = await apiHelper.get('/posts', { userId: userId });
    
    expect(response.status).toBe(200);
    expect(Array.isArray(response.body)).toBeTruthy();
    
    // Verify all posts belong to userId 1
    response.body.forEach(post => {
      expect(post.userId).toBe(userId);
    });
    
    console.log(`✅ Found ${response.body.length} posts for user ${userId}`);
  });

  test('GET /posts/:id/comments - Should get comments for a post', async () => {
    const postId = 1;
    const response = await apiHelper.get(`/posts/${postId}/comments`);
    
    expect(response.status).toBe(200);
    expect(Array.isArray(response.body)).toBeTruthy();
    
    // Verify all comments belong to postId 1
    response.body.forEach(comment => {
      expect(comment.postId).toBe(postId);
    });
    
    console.log(`✅ Found ${response.body.length} comments for post ${postId}`);
  });

  test('POST /posts - Create post with validation', async () => {
    // Test data dengan berbagai skenario
    const testScenarios = [
      { title: 'Valid Post', body: 'Content', userId: 1, expectedStatus: 201 },
      { title: '', body: 'Content', userId: 1, expectedStatus: 400 }, // Empty title
      { title: 'Test', body: '', userId: 1, expectedStatus: 400 }, // Empty body
      { title: 'Test', body: 'Content', userId: null, expectedStatus: 400 }, // Invalid userId
    ];

    for (const scenario of testScenarios) {
      const response = await apiHelper.post('/posts', {
        title: scenario.title,
        body: scenario.body,
        userId: scenario.userId
      });
      
      expect(response.status).toBe(scenario.expectedStatus);
      
      if (scenario.expectedStatus === 201) {
        expect(response.body).toHaveProperty('id');
      }
    }
    
    console.log('✅ POST /posts validation tests passed');
  });
});