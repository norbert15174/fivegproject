package pl.projectfiveg.services.interfaces;

import org.springframework.security.core.userdetails.UserDetails;
import pl.projectfiveg.models.User;

public interface IUserService {
    UserDetails accountVerifyToken(String username , Long salt);

    User getUserByLogin(String name);
}
