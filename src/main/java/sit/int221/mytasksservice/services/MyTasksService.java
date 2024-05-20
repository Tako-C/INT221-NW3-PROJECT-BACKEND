package sit.int221.mytasksservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
import java.util.stream.Collectors;

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

    public List<MyTasks> getAllTasksSortByAsc(String sort) {
        return repository.findAll(Sort.by(Sort. Direction.ASC, sort));
    }
    public List<MyTasks> getAllFilter(List<String> filterStatuses, String sort) {
        List<MyTasks> filteredTasks = getAllTasksSortByAsc(sort).stream()
                .filter(task -> filterStatuses.contains(task.getStatus().getName()))
                .collect(Collectors.toList());
        return filteredTasks;
    }
    public MyTasks createNewTask(TaskAddRequestDTO taskAddRequestDTO) {
        validateStatus(taskAddRequestDTO.getStatus());

        MyTasks task = modelMapper.map(taskAddRequestDTO, MyTasks.class);
        processTaskFields(task, taskAddRequestDTO.getDescription(), taskAddRequestDTO.getAssignees());

        Integer convertStatusId = Integer.valueOf(taskAddRequestDTO.getStatus());
        task.setStatus(statusRepository.findById(convertStatusId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Status Not Found")));
        return repository.save(task);
    }
    public MyTasks updateTask(TaskUpdateRequestDTO taskUpdateRequestDTO) {
        validateStatus(taskUpdateRequestDTO.getStatus());

        MyTasks task = modelMapper.map(taskUpdateRequestDTO, MyTasks.class);
        processTaskFields(task, taskUpdateRequestDTO.getDescription(), taskUpdateRequestDTO.getAssignees());

        Integer convertStatusId = Integer.valueOf(taskUpdateRequestDTO.getStatus());
        task.setStatus(statusRepository.findById(convertStatusId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Status Not Found")));

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
    private void validateStatus(String status) {
        if (status == null || status.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status is not null or empty!");
        }
    }
    private void processTaskFields(MyTasks task, String description, String assignees) {
        trimTaskFields(task);

        if (description != null && description.isEmpty()) {
            task.setDescription(null);
        } else {
            task.setDescription(description);
        }

        if (assignees != null && assignees.isEmpty()) {
            task.setAssignees(null);
        } else {
            task.setAssignees(assignees);
        }
    }

}
