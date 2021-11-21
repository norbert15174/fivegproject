package pl.projectfiveg.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import pl.projectfiveg.exceptions.UnAuthException;
import pl.projectfiveg.services.interfaces.ITokenPrivateKey;
import pl.projectfiveg.services.interfaces.IUserService;

@Component
public class AuthChannelInterceptorAdapter implements ChannelInterceptor {
    private static final String TOKEN_HEADER = "Authorization";
    private final IUserService userService;
    private final ITokenPrivateKey tokenPrivateKey;

    @Autowired
    public AuthChannelInterceptorAdapter(IUserService userService , ITokenPrivateKey tokenPrivateKey) {
        this.userService = userService;
        this.tokenPrivateKey = tokenPrivateKey;
    }

    @Override
    public Message <?> preSend(final Message <?> message , final MessageChannel channel) throws AuthenticationException {
        final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message , StompHeaderAccessor.class);

        if ( StompCommand.CONNECT == accessor.getCommand() ) {
            final String username = accessor.getFirstNativeHeader(TOKEN_HEADER);

            final UsernamePasswordAuthenticationToken user;
            try {
                user = getUsernamePasswordAuthenticationToken(username);
                accessor.setUser(user);
            } catch ( UnAuthException e ) {
                throw new AuthenticationCredentialsNotFoundException("Token is invalid");
            }
        }
        return message;
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String authorization) throws UnAuthException {
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512(tokenPrivateKey.getKey())).build();
            DecodedJWT verify = jwtVerifier.verify(authorization.substring(7));
            String username = verify.getClaim("login").asString();
            String salt = verify.getClaim("salt").asString();
            UserDetails userDetails = userService.accountVerifyToken(username , Long.parseLong(salt));
            if ( userDetails == null ) throw new UnAuthException();

            return new UsernamePasswordAuthenticationToken(userDetails , null , userDetails.getAuthorities());
        } catch ( Exception e ) {
            throw new UnAuthException();
        }
    }
}