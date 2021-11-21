package pl.projectfiveg.configuration;

import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.socket.WebSocketMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class AuthenticationEntryPointHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request , HttpServletResponse response , AuthenticationException authException)
            throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED , "Authentication Failed");
    }

    @ExceptionHandler(value = {AuthenticationCredentialsNotFoundException.class})
    public void commence(HttpServletRequest request , HttpServletResponse response ,
                         AuthenticationCredentialsNotFoundException accessDeniedException) throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN , "Authorization Failed : " + accessDeniedException.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    public void commence(HttpServletRequest request , HttpServletResponse response ,
                         Exception exception) throws IOException {
        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR , "Internal Server Error : " + exception.getMessage());
    }

}
