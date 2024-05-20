package sit.int221.mytasksservice.services;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.mytasksservice.dtos.response.request.StatusAddRequestDTO;
import sit.int221.mytasksservice.dtos.response.request.StatusDeleteRequestDTO;
import sit.int221.mytasksservice.dtos.response.request.StatusUpdateRequestDTO;
import sit.int221.mytasksservice.dtos.response.request.TaskUpdateRequestDTO;
import sit.int221.mytasksservice.dtos.response.response.GeneralException;
import sit.int221.mytasksservice.dtos.response.response.ItemNotFoundException;
import sit.int221.mytasksservice.entities.MyTasks;
import sit.int221.mytasksservice.entities.Status;
import sit.int221.mytasksservice.repositories.MyTasksRepository;
import sit.int221.mytasksservice.repositories.StatusRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {

    @Autowired
    private StatusRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MyTasksRepository myTasksRepository;

    public List<Status> getAllStatus() {
        return repository.findAll();
    }

    public Status getStatus(Integer id) {
        return repository.findById(id)
                .orElseThrow(ItemNotFoundException::new);
    }

    public Status createNewStatus(StatusAddRequestDTO statusAddRequestDTO) {
        Status status = modelMapper.map(statusAddRequestDTO, Status.class);
        trimAndValidateStatusFields(status, statusAddRequestDTO.getName(), statusAddRequestDTO.getDescription());;

        checkStatusNameExists(statusAddRequestDTO.getName());

        return repository.save(status);
    }

    public Status updateStatus(StatusUpdateRequestDTO statusUpdateRequestDTO) {
        Status status = modelMapper.map(statusUpdateRequestDTO, Status.class);
        trimAndValidateStatusFields(status, statusUpdateRequestDTO.getName(), statusUpdateRequestDTO.getDescription());

        return repository.save(status);
    }

    public void deleteStatus(Integer id) {
        if (!myTasksRepository.findByStatusId(id).isEmpty()) {
            throw new GeneralException();
        }
        repository.deleteById(id);
    }

    public Status reassignAndDeleteStatus(Integer id, Integer newId) {
        List<MyTasks> tasksWithThisStatus = myTasksRepository.findByStatusId(id);
        Status newStatus = repository.findById(newId)
                .orElseThrow(ItemNotFoundException::new);

        tasksWithThisStatus.forEach(task -> {
            task.setStatus(newStatus);
            myTasksRepository.save(task);
        });

        Status deletedStatus = repository.findById(id)
                .orElseThrow(ItemNotFoundException::new);

        repository.deleteById(id);
        return deletedStatus;
    }

    private void trimAndValidateStatusFields(Status status, String name, String description) {
        if (name == null || name.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status name cannot be null or empty!");
        }
        if (description != null && description.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status description cannot be empty!");
        }
        if (description != null){
            status.setDescription(description.trim());

        }else {
            status.setName(name.trim());
        }
    }

    private void checkStatusNameExists(String name) {
        if (repository.findByName(name) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status name already exists!");
        }
    }

}
