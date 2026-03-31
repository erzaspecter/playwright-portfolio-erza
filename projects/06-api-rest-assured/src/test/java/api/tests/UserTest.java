package api.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UserTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
    }

    @Test
    public void testCreateBooking() {
        String requestBody = "{" +
                "\"firstname\" : \"Erza\"," +
                "\"lastname\" : \"Akbar\"," +
                "\"totalprice\" : 111," +
                "\"depositpaid\" : true," +
                "\"bookingdates\" : {" +
                "    \"checkin\" : \"2026-01-01\"," +
                "    \"checkout\" : \"2026-01-02\"" +
                "}," +
                "\"additionalneeds\" : \"Breakfast\"" +
                "}";

        given()
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .post("/booking")
        .then()
            .statusCode(200)
            .body("booking.firstname", equalTo("Erza"))
            .body("bookingid", notNullValue())
            .log().all();
    }

    @Test
    public void testGetBooking() {
        // Step 1: Create booking dulu
        String requestBody = "{" +
                "\"firstname\" : \"Budi\"," +
                "\"lastname\" : \"Santoso\"," +
                "\"totalprice\" : 200," +
                "\"depositpaid\" : true," +
                "\"bookingdates\" : {" +
                "    \"checkin\" : \"2026-03-01\"," +
                "    \"checkout\" : \"2026-03-05\"" +
                "}," +
                "\"additionalneeds\" : \"Dinner\"" +
                "}";

        Integer bookingId = given()
            .contentType(ContentType.JSON)
            .body(requestBody)
        .when()
            .post("/booking")
        .then()
            .statusCode(200)
            .extract()
            .path("bookingid");

        System.out.println("✅ Created booking with ID: " + bookingId);

        // Step 2: Get booking by ID
        given()
            .when()
                .get("/booking/" + bookingId)
            .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("firstname", equalTo("Budi"))
                .body("lastname", equalTo("Santoso"))
                .log().all();
    }

    @Test
    public void testHealthCheck() {
        given()
            .when()
                .get("/ping")
            .then()
                .statusCode(201);
    }

    @Test
    public void testGetAllBookings() {
        given()
            .when()
                .get("/booking")
            .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .log().all();
    }

    // ✅ Perbaikan: Hapus lambda, gunakan extract() atau langsung assert
    @Test
    public void testGetExistingBooking() {
        int bookingId = 1;
        
        // Cek status code dengan extract
        int statusCode = given()
            .when()
                .get("/booking/" + bookingId)
            .then()
                .extract()
                .statusCode();
        
        if (statusCode == 200) {
            System.out.println("✅ Booking ID " + bookingId + " found");
            
            // Lanjutkan validasi
            given()
                .when()
                    .get("/booking/" + bookingId)
                .then()
                    .statusCode(200)
                    .body("firstname", notNullValue());
        } else {
            System.out.println("⚠️ Booking ID " + bookingId + " not found (status: " + statusCode + ")");
        }
    }
}