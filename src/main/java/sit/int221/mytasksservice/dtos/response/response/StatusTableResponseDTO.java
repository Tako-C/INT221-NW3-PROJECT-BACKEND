package sit.int221.mytasksservice.dtos.response.response;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class StatusTableResponseDTO {
    private Integer id;
    private String statusName;
    private String statusDescription;

}
