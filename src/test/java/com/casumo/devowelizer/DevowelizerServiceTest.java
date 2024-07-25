package com.casumo.devowelizer;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class DevowelizerServiceTest {

    // Method that runs before any test methods in the class
    @BeforeClass
    public void setUp() {
        // Set the base URI for RestAssured
        RestAssured.baseURI = "http://localhost:8080";
    }

    // This method provides test data for the test method.
    // It returns an array of objects where each element is a test case.
    // Each test case is an array of two strings: the input and the expected output after devowelization.
    @DataProvider(name = "testCases")
    public Object[][] createTestCases() {
        return new Object[][]{
            {"hello", "hll"},                // Test case for normal input
            {"world", "wrld"},               // Test case for normal input
            {"AEIOU", ""},                   // Test case for uppercase vowels
            {"Casumo", "Csm"},               // Test case for mixed case
            {"Devowelizer", "Dvwlzr"},       // Test case for a longer word
            {"", ""},                        // Test case for empty input
            {"123", "123"},                  // Test case for numeric input
            {"bcdf", "bcdf"},                // Test case with no vowels
            {"!@#", "!@#"},                  // Test case with special characters
            {"hëllo", "hëll"},               // Test case with special character ë
            {"wørld", "wørld"},              // Test case with special character ø
            {"smïle", "smïl"},               // Test case with special character ï
            {"h3ll0_w0rld!","h3ll0_w0rld!"}, // Test case with special characters and digits
            {"helloWORLD", "hllWRLD"},       // Test case for case sensitivity
            {"a".repeat(1000) + "e".repeat(1000) + "i".repeat(1000) + "o".repeat(1000) + "u".repeat(1000), ""} // Test case for very long input
        };
    }

    // This test method tests the devowelizer service using the provided test cases.
    // It makes a GET request to the service with the input and checks the response.
    
    @Test(dataProvider = "testCases")
    public void testDevowelize(String input, String expected) {
        // Log the input being tested
        System.out.println("Testing input: " + input);

        // Make a GET request to the endpoint with the input and check the response
        given()
            .when()
            .get("/" + input) // Send GET request to the endpoint with the input string
            .then()
            .assertThat()
            .statusCode(200) // Validate that the response status code is 200 (OK)
            .body(equalTo(expected)); // Verify that the response body matches the expected output

        // Log that the test passed for this input
        System.out.println("Test passed for input: " + input);
    }
}
