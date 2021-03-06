package pl.projectfiveg.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.projectfiveg.services.interfaces.ITokenPrivateKey;
import pl.projectfiveg.services.interfaces.IUserService;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final IUserService userService;
    private final ITokenPrivateKey tokenPrivateKey;

    @Autowired
    public WebSecurityConfig(IUserService userService , ITokenPrivateKey tokenPrivateKey) {
        this.userService = userService;
        this.tokenPrivateKey = tokenPrivateKey;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(HttpMethod.POST , "/auth/**")
                .antMatchers("/devicews")
                .antMatchers("/userws");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().authorizeRequests()
                .antMatchers("/client").authenticated()
                .antMatchers("/device").hasAnyRole("WEB_CLIENT" , "ADMIN")
                .antMatchers("/device/tasks").hasAnyRole("LINUX" , "IOS" , "ANDROID" , "ADMIN")
                .antMatchers("/device/active").hasAnyRole("LINUX" , "IOS" , "ANDROID" , "ADMIN")
                .antMatchers("/device/disconnect").hasAnyRole("LINUX" , "IOS" , "ANDROID" , "ADMIN")
                .antMatchers("/device/tasks/{taskId}/upload").hasAnyRole("LINUX" , "IOS" , "ANDROID" , "ADMIN")
                .antMatchers("/tasks").hasAnyRole("WEB_CLIENT" , "ADMIN")
                .antMatchers("/tasks/{taskId}/download").hasAnyRole("WEB_CLIENT" , "ADMIN")
                .and()
                .addFilterBefore(new JwtFilter(userService , tokenPrivateKey) , UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
        http.csrf().disable();

    }


}
