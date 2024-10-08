package teamcity.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamcity.api.annotation.Parameterizable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Role extends BaseModel {
    @Builder.Default
    @Parameterizable
    private String roleId = "SYSTEM_ADMIN";
    @Builder.Default
    @Parameterizable
    private String scope = "g";
}
