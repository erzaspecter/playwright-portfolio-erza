package com.modena.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.util.List;

/**
 * Page Object untuk Modal/Popup Registrasi dan Klaim di CIAO Modena
 * 
 * Berdasarkan website https://ciao.modena.com/id:
 * - Fitur registrasi produk (untuk garansi)
 * - Fitur klaim cashback
 * - Fitur klaim free gift
 * - Semua dalam bentuk modal/popup yang muncul saat klik tombol
 */
public class RegistrationPage extends BasePage {
    
    // ==================== MODAL / POPUP LOCATORS ====================
    
    @FindBy(css = ".modal, .popup, .dialog, [role='dialog']")
    private WebElement registrationModal;
    
    @FindBy(css = ".modal-title, .popup-title, .dialog-title")
    private WebElement modalTitle;
    
    @FindBy(css = ".close-modal, .btn-close, [aria-label='Close']")
    private WebElement closeButton;
    
    // ==================== REGISTRATION FORM LOCATORS ====================
    
    // Personal Info
    @FindBy(css = "input[name='name'], input#name, input[placeholder*='nama']")
    private WebElement nameInput;
    
    @FindBy(css = "input[name='email'], input#email, input[type='email']")
    private WebElement emailInput;
    
    @FindBy(css = "input[name='phone'], input#phone, input[placeholder*='telepon'], input[placeholder*='hp']")
    private WebElement phoneInput;
    
    @FindBy(css = "input[name='address'], input#address, input[placeholder*='alamat']")
    private WebElement addressInput;
    
    // Product Info
    @FindBy(css = "select[name='product_type'], select#product_type")
    private WebElement productTypeSelect;
    
    @FindBy(css = "input[name='serial_number'], input#serial_number, input[placeholder*='serial'], input[placeholder*='nomor seri']")
    private WebElement serialNumberInput;
    
    @FindBy(css = "input[name='purchase_date'], input#purchase_date, input[type='date'], input[placeholder*='tanggal']")
    private WebElement purchaseDateInput;
    
    @FindBy(css = "input[name='store_name'], input#store_name, input[placeholder*='toko']")
    private WebElement storeNameInput;
    
    // File Upload
    @FindBy(css = "input[type='file'][name='invoice'], input#invoice")
    private WebElement invoiceUpload;
    
    @FindBy(css = "input[type='file'][name='ktp'], input#ktp, input[placeholder*='ktp']")
    private WebElement idCardUpload;
    
    // Cashback Form
    @FindBy(css = "select[name='bank'], select#bank")
    private WebElement bankSelect;
    
    @FindBy(css = "input[name='account_number'], input#account_number, input[placeholder*='rekening']")
    private WebElement accountNumberInput;
    
    @FindBy(css = "input[name='account_holder'], input#account_holder, input[placeholder*='nama rekening']")
    private WebElement accountHolderInput;
    
    // Checkbox & Submit
    @FindBy(css = "input[type='checkbox'], .agree-checkbox")
    private WebElement agreeCheckbox;
    
    @FindBy(css = "button[type='submit'], .submit-btn, .btn-primary")
    private WebElement submitButton;
    
    // Messages
    @FindBy(css = ".success-message, .alert-success, .toast-success")
    private WebElement successMessage;
    
    @FindBy(css = ".error-message, .alert-danger, .toast-error")
    private WebElement errorMessage;
    
    @FindBy(css = ".validation-error, .field-error, .invalid-feedback")
    private List<WebElement> validationErrors;
    
    // ==================== CONSTRUCTOR ====================
    
    public RegistrationPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    
    // ==================== MODAL HANDLING ====================
    
    /**
     * Cek apakah modal registrasi terbuka
     */
    public boolean isModalDisplayed() {
        try {
            return waitUtil.isElementDisplayedWithWait(By.cssSelector(".modal, .popup, [role='dialog']"));
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Mendapatkan judul modal
     */
    public String getModalTitle() {
        return waitUtil.getTextWhenReady(By.cssSelector(".modal-title, .popup-title"));
    }
    
    /**
     * Menutup modal
     */
    public RegistrationPage closeModal() {
        if (isModalDisplayed()) {
            waitUtil.clickWhenReady(By.cssSelector(".close-modal, .btn-close"));
            waitUtil.hardWait(500);
        }
        return this;
    }
    
    /**
     * Tunggu modal selesai loading
     */
    public RegistrationPage waitForModalToLoad() {
        waitUtil.waitForElementVisible(By.cssSelector(".modal, .popup"));
        waitUtil.hardWait(500);
        return this;
    }
    
    // ==================== FORM FILLING METHODS ====================
    
    /**
     * Isi nama lengkap
     */
    public RegistrationPage enterName(String name) {
        if (name != null && !name.isEmpty()) {
            waitUtil.sendKeysWhenReady(By.cssSelector("input[name='name'], input#name"), name);
        }
        return this;
    }
    
    /**
     * Isi email
     */
    public RegistrationPage enterEmail(String email) {
        if (email != null && !email.isEmpty()) {
            waitUtil.sendKeysWhenReady(By.cssSelector("input[name='email'], input#email, input[type='email']"), email);
        }
        return this;
    }
    
    /**
     * Isi nomor telepon/HP
     */
    public RegistrationPage enterPhone(String phone) {
        if (phone != null && !phone.isEmpty()) {
            waitUtil.sendKeysWhenReady(By.cssSelector("input[name='phone'], input#phone, input[placeholder*='hp']"), phone);
        }
        return this;
    }
    
    /**
     * Isi alamat
     */
    public RegistrationPage enterAddress(String address) {
        if (address != null && !address.isEmpty()) {
            waitUtil.sendKeysWhenReady(By.cssSelector("input[name='address'], input#address"), address);
        }
        return this;
    }
    
    /**
     * Pilih tipe produk
     */
    public RegistrationPage selectProductType(String productType) {
        if (productType != null && !productType.isEmpty()) {
            Select select = new Select(waitUtil.waitForElementVisible(By.cssSelector("select[name='product_type']")));
            select.selectByVisibleText(productType);
        }
        return this;
    }
    
    /**
     * Isi nomor serial produk
     */
    public RegistrationPage enterSerialNumber(String serialNumber) {
        if (serialNumber != null && !serialNumber.isEmpty()) {
            waitUtil.sendKeysWhenReady(By.cssSelector("input[name='serial_number'], input[placeholder*='serial']"), serialNumber);
        }
        return this;
    }
    
    /**
     * Isi tanggal pembelian
     */
    public RegistrationPage enterPurchaseDate(String date) {
        if (date != null && !date.isEmpty()) {
            waitUtil.sendKeysWhenReady(By.cssSelector("input[name='purchase_date'], input[type='date']"), date);
        }
        return this;
    }
    
    /**
     * Isi nama toko
     */
    public RegistrationPage enterStoreName(String storeName) {
        if (storeName != null && !storeName.isEmpty()) {
            waitUtil.sendKeysWhenReady(By.cssSelector("input[name='store_name'], input[placeholder*='toko']"), storeName);
        }
        return this;
    }
    
    /**
     * Upload file invoice
     */
    public RegistrationPage uploadInvoice(String filePath) {
        if (filePath != null && !filePath.isEmpty()) {
            WebElement upload = waitUtil.waitForElementVisible(By.cssSelector("input[type='file'][name='invoice']"));
            upload.sendKeys(filePath);
            waitUtil.hardWait(1000);
        }
        return this;
    }
    
    /**
     * Upload file KTP
     */
    public RegistrationPage uploadIdCard(String filePath) {
        if (filePath != null && !filePath.isEmpty()) {
            WebElement upload = waitUtil.waitForElementVisible(By.cssSelector("input[type='file'][name='ktp']"));
            upload.sendKeys(filePath);
            waitUtil.hardWait(1000);
        }
        return this;
    }
    
    /**
     * Pilih bank untuk cashback
     */
    public RegistrationPage selectBank(String bankName) {
        if (bankName != null && !bankName.isEmpty()) {
            Select select = new Select(waitUtil.waitForElementVisible(By.cssSelector("select[name='bank']")));
            select.selectByVisibleText(bankName);
        }
        return this;
    }
    
    /**
     * Isi nomor rekening
     */
    public RegistrationPage enterAccountNumber(String accountNumber) {
        if (accountNumber != null && !accountNumber.isEmpty()) {
            waitUtil.sendKeysWhenReady(By.cssSelector("input[name='account_number']"), accountNumber);
        }
        return this;
    }
    
    /**
     * Isi nama pemilik rekening
     */
    public RegistrationPage enterAccountHolder(String accountHolder) {
        if (accountHolder != null && !accountHolder.isEmpty()) {
            waitUtil.sendKeysWhenReady(By.cssSelector("input[name='account_holder']"), accountHolder);
        }
        return this;
    }
    
    /**
     * Centang persetujuan
     */
    public RegistrationPage agreeToTerms() {
        WebElement checkbox = waitUtil.waitForElementClickable(By.cssSelector("input[type='checkbox']"));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        return this;
    }
    
    /**
     * Submit form
     */
    public RegistrationPage submitForm() {
        waitUtil.clickWhenReady(By.cssSelector("button[type='submit'], .submit-btn"));
        waitUtil.waitForPageLoad();
        return this;
    }
    
    // ==================== MESSAGES & VALIDATION ====================
    
    /**
     * Cek apakah ada pesan sukses
     */
    public boolean isSuccessMessageDisplayed() {
        return waitUtil.isElementDisplayedWithWait(By.cssSelector(".success-message, .alert-success"));
    }
    
    /**
     * Dapatkan pesan sukses
     */
    public String getSuccessMessage() {
        return waitUtil.getTextWhenReady(By.cssSelector(".success-message, .alert-success"));
    }
    
    /**
     * Cek apakah ada pesan error
     */
    public boolean isErrorMessageDisplayed() {
        return waitUtil.isElementDisplayedWithWait(By.cssSelector(".error-message, .alert-danger"));
    }
    
    /**
     * Dapatkan pesan error
     */
    public String getErrorMessage() {
        return waitUtil.getTextWhenReady(By.cssSelector(".error-message, .alert-danger"));
    }
    
    /**
     * Dapatkan jumlah validation errors
     */
    public int getValidationErrorCount() {
        return validationErrors.size();
    }
    
    /**
     * Dapatkan semua validation errors text
     */
    public List<String> getValidationErrorsText() {
        return validationErrors.stream()
                .map(WebElement::getText)
                .toList();
    }
    
    // ==================== FLUENT FORM FILLING ====================
    
    /**
     * Fluent method untuk registrasi produk
     */
    public RegistrationPage fillProductRegistrationForm(
            String name, String email, String phone,
            String productType, String serialNumber,
            String purchaseDate, String invoicePath) {
        
        return this
                .enterName(name)
                .enterEmail(email)
                .enterPhone(phone)
                .selectProductType(productType)
                .enterSerialNumber(serialNumber)
                .enterPurchaseDate(purchaseDate)
                .uploadInvoice(invoicePath)
                .agreeToTerms();
    }
    
    /**
     * Fluent method untuk klaim cashback
     */
    public RegistrationPage fillCashbackForm(
            String name, String email, String phone,
            String serialNumber, String bankName,
            String accountNumber, String accountHolder,
            String invoicePath) {
        
        return this
                .enterName(name)
                .enterEmail(email)
                .enterPhone(phone)
                .enterSerialNumber(serialNumber)
                .selectBank(bankName)
                .enterAccountNumber(accountNumber)
                .enterAccountHolder(accountHolder)
                .uploadInvoice(invoicePath)
                .agreeToTerms();
    }
    
    /**
     * Clear semua form fields
     */
    public RegistrationPage clearForm() {
        // Clear all inputs (implementasi sederhana)
        try { nameInput.clear(); } catch (Exception e) {}
        try { emailInput.clear(); } catch (Exception e) {}
        try { phoneInput.clear(); } catch (Exception e) {}
        try { serialNumberInput.clear(); } catch (Exception e) {}
        return this;
    }
}