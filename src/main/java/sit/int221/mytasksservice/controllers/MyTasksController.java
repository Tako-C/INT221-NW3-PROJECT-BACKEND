package sit.int221.mytasksservice.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int221.mytasksservice.dtos.response.request.TaskAddRequestDTO;
import sit.int221.mytasksservice.dtos.response.request.TaskDeleteRequestDTO;
import sit.int221.mytasksservice.dtos.response.request.TaskUpdateRequestDTO;
import sit.int221.mytasksservice.dtos.response.response.TaskDetailResponseDTO;
import sit.int221.mytasksservice.dtos.response.response.TaskTableResponseDTO;
import sit.int221.mytasksservice.entities.MyTasks;
import sit.int221.mytasksservice.services.MyTasksService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:5173","http://ip23nw3.sit.kmutt.ac.th:3333"})

@RequestMapping("/v2")
public class MyTasksController {
    @Autowired
    private MyTasksService service;

    @Autowired
    private ModelMapper modelMapper;

//    @GetMapping("/tasks")
//        public List<TaskTableResponseDTO> getAllTasks() {
//            List<MyTasks> mytasks = service.getAllTasks();
//            return mytasks.stream()
//                    .map(task -> modelMapper.map(task, TaskTableResponseDTO.class))
//                    .collect(Collectors.toList());
//
//        }
@GetMapping("/tasks")
public List<TaskTableResponseDTO> getAllTasks() {
    List<MyTasks> mytasks = service.getAllTasks();
    return mytasks.stream()
            .map(task -> {
                TaskTableResponseDTO responseDTO = modelMapper.map(task, TaskTableResponseDTO.class);
                responseDTO.setStatusName(task.getStatus().getStatusName());
                return responseDTO;
            })
            .collect(Collectors.toList());
}
//    @GetMapping("/tasks/{id}")
//    public TaskDetailResponseDTO getTaskById(@PathVariable Integer id) {
//        MyTasks task = service.getTask(id);
//        return modelMapper.map(task, TaskDetailResponseDTO.class);
//    }
@GetMapping("/tasks/{id}")
public TaskDetailResponseDTO getTaskById(@PathVariable Integer id)  {
    MyTasks task = service.getTask(id);
    TaskDetailResponseDTO responseDTO = modelMapper.map(task, TaskDetailResponseDTO.class);
    responseDTO.setStatusName(task.getStatus().getStatusName());
    return responseDTO;
}

    @PostMapping("/tasks")
    public ResponseEntity<TaskAddRequestDTO> addTask(@RequestBody TaskAddRequestDTO taskAddRequestDTO ){
        MyTasks  createdTask = service.createNewTask(taskAddRequestDTO);
        TaskAddRequestDTO createdTaskDTO = modelMapper.map(createdTask, TaskAddRequestDTO.class);
        URI location = URI.create("/tasks/");
        if (createdTaskDTO.getTitle() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.created(location).body(createdTaskDTO);
    }


//    @PostMapping("/tasks")
//    public ResponseEntity<TaskAddRequestDTO> addTask(@RequestBody TaskAddRequestDTO taskAddRequestDTO) {
//        try {
//            MyTasks createdTask = service.createNewTask(taskAddRequestDTO);
//            TaskAddRequestDTO createdTaskDTO = modelMapper.map(createdTask, TaskAddRequestDTO.class);
//            URI location = URI.create("/tasks/");
//
//            return ResponseEntity.created(location).body(createdTaskDTO);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//
//
//        }
//    }

//    @PutMapping("/tasks/{id}")
//    public ResponseEntity<TaskUpdateRequestDTO> updateTask (@RequestBody TaskAddRequestDTO taskAddRequestDTO,@PathVariable Integer id){
//        MyTasks updatedTask = service.getTask(id);
//        TaskUpdateRequestDTO updatedTaskDTO = modelMapper.map(updatedTask, TaskUpdateRequestDTO.class);
//
//        updatedTask.setTitle(taskAddRequestDTO.getTitle());
//        updatedTask.setDescription(taskAddRequestDTO.getDescription());
//        updatedTask.setAssignees(taskAddRequestDTO.getAssignees());
////        updatedTask.setStatus(taskAddRequestDTO.getStatus());
//
//        service.updateTask(updatedTask);
//        return ResponseEntity.ok().body(updatedTaskDTO);
//    }
@PutMapping("/tasks/{id}")
public ResponseEntity<TaskUpdateRequestDTO> updateTask (@RequestBody TaskAddRequestDTO taskAddRequestDTO,@PathVariable Integer id) {
    MyTasks updatedTask = service.getTask(id);
    TaskUpdateRequestDTO updatedTaskDTO = modelMapper.map(updatedTask, TaskUpdateRequestDTO.class);

    updatedTaskDTO.setTitle(taskAddRequestDTO.getTitle());
    updatedTaskDTO.setDescription(taskAddRequestDTO.getDescription());
    updatedTaskDTO.setAssignees(taskAddRequestDTO.getAssignees());
    updatedTaskDTO.setStatusName(taskAddRequestDTO.getStatusName());

    service.updateTask(updatedTaskDTO);
    return ResponseEntity.ok().body(updatedTaskDTO);
}
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<TaskDeleteRequestDTO> deleteTask(@PathVariable Integer id) {
        MyTasks deletedTask = service.getTask(id);
        TaskDeleteRequestDTO deletedTaskDTO = modelMapper.map(deletedTask, TaskDeleteRequestDTO.class);

        deletedTask.setId(deletedTaskDTO.getId());
        deletedTask.setTitle(deletedTaskDTO.getTitle());
        deletedTask.setAssignees(deletedTaskDTO.getAssignees());
        deletedTaskDTO.setStatusName(deletedTask.getStatus().getStatusName());

        service.deleteTask(id);
        return ResponseEntity.ok().body(deletedTaskDTO);
    }
}
