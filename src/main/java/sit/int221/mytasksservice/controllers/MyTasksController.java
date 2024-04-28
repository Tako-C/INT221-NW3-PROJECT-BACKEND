package sit.int221.mytasksservice.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sit.int221.mytasksservice.dtos.response.TaskDetailResponseDTO;
import sit.int221.mytasksservice.dtos.response.TaskTableResponseDTO;
import sit.int221.mytasksservice.entities.MyTasks;
import sit.int221.mytasksservice.services.MyTasksService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://ip23nw3.sit.kmutt.ac.th:3333","http://localhost:5173"})

@RequestMapping("/v1")
public class MyTasksController {
    @Autowired
    private MyTasksService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/tasks")
        public List<TaskTableResponseDTO> getAllTasks() {
            List<MyTasks> mytasks = service.getAllTasks();
            return mytasks.stream()
                    .map(task -> modelMapper.map(task, TaskTableResponseDTO.class))
                    .collect(Collectors.toList());
        }
    @GetMapping("/tasks/{id}")
    public TaskDetailResponseDTO getTaskById(@PathVariable Integer id) {
        MyTasks task = service.getTask(id);
        return modelMapper.map(task, TaskDetailResponseDTO.class);
    }



}
