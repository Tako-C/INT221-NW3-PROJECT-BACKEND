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
@CrossOrigin(origins = "https://localhost:5173")
@RequestMapping("/v1/tasks")
public class MyTasksController {
    @Autowired
    private MyTasksService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("")
    public ResponseEntity<List<MyTasksDTO>> getAllTasks(@RequestParam(required = false) String[] param) {
        List<MyTasks> mytasks = service.getAllTasks(param);
        List<MyTasksDTO> mytasksDTO = mytasks.stream()
                .map(task -> modelMapper.map(task, MyTasksDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(mytasksDTO);
    }

//    @GetMapping("")
//    public List<MyTasks> getAllTasks(@RequestParam(required = false) String[] param) {
//        return service.getAllTasks(param);
//    }
}
