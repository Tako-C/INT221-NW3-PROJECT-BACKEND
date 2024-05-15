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
@CrossOrigin(origins = {"http://localhost:5173","http://ip23nw3.sit.kmutt.ac.th","http://intproj23.sit.kmutt.ac.th"})

@RequestMapping("/v2")
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

//------------------------ Sortby ------------------------
    @GetMapping(value = "/tasks", params = {"sortBy","FilterStatuses"})
    public List<TaskTableResponseDTO> getTaskAsc(@RequestParam(value = "sortBy",defaultValue = "createdOn") String sort,
                                                 @RequestParam(value = "FilterStatuses",required = false) List<String> filterStatuses) {
        List<MyTasks> tasks ;
        if (filterStatuses == null || filterStatuses.isEmpty()){
            tasks = service.getAllTasksSortByAsc(sort);
        }
        else {
            tasks = service.getAllFilter(filterStatuses,sort);
        }
        return tasks.stream()
                .map(task -> modelMapper.map(task, TaskTableResponseDTO.class))
                .collect(Collectors.toList());

    }
//    @GetMapping(value = "/tasks", params = {"sortBy"})
//    public List<TaskTableResponseDTO> getTaskDesc(@RequestParam("sortBy") String sort) {
//        List<MyTasks> tasks = service.getAllTasksSortByAsc(sort);
//        return tasks.stream()
//                .map(task -> modelMapper.map(task, TaskTableResponseDTO.class))
//                .collect(Collectors.toList());
//    }
//---------------------------------------------------------

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

    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskUpdateRequestDTO> updateTask (@RequestBody TaskAddRequestDTO taskAddRequestDTO,@PathVariable Integer id){
        MyTasks updatedTask = service.getTask(id);
        TaskUpdateRequestDTO updatedTaskDTO = modelMapper.map(updatedTask, TaskUpdateRequestDTO.class);

    updatedTaskDTO.setTitle(taskAddRequestDTO.getTitle());
    updatedTaskDTO.setDescription(taskAddRequestDTO.getDescription());
    updatedTaskDTO.setAssignees(taskAddRequestDTO.getAssignees());
    updatedTaskDTO.setStatus(taskAddRequestDTO.getStatus());

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
        deletedTaskDTO.setStatusName(deletedTask.getStatus().getDescription());

        service.deleteTask(id);
        return ResponseEntity.ok().body(deletedTaskDTO);
    }
}
