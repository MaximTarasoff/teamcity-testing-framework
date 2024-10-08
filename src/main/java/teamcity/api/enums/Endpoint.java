package teamcity.api.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import teamcity.api.model.BaseModel;
import teamcity.api.model.BuildType;
import teamcity.api.model.Project;
import teamcity.api.model.User;

@AllArgsConstructor
@Getter
public enum Endpoint {
    BUILD_TYPES("/app/rest/buildTypes", BuildType.class),
    PROJECT("/app/rest/projects", Project.class),
    USERS("/app/rest/users", User.class);

    private final String url;
    private final Class<? extends BaseModel> modelClass; // Class<? extends BaseModel>  - может быть любой наследник base model
}
