package teamcity.api.request.uncheked;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import teamcity.api.enums.Endpoint;
import teamcity.api.model.BaseModel;
import teamcity.api.request.CrudInterface;
import teamcity.api.request.Request;

public class UnchekedBase extends Request implements CrudInterface {

    public UnchekedBase(RequestSpecification spec, Endpoint endpoint) {
        super(spec, endpoint);
    }


    @Override
    public Response create(BaseModel model) {
        return RestAssured.
                given()
                .spec(spec)
                .body(model)
                .post(endpoint.getUrl());
    }

    @Override
    public Response read(String id) {
        return RestAssured
                .given()
                .spec(spec)
                .get(endpoint.getUrl() + "/id:" + id);
    }

    @Override
    public Response update(String id, BaseModel model) {
        return RestAssured
                .given()
                .body(model)
                .spec(spec)
                .put(endpoint.getUrl() + "/id:" + id);// возможно нужно + id,model
    }

    @Override
    public Response delete(String id) {
        return RestAssured
                .given()
                .spec(spec)
                .delete(endpoint.getUrl() + "/id:" + id);
    }
}
