package sit.int221.mytasksservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int221.mytasksservice.entities.MyTasks;

public interface MyTasksRepository extends JpaRepository<MyTasks, Integer> {

}
