package sit.int221.mytasksservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.mytasksservice.entities.MyTasks;
import sit.int221.mytasksservice.repositories.MyTasksRepository;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.Optional;
import java.util.Collections;

@Service
public class MyTasksService {
    @Autowired
    private MyTasksRepository repository;

    public List<MyTasks> getAllTasks() {
        List<MyTasks> tasks = repository.findAll();
        for (MyTasks task : tasks) {
            task.setAssignees(task.getAssignees()!=null?task.getAssignees().trim():null);
            task.setTitle(task.getTitle()!=null?task.getTitle().trim():null);
        }
        if (tasks.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.OK, "No tasks found");
            return Collections.emptyList();
        }
        return tasks;
    }
    public MyTasks getTask(Integer id) {
        Optional<MyTasks> optionalTask = repository.findById(id);
        if (optionalTask.isPresent()) {
            MyTasks task = optionalTask.get();
            task.setAssignees(task.getAssignees()!=null?task.getAssignees().trim():null);
            task.setTitle(task.getTitle()!=null?task.getTitle().trim():null);
            task.setDescription(task.getDescription()!=null?task.getDescription().trim():null);
            return task;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task " + id +" does not exist !!!");
        }
  }

}
