package teamcity.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamcity.api.annotation.Optional;
import teamcity.api.annotation.Parameterizable;
import teamcity.api.annotation.Random;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BuildType extends BaseModel {
    @Random
    @Parameterizable
    private String id;
    @Random
    private String name;
    //@Parameterizable
    private Project project;
    //@Optional
    private Steps steps;
}
