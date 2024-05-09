package sit.int221.mytasksservice.dtos.response.request;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusAddRequestDTO {

    private Integer id;
    private String statusName;
    private String statusDescription;
}
