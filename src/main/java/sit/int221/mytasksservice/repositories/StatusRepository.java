package sit.int221.mytasksservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int221.mytasksservice.entities.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {
    Status findByName(String Name);
}
