package sit.int221.mytasksservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.mytasksservice.entities.MyTasks;
import sit.int221.mytasksservice.repositories.MyTasksRepository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MyTasksService {
    @Autowired
    private MyTasksRepository repository;

    public List<MyTasks> getAllTasks() {
        List<MyTasks> tasks = repository.findAll();
        for (MyTasks task : tasks) {
            if (task.getAssignees() != null) {
                task.setAssignees(task.getAssignees().trim());
            }
        }
        return tasks;
    }
    public MyTasks getTask(Integer id) {
        MyTasks task = repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task id " + id + " does not exist!")
        );

        if (task.getAssignees() != null) {
            task.setAssignees(task.getAssignees().trim());
        }
        task.setCreate_Time(convertToUTC(task.getCreate_Time()));
        task.setUpdate_Time(convertToUTC(task.getUpdate_Time()));
        return task;
    }
    private LocalDateTime convertToUTC(LocalDateTime localDateTime){
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        ZonedDateTime utcDateTime = zonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        return utcDateTime.toLocalDateTime();

    }
}
