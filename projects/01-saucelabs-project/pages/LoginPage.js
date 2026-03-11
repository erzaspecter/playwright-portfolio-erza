class LoginPage {
  constructor(page) {
    this.page = page
    this.usernameInput = '#user-name'
    this.passwordInput = '#password'
    this.loginButton = '#login-button'
    this.errorMessage = '[data-test="error"]'
  }

  async login(username, password) {
    await this.page.fill(this.usernameInput, username)
    await this.page.fill(this.passwordInput, password)
    await this.page.click(this.loginButton)
  }

  async getErrorMessage() {
    return this.page.textContent(this.errorMessage)
  }
}

module.exports = { LoginPage }