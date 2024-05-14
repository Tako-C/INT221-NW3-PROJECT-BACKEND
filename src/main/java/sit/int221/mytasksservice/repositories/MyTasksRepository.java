package sit.int221.mytasksservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int221.mytasksservice.entities.MyTasks;

import java.util.List;

public interface MyTasksRepository extends JpaRepository<MyTasks, Integer> {
    List<MyTasks> findByStatusId(Integer statusId);
}
