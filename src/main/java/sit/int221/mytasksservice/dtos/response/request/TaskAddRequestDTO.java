package sit.int221.mytasksservice.dtos.response.request;


import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import sit.int221.mytasksservice.entities.Status;
import sit.int221.mytasksservice.entities.TaskStatusEnum;


@Getter
@Setter
public class TaskAddRequestDTO {
    private Integer id;
    @Size(min=1, max=100)
    private String title;
    @Size(min=1, max=500)
    private String description;
    @Size(min=1, max=30)
    private String assignees;
//    private String status;
    private String status;


}

