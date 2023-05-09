package org.api;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class RegressNoPojoTest {
    private final static String URL = "https://regres.in/";
    @Test
    public void testGetAvatarAndId() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        //используем интерфейс Response для сохранения ответа
        Response response = given()
                .when()
                    .get("api/users&page=2")
                .then().log().all()
                    .body("page", equalTo(2))
                    .body("data.id", notNullValue())
                    .extract().response();
        //переводим ответ в формат JSON
        JsonPath jsonPath = response.jsonPath();

        List<String> emails = jsonPath.get("data.email");
        List<String> ids = jsonPath.get("data.id");
        List<String> avatars = jsonPath.get("data.avatar");

        for(int i = 0; i < avatars.size(); i++){
            Assert.assertTrue(avatars.get(i).contains(ids.get(i)));
        }
    }

    @Test
    public void testPostSuccesReg() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());

        Map<String, String> user = new HashMap<>();
        user.put("email", "ksdjfkl@mail.gh");
        user.put("password", "yweurhjksfdj_23");

        //1 вариант
        given()
                    .body(user)
                .when()
                    .post("api/register")
                .then().log().all()
                    .body("id", equalTo(4))
                    .body("token", equalTo("openskdjfjsahuw23478yhu"));

        //2 вариант
        Response response =  given()
                    .body(user)
                .when()
                    .post("api/register")
                .then().log().all()
                    .body("id", equalTo(4))
                    .body("token", equalTo("openskdjfjsahuw23478yhu"))
                    .extract().response();
        JsonPath jsonPath = response.jsonPath();
        int id = jsonPath.get("id");
        String token = jsonPath.get("token");

        Assert.assertEquals(4, id);
        Assert.assertEquals("openskdjfjsahuw23478yhu", token);
    }
}
