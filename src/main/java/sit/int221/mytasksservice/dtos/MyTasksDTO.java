package sit.int221.mytasksservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyTasksDTO {
    private Integer id;
    private String title;
    private String assignees;
    private String status;
}
