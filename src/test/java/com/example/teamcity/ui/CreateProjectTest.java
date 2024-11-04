package com.example.teamcity.ui;

import com.codeborne.selenide.Condition;
import org.testng.annotations.Test;
import teamcity.api.enums.Endpoint;
import teamcity.api.model.Project;
import teamcity.ui.pages.ProjectPage;
import teamcity.ui.pages.ProjectsPage;
import teamcity.ui.pages.admin.CreateProjectPage;

import static io.qameta.allure.Allure.step;

@Test(groups = {"Regression"})
public class CreateProjectTest extends BaseUiTest {
    private static final String REPO_URL = "https://github.com/MaximTarasoff/empty_rep";

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