package test.api;

import data.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

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

    @Test
    public void fullTestColorSorted() {

        ResourcesColor resourcesColor = given()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .log().body()
                .extract().body().as(ResourcesColor.class);

        resourcesColor.getData().forEach(x-> System.out.println(x.getYear()));

        boolean isOrdered = true;
        for (int i = 0; i < resourcesColor.getData().size() - 1; i++) {
            if (resourcesColor.getData().get(i).getYear() >= resourcesColor.getData().get(i + 1).getYear()) {
                isOrdered = false;
            }
        }
        Assert.assertTrue(isOrdered, "Данные отсортированы по возрастанию");
        System.out.println("Add new information for test");
    }

    @Test
    public void testExampleForGit() {
        System.out.println("Test Example + Amend");
        System.out.println("Fix commit2334");
    }
}
