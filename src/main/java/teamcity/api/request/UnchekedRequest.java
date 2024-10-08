package teamcity.api.request;

import io.restassured.specification.RequestSpecification;
import teamcity.api.enums.Endpoint;
import teamcity.api.request.uncheked.UnchekedBase;

import java.util.EnumMap;

public class UnchekedRequest {
    private final EnumMap<Endpoint, UnchekedBase> requests = new EnumMap<>(Endpoint.class);

    public UnchekedRequest(RequestSpecification spec) {
        for (var endpoint: Endpoint.values()) {
            requests.put(endpoint, new UnchekedBase(spec, endpoint));
        }
    }

    public UnchekedBase getRequest(Endpoint endpoint) {
        return requests.get(endpoint);
    }
}
