package sit.int221.mytasksservice.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int221.mytasksservice.dtos.MyTasksDTO;
import sit.int221.mytasksservice.entities.MyTasks;
import sit.int221.mytasksservice.services.MyTasksService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/v1")
public class MyTasksController {
    @Autowired
    private MyTasksService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/tasks")
        public List<MyTasksDTO> getAllTasks() {
            List<MyTasks> mytasks = service.getAllTasks();
            return mytasks.stream()
                    .map(task -> modelMapper.map(task, MyTasksDTO.class))
                    .collect(Collectors.toList());
        }


    @GetMapping("/tasks/{id}")
    public MyTasks getTaskById(@PathVariable Integer id){
        return service.getTask(id);
    }
}
