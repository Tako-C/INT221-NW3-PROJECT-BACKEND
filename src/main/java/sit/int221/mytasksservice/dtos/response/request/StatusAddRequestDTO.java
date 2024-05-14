package sit.int221.mytasksservice.dtos.response.request;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusAddRequestDTO {

    private Integer id;
    @Size(min=1, max=50)
    private String name;
    @Size(min=1, max=200)
    private String description;
}
