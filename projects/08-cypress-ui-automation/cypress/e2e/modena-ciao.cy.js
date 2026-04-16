describe('Ciao MODENA - Service & Support Portal', () => {
  
  beforeEach(() => {
    // Kunjungi website Ciao MODENA
    cy.visit('https://ciao.modena.com/en');
  });

  it('should display the main landing page correctly', () => {
    // Memastikan logo atau elemen identitas ada
    cy.get('img[alt*="MODENA"]').should('be.visible');
    cy.contains('Product Registration and Claims').should('be.visible');
  });

  it('should navigate to Login page and show validation', () => {
    // Contoh skenario gagal login untuk cek validasi
    cy.get('input[name="email"]').type('wrong-user@mail.com');
    cy.get('input[name="password"]').type('password123');
    cy.get('button[type="submit"]').click();

    // Sesuaikan selector error message dengan yang ada di web asli
    cy.get('.alert-danger').should('be.visible');
  });

  it('should check for Service Request tracking element', () => {
    // Memastikan fitur track service tersedia di halaman
    cy.contains('Track Your Service').should('exist');
  });
});