package teamcity.api.request.cheked;

import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;
import teamcity.api.enums.Endpoint;
import teamcity.api.model.BaseModel;
import teamcity.api.request.CrudInterface;
import teamcity.api.request.Request;
import teamcity.api.request.uncheked.UnchekedBase;


@SuppressWarnings("unchecked")
public final class CheckedBase<T extends BaseModel> extends Request implements CrudInterface {
    private final UnchekedBase unchekedBase;

    public CheckedBase(RequestSpecification spec, Endpoint endpoint) {
        super(spec, endpoint);
        this.unchekedBase = new UnchekedBase(spec, endpoint);
    }

    @Override
    public T create(BaseModel model) { //<T extends BaseModel> добавляем к классу, чтобы ыбла возможность возвращать любой объект который нам нужен
        return (T) unchekedBase
                .create(model)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.getModelClass());
    }

    @Override
    public T read(String id) {
        return (T) unchekedBase
                .read(id)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.getModelClass());
    }

    @Override
    public T update(String id, BaseModel model) {
        return (T) unchekedBase
                .update(id,model)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract().as(endpoint.getModelClass());
    }

    @Override
    public Object delete(String id) {
        return  unchekedBase
                .delete(id)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .extract().asString();
    }
}
