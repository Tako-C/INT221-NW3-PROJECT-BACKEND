package sit.int221.mytasksservice.entities;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "mytasks")
public class MyTasks {
    @Id
    private Integer id;
    private String title;
    private String description;
    private String assignees;
    private String status;
    private String create_time;
    private String update_time;
}
