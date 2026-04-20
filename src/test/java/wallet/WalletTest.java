package wallet;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;

import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WalletTest {

    private static final String BASE_URL = "http://localhost:8080/api/v1";
    private String walletId;

    @BeforeEach
    public void setUp() throws JSONException {
        RestAssured.baseURI = BASE_URL;

        Response response = RestAssured.given()
                .post("/create");

        LinkedHashMap<String,LinkedHashMap<String,String>> res = response.jsonPath().get();
        walletId = res.get("data").get("id");

        System.out.println("Created wallet with ID: " + walletId);
    }

    @Test
    public void testGetWalletById() {

        Response response = RestAssured.given()
                .get("/wallets" + "/" + walletId);

        assertEquals(200, response.getStatusCode(), "Failed to get wallet by ID");
        LinkedHashMap<String,LinkedHashMap<String,String>> res = response.jsonPath().get();
        String resWalletId = res.get("data").get("id");
        assertEquals(walletId, resWalletId, "Wallet name mismatch");
    }

    @Test
    public void testOperationWallet() {

        String operationWalletJson = """
                {
                    "walletId": "%s",
                    "operationType":"DEPOSIT",
                    "amount": 1000
                }
                """.formatted(walletId);

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(operationWalletJson)
                .post("/wallet");

        assertEquals(200, response.getStatusCode(), "Failed to update wallet");
        LinkedHashMap<String,LinkedHashMap<String,Integer>> res = response.jsonPath().get();
        Integer resWalletBalance = res.get("data").get("balance");
        assertEquals(1000, resWalletBalance, "Updated wallet balance mismatch");
    }
}