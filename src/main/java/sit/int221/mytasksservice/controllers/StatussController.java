package sit.int221.mytasksservice.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sit.int221.mytasksservice.dtos.response.request.StatusAddRequestDTO;
import sit.int221.mytasksservice.dtos.response.response.StatusDetailResponseDTO;
import sit.int221.mytasksservice.dtos.response.response.StatusTableResponseDTO;
import sit.int221.mytasksservice.entities.Status;
import sit.int221.mytasksservice.services.StatusService;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:5173","http://ip23nw3.sit.kmutt.ac.th:3333"})

@RequestMapping("/v2")
public class StatussController {
    @Autowired
    private StatusService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/statuses")
    public List<StatusTableResponseDTO> getAllStatus(){
        List<Status> statusList = service.getAllStatus();
        return statusList.stream()
                .map(status -> {
                    StatusTableResponseDTO responseDTO = modelMapper.map(status, StatusTableResponseDTO.class);
                    return responseDTO;
                })
                .collect(Collectors.toList());
    }
    @GetMapping("/statuses/{id}")
    public StatusDetailResponseDTO getStatusById(@PathVariable Integer id){
        Status status = service.getStatus(id);
        StatusDetailResponseDTO responseDTO = modelMapper.map(status, StatusDetailResponseDTO.class);
        return responseDTO;

    }
    @PostMapping("/statuses")
    public ResponseEntity<StatusAddRequestDTO> addStatus(@RequestBody StatusAddRequestDTO statusAddRequestDTO){
        Status createStatus = service.createNewStatus(statusAddRequestDTO);
        StatusAddRequestDTO addRequestDTO = modelMapper.map(createStatus, StatusAddRequestDTO.class);
        URI location = URI.create("/Statuses/");
        return ResponseEntity.created(location).body(addRequestDTO);
    }

}

