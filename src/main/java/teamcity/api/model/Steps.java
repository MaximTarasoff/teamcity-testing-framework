package teamcity.api.model;

import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Steps extends BaseModel{
    private Integer count;
    private List<Step> step;
}