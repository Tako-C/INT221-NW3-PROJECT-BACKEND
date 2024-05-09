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
        Optional<Status> optionalStatus = repository.findById(id);
        if (optionalStatus.isPresent()) {
            Status status = optionalStatus.get();

            return status;
        } else {
            throw new ItemNotFoundException();
        }
    }

    public Status createNewStatus(StatusAddRequestDTO statusAddRequestDTO) {
        Status status = modelMapper.map(statusAddRequestDTO, Status.class);
        Status statusCheckName = repository.findByStatusName(statusAddRequestDTO.getStatusName());
        if (statusAddRequestDTO.getStatusName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status Name is not null !!");
        }
        if (statusCheckName != null && statusCheckName.getStatusName() == statusAddRequestDTO.getStatusName()) {
            throw new GeneralException();
        }
        return repository.save(status);
    }

    public Status updateStatus(StatusUpdateRequestDTO statusUpdateRequestDTO) {
        trimStatusFields(statusUpdateRequestDTO);
        Status status = modelMapper.map(statusUpdateRequestDTO, Status.class);
        if (statusUpdateRequestDTO == null || "No Status".equals(statusUpdateRequestDTO.getStatusName())) {
            throw new GeneralException();
        }
        Status existingStatus = repository.findByStatusName(statusUpdateRequestDTO.getStatusName());
        if (existingStatus != null && !existingStatus.getId().equals(status.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status name is used.");
        }
        status.setStatusName(statusUpdateRequestDTO.getStatusName());
        status.setStatusDescription(statusUpdateRequestDTO.getStatusDescription());
        return repository.save(status);
    }

    public void deleteStatus(Integer id) {
        List<MyTasks> tasksWithThisStatus = myTasksRepository.findByStatusId(id);
        if (!tasksWithThisStatus.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This status is using by another task!");
        }
        repository.deleteById(id);
    }

    public Status reassignAndDeleteStatus(Integer id, Integer newId) {
        List<MyTasks> tasksWithThisStatus = myTasksRepository.findByStatusId(id);
        if (!tasksWithThisStatus.isEmpty()) {
            Status newStatus = repository.findById(newId)
                    .orElseThrow(() -> new ItemNotFoundException());
            for (MyTasks task : tasksWithThisStatus) {
                task.setStatus(newStatus);
                myTasksRepository.save(task);
            }
        }
        Status deletedStatus = repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException());
        repository.deleteById(id);
        return deletedStatus;
    }

    private void trimStatusFields(StatusUpdateRequestDTO statusUpdateRequestDTO) {
        if (statusUpdateRequestDTO.getStatusName() != null) {
            statusUpdateRequestDTO.setStatusName(statusUpdateRequestDTO.getStatusName().trim());
        }
        if (statusUpdateRequestDTO.getStatusDescription() != null) {
            statusUpdateRequestDTO.setStatusDescription(statusUpdateRequestDTO.getStatusDescription().trim());
        }
    }
}

