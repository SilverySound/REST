import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

public class ReqResApiTests {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    @DisplayName("Получение информации о пользователе")
    public void GetUserTest() {
        Response response = RestAssured.get("/users/1");
        response.then()
                .statusCode(200)
                .body("data.id", equalTo(1))
                .body("data.email", equalTo("george.bluth@reqres.in"));
    }

    @Test
    @DisplayName("Создание нового пользователя")
    public void CreateUserTest() {
        String requestBody = "{\"name\": \"John\", \"job\": \"Developer\"}";
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/users");
        response.then()
                .statusCode(201)
                .body("name", equalTo("John"));
    }

    @Test
    @DisplayName("Обновление данных пользователя")
    public void UpdateUserTest() {
        String requestBody = "{\"name\": \"Updated Name\", \"job\": \"QA Engineer\"}";
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .put("/users/1");
        response.then()
                .statusCode(200)
                .body("job", equalTo("QA Engineer"));
    }

    @Test
    @DisplayName("Удаление пользователя")
    public void DeleteUserTest() {
        Response response = RestAssured.delete("/users/1");
        response.then().statusCode(204);
    }

    @Test
    @DisplayName("Получение списка пользователей")
    public void GetUsersListTest() {
        Response response = RestAssured.get("/users?page=1");
        response.then()
                .statusCode(200)
                .body("data.size()", greaterThan(0));
    }
}

