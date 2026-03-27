package com.modena.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.modena.utils.WaitUtil;  // ← Tambahkan import ini


import java.util.List;

/**
 * Page Object untuk Landing Page CIAO Modena
 * URL: https://ciao.modena.com/id
 */
public class LandingPage extends BasePage {
    
    // ==================== LOCATORS ====================
    
    // Header/Navigation
    @FindBy(css = "nav, .navbar, header")
    private WebElement navigationBar;
    
    @FindBy(css = "a[href*='login'], .login-btn, .signin-btn")
    private WebElement loginButton;
    
    @FindBy(css = ".logo, .brand-logo")
    private WebElement logo;
    
    // Main Content
    @FindBy(css = "h1, .hero-title, .main-title")
    private WebElement mainTitle;
    
    @FindBy(css = ".hero-section, .banner-section")
    private WebElement heroSection;
    
    // Feature Cards
    @FindBy(css = ".feature-card, .benefit-card, .service-card")
    private List<WebElement> featureCards;
    
    @FindBy(xpath = "//*[contains(text(),'Extra Garansi')]")
    private WebElement extraGaransiFeature;
    
    @FindBy(xpath = "//*[contains(text(),'Klaim Cashback')]")
    private WebElement klaimCashbackFeature;
    
    @FindBy(xpath = "//*[contains(text(),'Pendaftaran Produk')]")
    private WebElement pendaftaranProdukFeature;
    
    @FindBy(xpath = "//*[contains(text(),'Klaim Free Gift')]")
    private WebElement klaimFreeGiftFeature;
    
    // Tracking Section
    @FindBy(css = ".tracking-section, .status-section")
    private WebElement trackingSection;
    
    @FindBy(css = "input[placeholder*='email'], input[type='email']")
    private WebElement trackingEmailInput;
    
    @FindBy(css = "input[placeholder*='hp'], input[type='tel']")
    private WebElement trackingPhoneInput;
    
    @FindBy(css = "button[type='submit'], .track-btn, .lacak-btn")
    private WebElement trackButton;
    
    // FAQ Section
    @FindBy(css = ".faq-section, .accordion-section")
    private WebElement faqSection;
    
    @FindBy(css = ".faq-item, .accordion-item")
    private List<WebElement> faqItems;
    
    @FindBy(css = ".faq-question, .accordion-header")
    private List<WebElement> faqQuestions;
    
    // Footer
    @FindBy(css = "footer")
    private WebElement footer;
    
    @FindBy(css = "a[href*='contact'], .contact-link")
    private WebElement contactLink;
    
    // CTA Buttons
    @FindBy(css = ".cta-button, .daftar-btn, .register-btn")
    private WebElement registerButton;
    
    // Success/Error Messages
    @FindBy(css = ".success-message, .alert-success")
    private WebElement successMessage;
    
    @FindBy(css = ".error-message, .alert-danger")
    private WebElement errorMessage;
    
    // ==================== CONSTRUCTOR ====================
    
    public LandingPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    // ==================== PAGE ACTIONS ====================
    
    /**
     * Verifikasi bahwa halaman landing sudah dimuat dengan benar
     */
    public boolean isLandingPageLoaded() {
    try {
        waitUtil.waitForPageLoad();
        return isElementDisplayed(By.cssSelector("nav, .navbar, header")) ||
               isElementDisplayed(By.cssSelector(".hero-section, .banner-section"));
    } catch (Exception e) {
        return false;
    }
}

    
    /**
     * Mendapatkan title utama halaman
     */
    public String getMainTitle() {
        return waitUtil.getTextWhenReady(By.cssSelector("h1, .hero-title, .main-title"));
    }
    
    /**
     * Klik tombol Login
     */
    public void clickLogin() {
        waitUtil.clickWhenReady(By.cssSelector("a[href*='login'], .login-btn, .signin-btn"));
    }
    
    /**
     * Klik tombol Registrasi/Daftar
     */
    public void clickRegister() {
        waitUtil.clickWhenReady(By.cssSelector(".cta-button, .daftar-btn, .register-btn"));
    }
    
    // ==================== FEATURE CARDS METHODS ====================
    
    /**
     * Mendapatkan jumlah feature cards yang ditampilkan
     */
    public int getFeatureCardsCount() {
        return featureCards.size();
    }
    
    /**
     * Verifikasi apakah feature "Extra Garansi" ditampilkan
     */
    public boolean isExtraGaransiDisplayed() {
        return isElementDisplayed(By.xpath("//*[contains(text(),'Extra Garansi')]"));
    }
    
    /**
     * Verifikasi apakah feature "Klaim Cashback" ditampilkan
     */
    public boolean isKlaimCashbackDisplayed() {
        return isElementDisplayed(By.xpath("//*[contains(text(),'Klaim Cashback')]"));
    }
    
    /**
     * Mendapatkan teks dari semua feature cards
     */
    public List<String> getFeatureCardsText() {
        return featureCards.stream()
                .map(WebElement::getText)
                .toList();
    }
    
    // ==================== TRACKING SECTION METHODS ====================
    
    /**
     * Scroll ke section tracking
     */
    public LandingPage scrollToTrackingSection() {
        scrollToElement(trackingSection);
        return this;
    }
    
    /**
     * Melakukan tracking menggunakan email
     */
    public TrackingPage trackByEmail(String email) {
        waitUtil.sendKeysWhenReady(By.cssSelector("input[placeholder*='email'], input[type='email']"), email);
        waitUtil.clickWhenReady(By.cssSelector("button[type='submit'], .track-btn, .lacak-btn"));
        return new TrackingPage(driver);
    }
    
    /**
     * Melakukan tracking menggunakan nomor HP
     */
    public TrackingPage trackByPhone(String phoneNumber) {
        waitUtil.sendKeysWhenReady(By.cssSelector("input[placeholder*='hp'], input[type='tel']"), phoneNumber);
        waitUtil.clickWhenReady(By.cssSelector("button[type='submit'], .track-btn, .lacak-btn"));
        return new TrackingPage(driver);
    }
    
    /**
     * Verifikasi apakah form tracking tersedia
     */
    public boolean isTrackingFormAvailable() {
        return isElementDisplayed(By.cssSelector("input[placeholder*='email']")) ||
               isElementDisplayed(By.cssSelector("input[placeholder*='hp']"));
    }
    
    /**
     * Mendapatkan placeholder text dari input email
     */
    public String getEmailInputPlaceholder() {
        WebElement element = waitUtil.waitForElementVisible(By.cssSelector("input[placeholder*='email'], input[type='email']"));
        return element.getAttribute("placeholder");
    }
    
    // ==================== FAQ SECTION METHODS ====================
    
    /**
     * Scroll ke section FAQ
     */
    public LandingPage scrollToFaqSection() {
        scrollToElement(faqSection);
        return this;
    }
    
    /**
     * Mendapatkan jumlah pertanyaan FAQ
     */
    public int getFaqCount() {
        waitUtil.waitForElementVisible(By.cssSelector(".faq-section, .accordion-section"));
        return faqItems.size();
    }
    
    /**
     * Membuka pertanyaan FAQ berdasarkan index
     */
    public LandingPage openFaqByIndex(int index) {
        if (index < faqQuestions.size()) {
            waitUtil.clickWhenReady(By.cssSelector(".faq-question, .accordion-header"));
        }
        return this;
    }
    
    /**
     * Mendapatkan teks pertanyaan FAQ
     */
    public List<String> getFaqQuestionsText() {
        return faqQuestions.stream()
                .map(WebElement::getText)
                .toList();
    }
    
    /**
     * Mendapatkan jawaban FAQ berdasarkan pertanyaan
     */
    public String getFaqAnswer(String question) {
        // Implementasi tergantung struktur FAQ (accordion)
        return "";
    }
    
    // ==================== FOOTER METHODS ====================
    
    /**
     * Scroll ke footer
     */
    public LandingPage scrollToFooter() {
        scrollToElement(footer);
        return this;
    }
    
    /**
     * Klik link contact
     */
    public void clickContactLink() {
        waitUtil.clickWhenReady(By.cssSelector("a[href*='contact'], .contact-link"));
    }
    
    // ==================== VALIDATION METHODS ====================
    
    /**
     * Verifikasi apakah logo ditampilkan
     */
    public boolean isLogoDisplayed() {
        return isElementDisplayed(By.cssSelector(".logo, .brand-logo"));
    }
    
    /**
     * Verifikasi apakah hero section ditampilkan
     */
    public boolean isHeroSectionDisplayed() {
        return isElementDisplayed(By.cssSelector(".hero-section, .banner-section"));
    }
    
    /**
     * Verifikasi apakah success message ditampilkan
     */
    public boolean isSuccessMessageDisplayed() {
        return isElementDisplayed(By.cssSelector(".success-message, .alert-success"));
    }
    
    /**
     * Mendapatkan text success message
     */
    public String getSuccessMessage() {
        return waitUtil.getTextWhenReady(By.cssSelector(".success-message, .alert-success"));
    }
    
    /**
     * Verifikasi apakah error message ditampilkan
     */
    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(By.cssSelector(".error-message, .alert-danger"));
    }
    
    /**
     * Mendapatkan text error message
     */
    public String getErrorMessage() {
        return waitUtil.getTextWhenReady(By.cssSelector(".error-message, .alert-danger"));
    }
    
    // ==================== NAVIGATION METHODS ====================
    
    /**
     * Navigate ke halaman tertentu
     */
    public void navigateTo(String url) {
        driver.get(url);
        waitUtil.waitForPageLoad();
    }
    
    /**
     * Refresh halaman
     */
    public LandingPage refresh() {
        waitUtil.refreshAndWait();
        return this;
    }
    
    // ==================== HELPER METHODS ====================
    
    /**
     * Cek apakah element tertentu ada di halaman
     */
    private boolean isElementDisplayed(By locator) {
        return waitUtil.isElementDisplayedWithWait(locator);
    }
    
    /**
     * Scroll ke element tertentu
     */
    private void scrollToElement(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
        waitUtil.hardWait(500);
    }
}