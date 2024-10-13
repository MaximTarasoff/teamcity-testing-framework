package com.example.teamcity;

import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import teamcity.api.enums.Endpoint;
import teamcity.api.model.*;
import teamcity.api.request.CheckedRequest;
import teamcity.api.request.uncheked.UnchekedBase;
import teamcity.api.spec.Specification;

import java.util.Arrays;

import static teamcity.api.generator.TestDataGenerator.generate;

@Test(groups = {"Regression"})
public class BuildTypeTest extends BaseApiTest {

    @Test(description = "User should be able to create build type", groups = {"Regression"})
    public void userCreatesBuildTypeTest() {

        superUserCheckRequests.getRequest(Endpoint.USERS).create(testData.getUser());
        CheckedRequest userCheckRequest = new CheckedRequest(Specification.authSpec(testData.getUser()));

        userCheckRequest.<Project>getRequest(Endpoint.PROJECT).create(testData.getProject());

        userCheckRequest.getRequest(Endpoint.BUILD_TYPES).create(testData.getBuildType());

        BuildType createdBuildType = userCheckRequest
                .<BuildType>getRequest(Endpoint.BUILD_TYPES).read(testData.getBuildType().getId());

        softy.assertEquals(testData.getBuildType().getName(), createdBuildType.getName(), "Build type name is not correct");
    }

    @Test(description = "User should not be able to create two build types with the same id", groups = {"Negative", "CRUD"})
    public void userCreatesTwoBuildTypesWithTheSameIdTest() {
        BuildType buildTypeWithSameId = generate(Arrays.asList(testData.getProject()), BuildType.class, testData.getBuildType().getId());

        superUserCheckRequests.getRequest(Endpoint.USERS).create(testData.getUser());
        CheckedRequest userCheckRequest = new CheckedRequest(Specification.authSpec(testData.getUser()));

        userCheckRequest.<Project>getRequest(Endpoint.PROJECT).create(testData.getProject());

        userCheckRequest.getRequest(Endpoint.BUILD_TYPES).create(testData.getBuildType());
        new UnchekedBase(Specification.authSpec(testData.getUser()), Endpoint.BUILD_TYPES)
                .create(buildTypeWithSameId)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.containsString("The build configuration / template ID \"%s\" is already used by another configuration or template".formatted(testData.getBuildType().getId())));
    }

    @Test(description = "Project admin should be able to create build type for their project", groups = {"Positive", "Roles"})
    public void projectAdminCreatesBuildTypeTest() {


        superUserCheckRequests.getRequest(Endpoint.PROJECT).create(testData.getProject());

        testData.getUser().setRoles(generate(Roles.class, "PROJECT_ADMIN", "p:" + testData.getProject().getId()));
        superUserCheckRequests.<User>getRequest(Endpoint.USERS).create(testData.getUser());

        CheckedRequest userCheckRequest = new CheckedRequest(Specification.authSpec(testData.getUser()));
        userCheckRequest.getRequest(Endpoint.BUILD_TYPES).create(testData.getBuildType());
        BuildType createdBuildType = userCheckRequest
                .<BuildType>getRequest(Endpoint.BUILD_TYPES).read(testData.getBuildType().getId());

        softy.assertEquals(testData.getBuildType().getName(), createdBuildType.getName(), "Build type name is not correct");
    }

    //just to pull
    @Test(description = "Project admin should not be able to create build type for not their project", groups = {"Negative", "Roles"})
    public void projectAdminCreatesBuildTypeForAnotherUserProjectTest() {

           TestData testDataOne = generate();
        superUserCheckRequests.getRequest(Endpoint.PROJECT).create(testDataOne.getProject());
        testDataOne.getUser().setRoles(generate(Roles.class, "PROJECT_ADMIN", "p:" + testDataOne.getProject().getId()));
        superUserCheckRequests.<User>getRequest(Endpoint.USERS).create(testDataOne.getUser());

        TestData testDataTwo = generate();
        superUserCheckRequests.getRequest(Endpoint.PROJECT).create(testDataTwo.getProject());
        testDataTwo.getUser().setRoles(generate(Roles.class, "PROJECT_ADMIN", "p:" + testDataTwo.getProject().getId()));
        superUserCheckRequests.<User>getRequest(Endpoint.USERS).create(testDataTwo.getUser());

        new UnchekedBase(Specification.authSpec(testDataOne.getUser()), Endpoint.BUILD_TYPES)
                .create(testDataTwo.getBuildType())
                .then().assertThat()
                .statusCode(HttpStatus.SC_FORBIDDEN)
                .body(Matchers.containsString("You do not have enough permissions to edit project with id: %s".formatted(testDataTwo.getProject().getId())));
    }
}
