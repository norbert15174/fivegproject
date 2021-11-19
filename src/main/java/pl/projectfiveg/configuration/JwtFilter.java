package pl.projectfiveg.configuration;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.projectfiveg.exceptions.UnAuthException;
import pl.projectfiveg.services.interfaces.ITokenPrivateKey;
import pl.projectfiveg.services.interfaces.IUserService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    private final IUserService userService;
    private final ITokenPrivateKey tokenPrivateKey;

    public JwtFilter(IUserService userService , ITokenPrivateKey tokenPrivateKey) {
        this.userService = userService;
        this.tokenPrivateKey = tokenPrivateKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request , HttpServletResponse response , FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        try {
            UsernamePasswordAuthenticationToken authenticationToken = getUsernamePasswordAuthenticationToken(authorization);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request , response);
        } catch ( UnAuthException e ) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED , "Authentication Failed");
        }
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
