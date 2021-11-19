package pl.projectfiveg.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.projectfiveg.models.User;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository <User, Long> {
    @Query("select u from User u where u.login = :login")
    User findByLogin(@Param("login") String login);

    @Query("select u from User u where u.login = :login")
    Optional <User> findByLoginOpt(@Param("login") String login);
}
