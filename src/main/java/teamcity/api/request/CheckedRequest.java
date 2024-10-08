package teamcity.api.request;

import io.restassured.specification.RequestSpecification;
import teamcity.api.enums.Endpoint;
import teamcity.api.model.BaseModel;
import teamcity.api.request.cheked.CheckedBase;

import java.util.EnumMap;

public class CheckedRequest {
    private final EnumMap<Endpoint, CheckedBase<?>> requests = new EnumMap<>(Endpoint.class);

    public CheckedRequest(RequestSpecification spec) {
        for (var endpoint: Endpoint.values()) {
            requests.put(endpoint, new CheckedBase<>(spec, endpoint));
        }
    }

    public <T extends BaseModel> CheckedBase<T> getRequest(Endpoint endpoint) {
        return (CheckedBase<T>) requests.get(endpoint);
    }
}
