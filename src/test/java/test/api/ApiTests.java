package test.api;

import data.People;
import data.PeopleCreated;
import data.Resource;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

public class ApiTests {

    @Test
    public void firstTestGet() {
        given()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().all()
                .body("page", notNullValue())
                .body("per_page", notNullValue())
                .body("total", notNullValue())
                .body("total_pages", notNullValue())
                .body("data.id", not(hasItem(nullValue()))) //хотя бы один не ноль
                .body("data.first_name", hasItem("Byron"));

    }

    @Test
    public void secondTestPost() {

        Map<String, String> requestData = new HashMap<>();
        requestData.put("name", "Kirill");
        requestData.put("job", "Teacher");
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
        Assert.assertEquals(jsonResponse.get("name"), requestData.get("name"),
                "Ожидали создание пользователя с именем " + requestData.get("name") +
                " Создался с именем " + jsonResponse.get("name"));
    }

    @Test
    public void thirdDtoTestPost() {
        People people = new People("Katy", "doctor");
        PeopleCreated peopleCreated = given()
                .contentType("application/json")
                .log().all()
                .body(people)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().body()
                .extract().body().as(PeopleCreated.class);

        System.out.println("_____________________________");
        System.out.println(peopleCreated.getCreatedAt());

    }

    @Test
    public void fullTest() {
        Resource resource = given()
                .log().all()
                .when().get("https://reqres.in/api/users?page=2")
                .then()
                .log().body()
                .extract().body().as(Resource.class);
        resource.getData().forEach(x-> System.out.println(x.getEmail()));


    }






}
