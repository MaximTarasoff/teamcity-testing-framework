package com.example.teamcity;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;
import teamcity.api.generator.TestDataStorage;
import teamcity.api.model.TestData;
import teamcity.api.request.CheckedRequest;
import teamcity.api.spec.Specification;

import static teamcity.api.generator.TestDataGenerator.generate;

public class BaseTest {
    protected SoftAssert softy;
    protected CheckedRequest superUserCheckRequests = new CheckedRequest(Specification.superUserSpec());
    protected TestData testData;

    @BeforeMethod(alwaysRun = true)
    public void beforeTest() {
        softy = new SoftAssert();
        testData = generate();
    }

    @AfterMethod(alwaysRun = true)
    public void afterTest() {
        softy.assertAll();
        TestDataStorage.getStorage().deleteCreatedEntities();
    }
}
