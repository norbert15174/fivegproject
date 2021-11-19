package pl.projectfiveg.services.interfaces;

import org.springframework.security.core.userdetails.UserDetails;

public interface IUserService {
    UserDetails accountVerifyToken(String username , Long salt);
}
