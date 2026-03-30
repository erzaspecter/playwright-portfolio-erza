# 🌐 API Testing Project with Playwright

## 📋 Overview
Comprehensive API testing project demonstrating various testing techniques using Playwright's built-in API testing capabilities.

## 🎯 Features Tested

### User API Tests
- ✅ GET all users
- ✅ GET user by ID
- ✅ POST create new user
- ✅ PUT update existing user
- ✅ DELETE user
- ✅ Input validation

### Posts API Tests
- ✅ GET posts with filtering
- ✅ GET comments for posts
- ✅ POST create posts
- ✅ Data validation
- ✅ Relationship testing

### Authentication Tests
- ✅ Login with valid credentials
- ✅ Failed login handling
- ✅ Token validation
- ✅ Protected endpoints

### Error Handling
- ✅ 404 Not Found
- ✅ 400 Bad Request
- ✅ Timeout handling
- ✅ Invalid data formats

## 🛠️ Tech Stack
- **Playwright** - API Testing Framework
- **Joi** - Schema Validation
- **Allure** - Test Reporting
- **GitHub Actions** - CI/CD Integration

## 📊 Test Statistics
- **Total Tests**: 25+ API test scenarios
- **Coverage**: CRUD operations, edge cases, negative tests
- **Execution Time**: < 30 seconds
- **Reliability**: 100% flake-free tests

## 🚀 Quick Start

```bash
# Install dependencies
npm install

# Run all API tests
npm run test:api

# Run specific test suite
npm run test:users
npm run test:posts

# Generate Allure report
npm run allure:generate
npm run allure:open