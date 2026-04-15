describe('Login Feature', () => {

  beforeEach(() => {
    cy.visit('/')
  })

  it('should login with valid credentials', () => {
    cy.get('#user-name').type('standard_user')
    cy.get('#password').type('secret_sauce')
    cy.get('#login-button').click()
    cy.url().should('include', '/inventory')
  })

  it('should show error with wrong password', () => {
    cy.get('#user-name').type('standard_user')
    cy.get('#password').type('wrong_password')
    cy.get('#login-button').click()
    cy.get('[data-test="error"]').should('be.visible')
  })

})