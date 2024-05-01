package sit.int221.mytasksservice.dtos.response.request;

import sit.int221.mytasksservice.entities.TaskStatusEnum;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
public class TaskDeleteRequestDTO {
    private Integer id;
    private String title;
    private String description;
    private String assignees;
    private TaskStatusEnum status;


}
