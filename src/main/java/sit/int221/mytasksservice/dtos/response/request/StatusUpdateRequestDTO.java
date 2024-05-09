package sit.int221.mytasksservice.dtos.response.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import sit.int221.mytasksservice.entities.TaskStatusEnum;


@Getter
@Setter
@DynamicUpdate
public class StatusUpdateRequestDTO {
    private Integer id;
    private String statusName;
    private String statusDescription;
}
