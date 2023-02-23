package test.api;

import data.ErrorMessage;
import data.Register;
import data.Resource;
import data.TokenResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class HomeWorkApiTests {

    @Test
    public void fullTest() {

        Resource resource = given()
                .contentType("application/json")
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().body()
                .extract().body().as(Resource.class);

        resource.getData().forEach(x -> System.out.println(x.getAvatar()));
    }

    @Test
    public void authorSuccTest() {
        Map<String, String> pass = new HashMap<>();
        pass.put("email", "eve.holt@reqres.in");
        pass.put("password", "pistol");

        Register register = new Register("eve.holt@reqres.in", "pistol");

        TokenResponse tokenResponse = given()
                .contentType("application/json")
                .auth()
                .basic("eve.holt@reqres.in", "pistol")
                .body(register)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(200)
                .extract().as(TokenResponse.class);

        System.out.println(tokenResponse.getToken());
    }

    @Test
    public void authorUnSuccTest() {
        Register register = new Register("eve.holt@reqres.in", "");
        ErrorMessage errorMessage = given()
                .contentType("application/json")
                .auth()
                .basic("eve.holt@reqres.in", "pistol")
                .body(register)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .statusCode(400)
                .extract().as(ErrorMessage.class);

        System.out.println(errorMessage.getError());
        Assert.assertEquals(errorMessage.getError(), "Missing password");
    }
}
