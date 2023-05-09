package org.api;

import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class RestAssuredWithPojoTest {
    private final static String URL = "https://regres.in/";
    @Test
    public void testGetAvatarAndId() {
        //извлекаем в переменную users данные json в блоке data
        List<UserData> users = given()
                .when()
                    .contentType(ContentType.JSON)
                    .get(URL + "/api/users?page=2")
                .then().log().all()
                    .extract().body().jsonPath().getList("data", UserData.class);

        //сравниваем, что avatar содержит Id (id перевели в строку)
        users.stream().forEach(x -> Assert.assertTrue(x.getAvatar().contains((x.getId().toString()))));

        //stream- достать из листа опредленные данныеallMatch -все совпадения
        Assert.assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("@regres.in")));

        List<String> avatars = users.stream().map(UserData::getAvatar).collect(Collectors.toList());

        List<String> ids = users.stream().map(x -> x.getId().toString()).collect(Collectors.toList());

        for(int i = 0; i < avatars.size(); i++){
            Assert.assertTrue(avatars.get(i).contains(ids.get(i)));
        }
    }
    @Test
    public void testGetSpecification() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        List<UserData> users = given()
                .when()
                    .get("/api/users?page=2")
                //логировать не обязательно, но удобно
                .then().log().all()
                    .extract().body().jsonPath().getList("data", UserData.class);

        //сравниваем, что avatar содержит Id (id перевели в строку)
        users.stream().forEach(x -> Assert.assertTrue(x.getAvatar().contains((x.getId().toString()))));

        //allMatch -все совпадения
        Assert.assertTrue(users.stream().allMatch(x -> x.getEmail().endsWith("@regres.in")));

        List<String> avatars = users.stream().map(UserData::getAvatar).collect(Collectors.toList());

        List<String> ids = users.stream().map(x -> x.getId().toString()).collect(Collectors.toList());

        for(int i = 0; i < avatars.size(); i++){
            Assert.assertTrue(avatars.get(i).contains(ids.get(i)));
        }
    }
    @Test
    public void testPostSuccesReg() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());
        Integer id = 4;
        String token = "openskdjfjsahuw23478yhu";

        Register user = new Register("ksdjfkl@mail.gh", "yweurhjksfdj_23");

        SuccessReg successReg = given()
                    .body(user)
                .when()
                    .post("api/register")
                .then().log().all()
                    .extract().as(SuccessReg.class);

        Assert.assertNull(successReg.getId());
        Assert.assertNull(successReg.getToken());

        Assert.assertEquals(id, successReg.getId());
        Assert.assertEquals(token, successReg.getToken());
    }
    @Test
    public void testPostUnsuccesReg() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecError400());
        Integer id = 4;
        String token = "openskdjfjsahuw23478yhu";

        Register user = new Register("ksdjfkl@mail", "");

        UnsuccessReg unsuccessReg = given()
                    .body(user)
                .when()
                    .post("api/register")
                .then().log().all()
                    .extract().as(UnsuccessReg.class);

        Assert.assertEquals("Missing password", unsuccessReg.getError());
    }
    @Test
    public void testDeleteUser() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecCustom(204));

        given()
                .when()
                    .delete("/api/users/2")
                .then().log().all();
        //в Specifications есть expected
    }
}
