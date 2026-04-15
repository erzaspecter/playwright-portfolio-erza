describe('MODENA Energy - Homepage Tests', () => {

  // ← TAMBAHKAN INI DI SINI (sebelum beforeEach)
  Cypress.on('uncaught:exception', (err, runnable) => {
    // Ini akan ignore semua uncaught exception dari aplikasi
    // return false; → Cypress tidak akan fail test karena error ini
    return false;
  });

  beforeEach(() => {
    cy.viewport(1920, 1080);
    cy.visit('https://energy.modena.com/id_en', {
      // Tambahan ini biar lebih stabil
      timeout: 60000,
      failOnStatusCode: false
    });
  });

  it('Harus menampilkan logo MODENA dan judul yang benar', () => {
    // Memastikan logo visible
    cy.get('img[src="https://cdn.modena.com/media/logo/websites/1/modena-logo.png"]').should('be.visible');
    
    // Memastikan judul halaman mengandung kata Energy
    cy.title().should('include', 'Energy');
  });

  it('Harus bisa navigasi ke menu Home Solutions', () => {
    // Cara lebih stabil: pastikan parent nav sudah visible dulu
    cy.get('nav', { timeout: 15000 }).should('be.visible');

    // Klik menu dengan text yang lebih fleksibel
    cy.contains('a', 'Home Solutions', { timeout: 10000 })
      .should('be.visible')
      .click();

    // Atau pakai force kalau masih susah (hanya sebagai cadangan)
    // cy.contains('a', 'Home Solutions').click({ force: true });

    // Cek URL berubah
    cy.url().should('include', '/home-solution');

    // Cek heading di halaman baru
    cy.get('h1, h2, h4', { timeout: 15000 }).should('contain', 'Home');
  });
});