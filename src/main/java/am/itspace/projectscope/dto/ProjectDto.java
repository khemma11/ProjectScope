package am.itspace.projectscope.dto;

import am.itspace.projectscope.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDto {

    private String name;
    private UserType userType=UserType.TEAM_LEADER;
    private double hours;


}
