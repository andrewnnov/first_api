package test.api;

import data.People;
import data.PeopleCreated;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.*;

public class ApiTests {

    @Test
    public void firstTest() {
        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .body("page", notNullValue())
                .body("per_page", notNullValue())
                .body("total", notNullValue())
                .body("total_pages", notNullValue())
                .body("data.id", not(hasItem(nullValue())))
                .body("data.first_name", hasItem("Lindsay"));
    }

    @Test
    public void secondTest() {
        Map<String, String> requestData = new HashMap<>();
        requestData.put("name", "Valdemar");
        requestData.put("job", "Shut");
        Response response = given()
                .contentType("application/json")
                .body(requestData)
                .log().all()
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .response();

        JsonPath jsonResponse = response.jsonPath();
        Assert.assertEquals(jsonResponse.get("name"), requestData.get("name")
                , "Ожидали создание пользователя с именем "
                        + requestData.get("name")
                        + ", создался с именем" + jsonResponse.get("name"));
    }

    @Test
    public void dtoTest() {
        People people = new People("Katy", "Programmer");
        PeopleCreated peopleCreated = given()
                .contentType("application/json")
                .body(people)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().body()
                .extract().body().as(PeopleCreated.class);

        System.out.println("___________________");
        System.out.println(peopleCreated.getCreatedAt());

    }

}
