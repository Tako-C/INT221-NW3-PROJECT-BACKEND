package sit.int221.mytasksservice.dtos.response.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;


@Getter
@Setter
@DynamicUpdate
public class StatusUpdateRequestDTO {
    private Integer id;
    private String name;
    private String description;
}
