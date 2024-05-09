
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
    public Status getStatus(Integer id){
        Optional<Status> optionalStatus = repository.findById(id);

        if (optionalStatus.isPresent()) {
            Status status = optionalStatus.get();

            return status;
        } else {
            throw new ItemNotFoundException();
//            throw new RuntimeException();
        }
    }
    public Status createNewStatus(StatusAddRequestDTO statusAddRequestDTO) {
        trimStatusFieldsAdd(statusAddRequestDTO);
        Status status = modelMapper.map(statusAddRequestDTO , Status.class);
        Status statusCheckName = repository.findByName(statusAddRequestDTO.getName());
//        Optional<Status> statusId = repository.findById(Integer.valueOf(statusAddRequestDTO.getName()));
//
//        if (statusId.isEmpty()){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Status ID is not found !!");
//        }
        if (status.getName() == null || statusAddRequestDTO.getName().isEmpty() ) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status Name is not null or empty !!");

        }
//        if (statusAddRequestDTO.getDescription().isEmpty()) {
//           status.setDescription(null);
//        }
        if (statusCheckName != null && statusCheckName.getName() == statusAddRequestDTO.getName()) {
            throw new GeneralException();
        }
        return repository.save(status);
    }

    public Status updateStatus(StatusUpdateRequestDTO statusUpdateRequestDTO) {
        trimStatusFieldsEdit(statusUpdateRequestDTO);
        Status status = modelMapper.map(statusUpdateRequestDTO, Status.class);

        if ( "No Status".equals(statusUpdateRequestDTO.getName()) || statusUpdateRequestDTO.getId() == 1) {
            throw new GeneralException();
        }
        if(statusUpdateRequestDTO.getName().isEmpty() || statusUpdateRequestDTO.getName() == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  "Status Name is not null or empty !!");
        }
        if(statusUpdateRequestDTO.getDescription().isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,  "Status Description is empty !!");
        }
        Status existingStatus = repository.findByName(statusUpdateRequestDTO.getName());
        if (existingStatus != null && !existingStatus.getId().equals(status.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status name is used.");
        }
        status.setName(statusUpdateRequestDTO.getName());
        status.setDescription(statusUpdateRequestDTO.getDescription());
        return repository.save(status);
    }

    public void deleteStatus(Integer id) {
        List<MyTasks> tasksWithThisStatus = myTasksRepository.findByStatusId(id);
//        if (!tasksWithThisStatus.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This status is using by another task!");
//            throw new GeneralException();

//        }
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

    private void trimStatusFieldsEdit(StatusUpdateRequestDTO statusUpdateRequestDTO) {
        if (statusUpdateRequestDTO.getName() != null) {
            statusUpdateRequestDTO.setName(statusUpdateRequestDTO.getName().trim());
        }
        if (statusUpdateRequestDTO.getDescription() != null) {
            statusUpdateRequestDTO.setDescription(statusUpdateRequestDTO.getDescription().trim());
        }
    }
    private void trimStatusFieldsAdd(StatusAddRequestDTO statusAddRequestDTO) {
        if (statusAddRequestDTO.getName() != null) {
            statusAddRequestDTO.setName(statusAddRequestDTO.getName().trim());
        }
        if (statusAddRequestDTO.getDescription() != null) {
            statusAddRequestDTO.setDescription(statusAddRequestDTO.getDescription().trim());
        }
    }
}

