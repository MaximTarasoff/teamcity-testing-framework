package com.example.teamcity;

import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import teamcity.api.model.User;
import teamcity.api.spec.Specification;

public class BuildConfigurationTest extends BaseApiTest {

    @Test
    public void buildConfigurationTest() {
//        GET http://admin:admin@172.17.0.1:8111/app/rest/projects/
//        Accept: application/json
        User user = User.builder()
                .username("admin")
                .password("admin")
                .build();

        String token = RestAssured
                .given()
                .spec(Specification.getSpec().authSpec(user))
                .get("/authenticationTest.html?csrf")
                .then().assertThat().statusCode(HttpStatus.SC_OK)
                .extract().asString();
        System.out.println(token);
    }
}
