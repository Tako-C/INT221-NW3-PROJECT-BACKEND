package sit.int221.mytasksservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.mytasksservice.dtos.response.request.TaskAddRequestDTO;
import sit.int221.mytasksservice.entities.MyTasks;
import sit.int221.mytasksservice.entities.TaskStatusEnum;
import sit.int221.mytasksservice.repositories.MyTasksRepository;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.Optional;
import java.util.Collections;

@Service
public class MyTasksService {
    @Autowired
    private MyTasksRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    public List<MyTasks> getAllTasks() {
        List<MyTasks> tasks = repository.findAll();
        for (MyTasks task : tasks) {
            trimTaskFields(task);
        }
        if (tasks.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.OK, "No tasks found");
//            return Collections.emptyList();
        }

        return tasks;
    }
    public MyTasks getTask(Integer id) {
        Optional<MyTasks> optionalTask = repository.findById(id);
        if (optionalTask.isPresent()) {
            MyTasks task = optionalTask.get();
            trimTaskFields(task);
            return task;
        } else {
            throw new RuntimeException();
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    public MyTasks createNewTask(TaskAddRequestDTO taskAddRequestDTO){
        MyTasks task = modelMapper.map(taskAddRequestDTO , MyTasks.class);
        trimTaskFields(task);
        task.setStatus(task.getStatus() == null ? TaskStatusEnum.NO_STATUS: task.getStatus());

        return repository.save(task);

    }
    public void updateTask(MyTasks task) {
        trimTaskFields(task);
        task.setStatus(task.getStatus() == null ? TaskStatusEnum.NO_STATUS : task.getStatus());
        repository.save(task);
    }
    public void deleteTask(Integer id) {
        repository.deleteById(id);
    }


    private void trimTaskFields(MyTasks task) {
        task.setAssignees(task.getAssignees() != null ? task.getAssignees().trim() : null);
        task.setTitle(task.getTitle() != null ? task.getTitle().trim() : null);
        task.setDescription(task.getDescription() != null ? task.getDescription().trim() : null);
    }

}
