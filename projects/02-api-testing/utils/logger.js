// helpers/data-generator.js
class DataGenerator {
  static randomEmail() {
    const domains = ['test.com', 'example.com', 'demo.com'];
    const randomDomain = domains[Math.floor(Math.random() * domains.length)];
    return `user_${Date.now()}@${randomDomain}`;
  }

  static randomUsername() {
    return `user_${Math.random().toString(36).substring(7)}`;
  }

  static randomPost() {
    return {
      title: `Test Post ${Date.now()}`,
      body: 'This is a test post body content',
      userId: Math.floor(Math.random() * 10) + 1
    };
  }

  static randomUser() {
    return {
      name: `Test User ${Math.random().toString(36).substring(7)}`,
      username: this.randomUsername(),
      email: this.randomEmail(),
      phone: `+62${Math.floor(Math.random() * 1000000000)}`
    };
  }
}

module.exports = DataGenerator;