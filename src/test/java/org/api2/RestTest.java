package org.api2;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RestTest {
    @Test
    void restTest() {
//        String authReq = "{\n" +
//                " \"username\": \"admin\",\n" +
//                " \"password\": \"admin\",\n" +
//                "}";

        AuthReq authReq = new AuthReq("admin", "admin");
        //получаем токен через строку
        String token1 = given()
                    .log().all()
                .when()
                    .contentType(ContentType.JSON)
                    .body(authReq)
                    .post("http://localhost:8081/api/v1/authenticate")
                .then()
                    .log().all()
                    .statusCode(200)
                    .extract().response().jsonPath().getString("token");
        //с учетом класса ответов
        AuthRes response = given()
                .log().all()
                .when()
                .contentType(ContentType.JSON)
                .body(authReq)
                .post("http://localhost:8081/api/v1/authenticate")
                .then()
                .log().all()
                .statusCode(200)
                .extract().response().as(AuthRes.class);

        String orderReq = "{\n" +
                " \"text\": \"pizza\",\n" +
                " \"comment\": \"4 season\",\n" +
                "}";
        given()
                    .log().all()
                .when()
                    .contentType(ContentType.JSON)
                    .header("Authorization", "Bearer" + response.getToken())
                    .body(orderReq)
                .post("http://localhost:8081/api/v1/order")
                .then()
                    .log().all()
                    .statusCode(200);
    }
}
