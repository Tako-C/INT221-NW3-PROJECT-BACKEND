package sit.int221.mytasksservice.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sit.int221.mytasksservice.dtos.response.request.StatusAddRequestDTO;
import sit.int221.mytasksservice.dtos.response.response.GeneralException;
import sit.int221.mytasksservice.dtos.response.response.ItemNotFoundException;
import sit.int221.mytasksservice.entities.Status;
import sit.int221.mytasksservice.repositories.StatusRepository;

import java.util.List;
import java.util.Optional;

@Service
public class StatusService {

    @Autowired
    private StatusRepository repository;

    @Autowired
    private ModelMapper modelMapper;

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
        }
    }
    public Status createNewStatus(StatusAddRequestDTO statusAddRequestDTO) {
        Status status = modelMapper.map(statusAddRequestDTO , Status.class);
        Status statusCheckName = repository.findByStatusName(statusAddRequestDTO.getStatusName());
        if (statusAddRequestDTO.getStatusName() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status Name is not null !!");
        }
        if (statusCheckName != null && statusCheckName.getStatusName() == statusAddRequestDTO.getStatusName()) {
            throw new GeneralException();
        }
        return repository.save(status);
    }


}
