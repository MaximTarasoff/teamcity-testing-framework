package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import org.testng.annotations.Test;
import teamcity.api.enums.Endpoint;
import teamcity.api.model.BuildType;
import teamcity.api.model.Project;
import teamcity.api.request.CheckedRequest;
import teamcity.api.spec.Specification;
import teamcity.ui.elements.ErrorElement;
import teamcity.ui.pages.BuildTypePage;
import teamcity.ui.pages.ProjectPage;
import teamcity.ui.pages.ProjectsPage;
import teamcity.ui.pages.admin.CreateBuildTypePage;
import teamcity.ui.pages.admin.CreateProjectPage;

import static io.qameta.allure.Allure.step;

@Test(groups = {"Regression"})
public class CreateProjectTest extends BaseUiTest {
    private static final String REPO_URL = "https://github.com/MaximTarasoff/empty_rep";
    private static final String REPO_URL_TWO = "https://github.com/MaximTarasoff/empty_rep_two";

    @Test(description = "User should be able to create project", groups = {"Positive"})
    public void userCreatesProject() {
        // подготовка окружения
        loginAs(testData.getUser());

        // взаимодействие с UI
        CreateProjectPage.open("_Root")
                .createForm(REPO_URL)
                .setupProject(testData.getProject().getName(), testData.getBuildType().getName());


        // проверка состояния API
        // (корректность отправки данных с UI на API)
        Project createdProject = superUserCheckRequests.<Project>getRequest(Endpoint.PROJECT).read("name:" + testData.getProject().getName());
        softy.assertNotNull(createdProject);

        // проверка состояния UI
        // (корректность считывания данных и отображение данных на UI)
        ProjectPage.open(createdProject.getId())
                .title.shouldHave(Condition.exactText(testData.getProject().getName()));

        var foundProjects = ProjectsPage.open()
                .getProjects().stream()
                .anyMatch(project -> project.getName().text().equals(testData.getProject().getName()));

        softy.assertTrue(foundProjects);
    }

    @Test(description = "User should be able to create build type", groups = {"{Positive}"})
    public void creatingBuildConfigurationShouldBeAvailable(){
        loginAs(testData.getUser());

        CheckedRequest userCheckRequest = new CheckedRequest(Specification.authSpec(testData.getUser()));

        userCheckRequest.<Project>getRequest(Endpoint.PROJECT).create(testData.getProject());

        Project createdProject = superUserCheckRequests.<Project>getRequest(Endpoint.PROJECT).read("name:" + testData.getProject().getName());
        softy.assertNotNull(createdProject);

        CreateBuildTypePage.open(createdProject.getId())
                .createForm(REPO_URL_TWO)
                .setupBuildType(testData.getBuildType().getName());

        BuildType createdBuildType = superUserCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES).read("name:" + testData.getBuildType().getName());
        softy.assertNotNull(createdBuildType);

        BuildTypePage.open(createdBuildType.getId())
                .buildTitle.shouldHave(Condition.exactText(testData.getBuildType().getName()));

    }

    @Test(description = "Creating build configuration with same name should not be available", groups = {"Negative"})
    public void creatingBuildConfigurationSameNameShouldNotBeAvailable(){
        loginAs(testData.getUser());

        CheckedRequest userCheckRequest = new CheckedRequest(Specification.authSpec(testData.getUser()));

        userCheckRequest.<Project>getRequest(Endpoint.PROJECT).create(testData.getProject());

        userCheckRequest.getRequest(Endpoint.BUILD_TYPES).create(testData.getBuildType());

        var createdProject = superUserCheckRequests.<Project>getRequest(Endpoint.PROJECT).read("name:" + testData.getProject().getName());

        CreateBuildTypePage.open(createdProject.getId())
                .createForm(REPO_URL)
                .setupBuildType(testData.getBuildType().getName());

        ErrorElement er = new ErrorElement();
        er.checkErrorText(String.format("Build configuration with name \"%s\" already exists in project: \"%s\"", testData.getBuildType().getName(), testData.getProject().getName()));
    }

    @Test(description = "User should not be able to craete project without name", groups = {"Negative"})
    public void userCreatesProjectWithoutName() {
        // подготовка окружения
        step("Login as user");
        step("Check number of projects");

        // взаимодействие с UI
        step("Open `Create Project Page` (http://localhost:8111/admin/createObjectMenu.html)");
        step("Send all project parameters (repository URL)");
        step("Click `Proceed`");
        step("Set Project Name");
        step("Click `Proceed`");

        // проверка состояния API
        // (корректность отправки данных с UI на API)
        step("Check that number of projects did not change");

        // проверка состояния UI
        // (корректность считывания данных и отображение данных на UI)
        step("Check that error appears `Project name must not be empty`");
    }
}
