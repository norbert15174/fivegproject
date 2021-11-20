package pl.projectfiveg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.projectfiveg.models.Task;

@Repository
public interface ITaskRepository extends JpaRepository <Task, Long> {
}
