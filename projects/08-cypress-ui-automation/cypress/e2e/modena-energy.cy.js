//import ModenaHomePage, { modenaHomePage } from '../../support/pages/ModenaHomePage';

// const ModenaHomePage = require('../support/pages/ModenaHomePage');
// const { modenaHomePage } = require('../support/pages/ModenaHomePage');

import ModenaHomePage from '../support/pages/ModenaHomePage.js';

describe('Home Page Test', () => {
  it('should load homepage', () => {
    const home = new ModenaHomePage();
    home.visit();
  });
});


describe('Home Page Test', () => {
  it('should load homepage', () => {
    const home = new ModenaHomePage();
    home.visit();
  });
});

describe('MODENA Energy - Homepage Tests', () => {

  let modenaHomePage;
  Cypress.on('uncaught:exception', () => false);

    
  beforeEach(() => {
    modenaHomePage = new ModenaHomePage();
    modenaHomePage.visit();
  });

  it('Harus menampilkan logo MODENA dan judul yang benar', () => {
    modenaHomePage.getLogo().should('be.visible');
    cy.title().should('include', 'Energy');
  });

  it('Harus bisa navigasi ke menu Home Solutions', () => {
    modenaHomePage.clickHomeSolutions();
    modenaHomePage.verifyUrlContains('/home-solution');
    cy.get('h1, h4', { timeout: 15000 }).should('contain', 'Home');
  });

});