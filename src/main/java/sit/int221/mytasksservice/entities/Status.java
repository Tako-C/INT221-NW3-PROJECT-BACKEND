package sit.int221.mytasksservice.entities;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="statuss")
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String statusName;
    private String statusDescription;
}