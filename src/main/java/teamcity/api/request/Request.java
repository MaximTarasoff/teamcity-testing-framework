package teamcity.api.request;

import io.restassured.specification.RequestSpecification;
import teamcity.api.enums.Endpoint;

//хранит эндпойнт, саму сущность и спецификацию
public class Request {
    /**
     * Request - это класс, описывающий меняющиеся параметры запроса, такие как:
     *  спецификация, эндпоинт (relative URL, model)
     */
    protected final RequestSpecification spec; //protected - есть доступ у всех детей
    protected final Endpoint endpoint;

    public Request(RequestSpecification spec, Endpoint endpoint) {
        this.spec = spec;
        this.endpoint = endpoint;
    }
}
