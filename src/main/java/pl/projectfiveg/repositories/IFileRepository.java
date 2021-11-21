package pl.projectfiveg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.projectfiveg.models.File;

import java.util.Optional;

@Repository
public interface IFileRepository extends JpaRepository <File, Long> {
    @Query("select f from File f inner join fetch f.task t inner join fetch t.user where t.id = :taskId")
    Optional <File> findFileByTaskId(@Param("taskId") Long taskId);
}
