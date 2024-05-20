package sit.int221.mytasksservice.dtos.response.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import sit.int221.mytasksservice.entities.Status;
import sit.int221.mytasksservice.entities.TaskStatusEnum;

import java.sql.Timestamp;

@Getter
@Setter
public class TaskTableResponseDTO {
    private Integer id;
    private String title;
    private String assignees;
    private String statusName;
}
