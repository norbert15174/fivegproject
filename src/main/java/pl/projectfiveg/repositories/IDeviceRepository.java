package pl.projectfiveg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.projectfiveg.models.Device;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IDeviceRepository extends JpaRepository <Device, String>, JpaSpecificationExecutor <Device> {
    @Query("select d from Device d where d.uuid = :uuid")
    Optional <Device> getByIdOpt(@Param("uuid") String uuid);

    @Query("select d from Device d where d.connectionDate < :time and d.status <> pl.projectfiveg.models.enums.CurrentStatus.INACTIVE")
    List <Device> findDevicesStatusChange(LocalDateTime time);
}
