package teamcity.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import teamcity.api.annotation.Random;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)///добавляем игнорирование отстутствующих полей аннотация @JsonIgnoreProperties(ignoreUnknown = true) в классе User, чтобы не падать при сериализации из JSON в User:
public class User extends BaseModel {
    private String id;
    @Random
    private String username;
    @Random
    private String password;
    private Roles roles;
}
