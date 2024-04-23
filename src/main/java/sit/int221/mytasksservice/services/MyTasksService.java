package sit.int221.mytasksservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sit.int221.mytasksservice.entities.MyTasks;
import sit.int221.mytasksservice.repositories.MyTasksRepository;

import java.util.List;

@Service
public class MyTasksService {
    @Autowired
    private MyTasksRepository repository;

    public List<MyTasks> getAllTasks() {
            return repository.findAll();
    }
}
