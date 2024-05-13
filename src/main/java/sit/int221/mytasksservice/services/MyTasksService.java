package sit.int221.mytasksservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.mytasksservice.dtos.response.request.TaskAddRequestDTO;
import sit.int221.mytasksservice.dtos.response.request.TaskUpdateRequestDTO;
import sit.int221.mytasksservice.entities.MyTasks;
import sit.int221.mytasksservice.entities.Status;
import sit.int221.mytasksservice.repositories.MyTasksRepository;
import org.modelmapper.ModelMapper;
import sit.int221.mytasksservice.repositories.StatusRepository;

import java.util.List;
import java.util.Optional;
import java.util.Collections;

@Service
public class MyTasksService {
    @Autowired
    private MyTasksRepository repository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private ModelMapper modelMapper;
    public List<MyTasks> getAllTasks() {
        List<MyTasks> tasks = repository.findAll();
        for (MyTasks task : tasks) {
            trimTaskFields(task);
        }
//        if (tasks.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.OK, "No tasks found");
////            return Collections.emptyList();
//        }

        return tasks;
    }
    public MyTasks getTask(Integer id) {
        Optional<MyTasks> optionalTask = repository.findById(id);
        if (optionalTask.isPresent()) {
            MyTasks task = optionalTask.get();
            trimTaskFields(task);
            return task;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
    public MyTasks createNewTask(TaskAddRequestDTO taskAddRequestDTO){
        Integer findbyIdStatus = Integer.valueOf(taskAddRequestDTO.getStatus());

        MyTasks task = modelMapper.map(taskAddRequestDTO , MyTasks.class);
//        Status statusCheckName = statusRepository.findByName(taskAddRequestDTO.getStatus());
        trimTaskFields(task);
//        if (statusCheckName == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "StatusName is not found !!");
//
//        }
            if (taskAddRequestDTO.getDescription() != null &&taskAddRequestDTO.getDescription().isEmpty()) {
            task.setDescription(null);
            }
            if (taskAddRequestDTO.getAssignees() != null && taskAddRequestDTO.getAssignees().isEmpty()) {
                task.setAssignees(null);
            }

        if (taskAddRequestDTO.getStatus() == null) {
                task.setStatus(getStatusFromName("No Status"));

            } else {
                task.setStatus(statusRepository.findById(findbyIdStatus).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, " Status Not found")));
            }
            return repository.save(task);



    }
    public MyTasks updateTask(TaskUpdateRequestDTO taskUpdateRequestDTO) {
        Integer findbyIdStatus = Integer.valueOf(taskUpdateRequestDTO.getStatus());
        MyTasks task = modelMapper.map(taskUpdateRequestDTO , MyTasks.class);
        trimTaskFields(task);
        task.setTitle(taskUpdateRequestDTO.getTitle());
        task.setDescription(taskUpdateRequestDTO.getDescription());
        task.setAssignees(taskUpdateRequestDTO.getAssignees());

        if (taskUpdateRequestDTO.getStatus() == null) {
            task.setStatus(getStatusFromName("No Status"));
            taskUpdateRequestDTO.setStatus("No Status");
        } else {
//            task.setStatus(getStatusFromName(taskUpdateRequestDTO.getStatus()));
            task.setStatus(statusRepository.findById(findbyIdStatus).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, " Status Not found")));
        }

        return repository.save(task);
    }
    public void deleteTask(Integer id) {
        repository.deleteById(id);
    }


    private void trimTaskFields(MyTasks task) {
        task.setAssignees(task.getAssignees() != null ? task.getAssignees().trim() : null);
        task.setTitle(task.getTitle() != null ? task.getTitle().trim() : null);
        task.setDescription(task.getDescription() != null ? task.getDescription().trim() : null);
    }
    private Status getStatusFromName(String statusName) {
        Status status = statusRepository.findByName(statusName);
        if (status == null) {
            throw new RuntimeException("Status not found with name: " + statusName);
        }

        return status;
    }

}
