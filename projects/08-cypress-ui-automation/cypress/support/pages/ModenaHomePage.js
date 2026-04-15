// cypress/pages/ModenaHomePage.js

export default class ModenaHomePage {

  // Selectors
  selectors = {
    logo: 'img[src*="modena-logo"], .navbar-brand img, img[alt*="MODENA"]',
    nav: 'nav',
    homeSolutionsLink: 'a:contains("Home Solutions")',
    solutionsLink: 'a:contains("Solutions")',   // kalau ada
    pageTitle: 'h1, h4.text-\\[32px\\]'
  };

  // Actions / Methods
  visit() {
    cy.viewport(1920, 1080);
    cy.visit('https://energy.modena.com/id_en', {
      timeout: 60000,
      failOnStatusCode: false
    });
    cy.wait(3000); // atau tunggu elemen penting
  }

  getLogo() {
    return cy.get(this.selectors.logo, { timeout: 15000 });
  }

  clickHomeSolutions() {
    cy.get(this.selectors.nav).should('be.visible');
    return cy.contains('a', 'Home Solutions', { timeout: 10000 })
             .should('be.visible')
             .click();
  }

  // Bisa tambah method lain nanti
  verifyUrlContains(path) {
    cy.url().should('include', path);
  }
}

// Export instance agar bisa langsung dipakai
//export const modenaHomePage = new ModenaHomePage();