package teamcity.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import teamcity.api.annotation.Random;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project extends BaseModel {
    @Random
    private String id;
    @Random
    private String name;
    private String locator;
}
