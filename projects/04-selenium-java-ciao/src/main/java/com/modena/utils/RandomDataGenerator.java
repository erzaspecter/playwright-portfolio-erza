package com.modena.utils;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Utility class untuk menghasilkan data acak untuk testing
 * Berguna untuk:
 * - Mengisi form registrasi dengan data unik
 * - Menghindari duplicate data conflict
 * - Membuat test data yang valid
 * - Negative testing dengan data invalid
 */
public class RandomDataGenerator {
    
    private static final SecureRandom random = new SecureRandom();
    
    // Data pools untuk random generation
    private static final String[] FIRST_NAMES = {
        "Budi", "Siti", "Agus", "Dewi", "Joko", "Rina", "Hendra", "Lina",
        "Andi", "Maya", "Rudi", "Tina", "Eko", "Nina", "Dodi", "Rani",
        "Irfan", "Sari", "Bayu", "Diana", "Rizki", "Amelia", "Fajar", "Citra"
    };
    
    private static final String[] LAST_NAMES = {
        "Santoso", "Wijaya", "Kusuma", "Pratama", "Surya", "Putri", "Hidayat",
        "Nugroho", "Wahyuni", "Setiawan", "Lestari", "Gunawan", "Permana",
        "Wulandari", "Saputra", "Handayani", "Hartono", "Yulianti"
    };
    
    private static final String[] PHONE_PREFIXES = {
        "0812", "0813", "0821", "0822", "0852", "0853", "0877", "0878", "0881", "0882"
    };
    
    private static final String[] DOMAINS = {
        "gmail.com", "yahoo.com", "hotmail.com", "outlook.com", "modena.com", 
        "email.com", "mail.com", "test.com", "example.com"
    };
    
    private static final String[] PRODUCT_TYPES = {
        "Kompor Gas", "Kompor Listrik", "Hood", "Oven", "Dispenser", 
        "Water Heater", "Rice Cooker", "Blender", "Mixer", "Microwave"
    };
    
    private static final String[] BANKS = {
        "BCA", "Mandiri", "BNI", "BRI", "CIMB Niaga", "Danamon", 
        "Permata", "Maybank", "OCBC NISP", "Bank Mega"
    };
    
    private static final String[] CITIES = {
        "Jakarta", "Surabaya", "Bandung", "Medan", "Semarang", "Yogyakarta",
        "Denpasar", "Makassar", "Palembang", "Balikpapan", "Bekasi", "Tangerang"
    };
    
    private static final String[] ADDRESSES = {
        "Jl. Sudirman No. 123", "Jl. Thamrin No. 45", "Jl. Gatot Subroto No. 67",
        "Jl. Ahmad Yani No. 89", "Jl. Pahlawan No. 10", "Jl. Diponegoro No. 20",
        "Perumahan Garden City Blok A5", "Apartemen Green Park Lantai 10",
        "Komplek Ruko Permata No. 15", "Jl. Merdeka No. 5"
    };
    
    // ==================== BASIC RANDOM GENERATORS ====================
    
    /**
     * Generate random integer dalam range
     */
    public static int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
    
    /**
     * Generate random long
     */
    public static long randomLong(long min, long max) {
        return ThreadLocalRandom.current().nextLong(min, max + 1);
    }
    
    /**
     * Generate random boolean
     */
    public static boolean randomBoolean() {
        return random.nextBoolean();
    }
    
    /**
     * Pilih random item dari array
     */
    public static <T> T randomFromArray(T[] array) {
        return array[random.nextInt(array.length)];
    }
    
    /**
     * Pilih random item dari list
     */
    public static <T> T randomFromList(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }
    
    // ==================== NAME GENERATORS ====================
    
    /**
     * Generate random nama lengkap
     */
    public static String generateFullName() {
        return randomFromArray(FIRST_NAMES) + " " + randomFromArray(LAST_NAMES);
    }
    
    /**
     * Generate random first name
     */
    public static String generateFirstName() {
        return randomFromArray(FIRST_NAMES);
    }
    
    /**
     * Generate random last name
     */
    public static String generateLastName() {
        return randomFromArray(LAST_NAMES);
    }
    
    // ==================== EMAIL GENERATORS ====================
    
    /**
     * Generate random email
     */
    public static String generateEmail() {
        String name = generateFirstName().toLowerCase();
        String domain = randomFromArray(DOMAINS);
        int randomNum = randomInt(1, 999);
        return name + "." + randomNum + "@" + domain;
    }
    
    /**
     * Generate random email dengan domain tertentu
     */
    public static String generateEmail(String domain) {
        String name = generateFirstName().toLowerCase();
        int randomNum = randomInt(1, 999);
        return name + "." + randomNum + "@" + domain;
    }
    
    /**
     * Generate invalid email format (untuk negative testing)
     */
    public static String generateInvalidEmail() {
        String[] invalidFormats = {
            "invalid-email",
            "test@",
            "@domain.com",
            "test@domain",
            "test@.com",
            "test@domain.",
            "test@domain..com",
            "test space@domain.com",
            "test@domain.c",
            ""
        };
        return randomFromArray(invalidFormats);
    }
    
    // ==================== PHONE GENERATORS ====================
    
    /**
     * Generate random nomor telepon Indonesia
     */
    public static String generatePhoneNumber() {
        String prefix = randomFromArray(PHONE_PREFIXES);
        String number = String.format("%08d", randomInt(0, 99999999));
        return prefix + number;
    }
    
    /**
     * Generate nomor telepon dengan panjang tertentu
     */
    public static String generatePhoneNumber(int length) {
        String prefix = randomFromArray(PHONE_PREFIXES);
        int remaining = length - prefix.length();
        String number = String.format("%0" + remaining + "d", randomInt(0, (int) Math.pow(10, remaining) - 1));
        return prefix + number;
    }
    
    /**
     * Generate invalid phone number (untuk negative testing)
     */
    public static String generateInvalidPhoneNumber() {
        String[] invalidFormats = {
            "123",                    // Terlalu pendek
            "abcdefghij",            // Bukan angka
            "08123456789012345",     // Terlalu panjang
            "+62",                   // Tidak lengkap
            "0812abcde",             // Campuran
            ""                        // Kosong
        };
        return randomFromArray(invalidFormats);
    }
    
    // ==================== ADDRESS GENERATORS ====================
    
    /**
     * Generate random alamat
     */
    public static String generateAddress() {
        return randomFromArray(ADDRESSES);
    }
    
    /**
     * Generate random kota
     */
    public static String generateCity() {
        return randomFromArray(CITIES);
    }
    
    /**
     * Generate random kode pos (5 digit)
     */
    public static String generatePostalCode() {
        return String.format("%05d", randomInt(10000, 99999));
    }
    
    // ==================== PRODUCT GENERATORS ====================
    
    /**
     * Generate random tipe produk
     */
    public static String generateProductType() {
        return randomFromArray(PRODUCT_TYPES);
    }
    
    /**
     * Generate random serial number produk
     * Format: MDN + 10 digit angka acak
     */
    public static String generateSerialNumber() {
        return "MDN" + String.format("%010d", randomInt(1, 999999999));
    }
    
    /**
     * Generate serial number dengan prefix kustom
     */
    public static String generateSerialNumber(String prefix) {
        return prefix + String.format("%010d", randomInt(1, 999999999));
    }
    
    // ==================== DATE GENERATORS ====================
    
    /**
     * Generate random tanggal dalam format yyyy-MM-dd
     */
    public static String generateRandomDate() {
        long minDate = System.currentTimeMillis() - (365L * 24 * 60 * 60 * 1000 * 2); // 2 tahun lalu
        long maxDate = System.currentTimeMillis();
        long randomDate = minDate + (long) (Math.random() * (maxDate - minDate));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(randomDate));
    }
    
    /**
     * Generate tanggal hari ini
     */
    public static String getTodayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }
    
    /**
     * Generate tanggal kemarin
     */
    public static String getYesterdayDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }
    
    /**
     * Generate tanggal besok
     */
    public static String getTomorrowDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }
    
    // ==================== BANK GENERATORS ====================
    
    /**
     * Generate random nama bank
     */
    public static String generateBankName() {
        return randomFromArray(BANKS);
    }
    
    /**
     * Generate random nomor rekening (10-15 digit)
     */
    public static String generateAccountNumber() {
        int length = randomInt(10, 15);
        return String.format("%0" + length + "d", randomInt(1, (int) Math.pow(10, length) - 1));
    }
    
    /**
     * Generate nama pemilik rekening (sama dengan nama)
     */
    public static String generateAccountHolder() {
        return generateFullName();
    }
    
    // ==================== STORE GENERATORS ====================
    
    /**
     * Generate random nama toko
     */
    public static String generateStoreName() {
        String[] stores = {
            "Electronic City", "Best Denki", "Hypermart", "Lotte Mart", "Carrefour",
            "Modena Official Store", "Tokopedia", "Shopee", "Lazada", "Blibli"
        };
        return randomFromArray(stores);
    }
    
    // ==================== STRING GENERATORS ====================
    
    /**
     * Generate random alphanumeric string
     */
    public static String generateRandomString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
    
    /**
     * Generate random numeric string
     */
    public static String generateRandomNumeric(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
    
    // ==================== COMPLEX OBJECT GENERATORS ====================
    
    /**
     * Generate Map berisi data registrasi produk lengkap
     */
    public static Map<String, String> generateProductRegistrationData() {
        Map<String, String> data = new HashMap<>();
        data.put("name", generateFullName());
        data.put("email", generateEmail());
        data.put("phone", generatePhoneNumber());
        data.put("productType", generateProductType());
        data.put("serialNumber", generateSerialNumber());
        data.put("purchaseDate", generateRandomDate());
        data.put("storeName", generateStoreName());
        data.put("address", generateAddress());
        data.put("city", generateCity());
        data.put("postalCode", generatePostalCode());
        return data;
    }
    
    /**
     * Generate Map berisi data cashback
     */
    public static Map<String, String> generateCashbackData() {
        Map<String, String> data = new HashMap<>();
        data.put("name", generateFullName());
        data.put("email", generateEmail());
        data.put("phone", generatePhoneNumber());
        data.put("serialNumber", generateSerialNumber());
        data.put("bankName", generateBankName());
        data.put("accountNumber", generateAccountNumber());
        data.put("accountHolder", generateFullName());
        return data;
    }
    
    /**
     * Generate User object (bisa disesuaikan)
     */
    public static class TestUser {
        public String name;
        public String email;
        public String phone;
        public String address;
        
        public TestUser() {
            this.name = generateFullName();
            this.email = generateEmail();
            this.phone = generatePhoneNumber();
            this.address = generateAddress();
        }
        
        @Override
        public String toString() {
            return "TestUser{" +
                    "name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", phone='" + phone + '\'' +
                    ", address='" + address + '\'' +
                    '}';
        }
    }
    
    /**
     * Buat TestUser baru
     */
    public static TestUser createTestUser() {
        return new TestUser();
    }
}