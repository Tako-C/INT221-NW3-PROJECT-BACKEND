package sit.int221.mytasksservice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "mytasks")
public class MyTasks {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        @Size(min=1, max=100)
        private String title;
        @Size(min=1, max=500)
        private String description;
        @Size(min=1, max=30)
        private String assignees;
//        @Enumerated(EnumType.STRING)
//        private TaskStatusEnum status;

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
        @Column(insertable = false,updatable = false)
        private Timestamp createdOn;

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
        @Column(insertable = false,updatable = false)
        private Timestamp updatedOn;

        @ManyToOne
        @JoinColumn(name = "statuss_id")
        private  Status status;

}
