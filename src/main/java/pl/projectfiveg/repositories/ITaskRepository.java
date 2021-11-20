package pl.projectfiveg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.projectfiveg.DTO.TaskDTO;
import pl.projectfiveg.models.Task;

import java.util.Set;

@Repository
public interface ITaskRepository extends JpaRepository <Task, Long>, JpaSpecificationExecutor <Task> {
    @Query("select new pl.projectfiveg.DTO.TaskDTO(t) from Task t inner join t.device d where d.uuid = :deviceUuId and t.status <> pl.projectfiveg.models.enums.TaskStatus.FINISHED")
    Set <TaskDTO> findTasksByDeviceUuid(@Param("deviceUuId") String deviceUuId);

    @Query("select t from Task t inner join t.device d where d.uuid = :deviceUuId and t.status <> pl.projectfiveg.models.enums.TaskStatus.FINISHED")
    Set <Task> findFullTasksByDeviceUuid(@Param("deviceUuId") String deviceUuId);

}
