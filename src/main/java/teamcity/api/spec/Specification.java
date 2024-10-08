package teamcity.api.spec;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import teamcity.api.config.Config;
import teamcity.api.model.User;

public class Specification { //делаем его синглтон, тк нам он нужен только один

    private static Specification spec;

    private static RequestSpecBuilder resBuilder() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.addFilter(new RequestLoggingFilter());
        requestSpecBuilder.addFilter(new ResponseLoggingFilter());
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.setAccept(ContentType.JSON);
        return requestSpecBuilder;
    }
    public static RequestSpecification superUserSpec() {
        RequestSpecBuilder requestSpecBuilder = resBuilder();
        requestSpecBuilder.setBaseUri("http://%s:%s@%s/httpAuth".formatted("", Config.getProperty("superUserToken"), Config.getProperty("host")));
        return requestSpecBuilder.build();
    }

    public static RequestSpecification unauthSpec() {
        // пример использования паттерна проектирования - builder
        RequestSpecBuilder requestSpecBuilder = resBuilder();
        return requestSpecBuilder.build();
    }

    public static RequestSpecification authSpec(User user) {
        RequestSpecBuilder requestSpecBuilder = resBuilder();
        requestSpecBuilder.setBaseUri("http://%s:%s@%s".formatted(user.getUsername(), user.getPassword(), Config.getProperty("host")));
        //        requestSpecBuilder.setBaseUri("http://" + user.getUsername() + ":" + user.getPassword() + "@" + Config.getProperty("host"));
        return requestSpecBuilder.build();
    }
}
