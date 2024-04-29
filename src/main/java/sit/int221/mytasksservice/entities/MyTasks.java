package sit.int221.mytasksservice.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;
@Entity
@Data
@Table(name = "mytasks")
public class MyTasks {

        @Id
        private Integer id;
        private String title;
        private String description;
        private String assignees;
        @Enumerated(EnumType.STRING)
        private TaskStatusEnum status;

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
        private Timestamp createdOn;

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
        private Timestamp updatedOn;

}
