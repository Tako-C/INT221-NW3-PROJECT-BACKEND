package sit.int221.mytasksservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.mytasksservice.entities.MyTasks;
import sit.int221.mytasksservice.repositories.MyTasksRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MyTasksService {
    @Autowired
    private MyTasksRepository repository;

    public List<MyTasks> getAllTasks() {
            return repository.findAll();
    }
    public MyTasks getTask(Integer id) {
        Optional<MyTasks> optionalTask = repository.findById(id);
        if (optionalTask.isPresent()) {
            return optionalTask.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task " + id +" does not exist !!!");
        }
    }
}
