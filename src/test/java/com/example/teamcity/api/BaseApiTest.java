package com.example.teamcity.api;

import com.example.teamcity.BaseTest;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import teamcity.api.model.AuthModules;
import teamcity.api.model.ServerAuthSettings;
import teamcity.api.request.ServerAuthRequest;
import teamcity.api.spec.Specification;

import static teamcity.api.generator.TestDataGenerator.generate;

public class BaseApiTest extends BaseTest {
    private final ServerAuthRequest serverAuthRequest = new ServerAuthRequest(Specification.superUserSpec());
    private AuthModules authModules;
    private boolean perProjectPermissions;

    @BeforeSuite(alwaysRun = true)
    public void setUpServerAuthSettings() {
        // Получаем текущие настройки perProjectPermissions
        perProjectPermissions = serverAuthRequest.read().getPerProjectPermissions();

        authModules = generate(AuthModules.class);
        // Обновляем значение perProjectPermissions на true
        serverAuthRequest.update(ServerAuthSettings.builder()
                .perProjectPermissions(true)
                .modules(authModules)
                .build());
    }

    @AfterSuite(alwaysRun = true)
    public void cleanUpServerAuthSettings() {
        // Возвращаем настройке perProjectPermissions исходное значение
        serverAuthRequest.update(ServerAuthSettings.builder()
                .perProjectPermissions(perProjectPermissions)
                .modules(authModules)
                .build());
    }

}
