package sit.int221.mytasksservice.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import sit.int221.mytasksservice.entities.TaskStatusEnum;

import java.sql.Timestamp;

@Getter
@Setter
public class TaskDetailResponseDTO {
    private Integer id;
    private String title;
    private String description;
    private String assignees;
    private TaskStatusEnum status;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'",timezone = "UTC")
    private Timestamp createdOn;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'",timezone = "UTC")
    private Timestamp updatedOn;
}
