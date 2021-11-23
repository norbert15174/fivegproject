package pl.projectfiveg.models;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.projectfiveg.DTO.UserDTO;
import pl.projectfiveg.models.enums.DeviceType;
import pl.projectfiveg.models.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.*;

@NoArgsConstructor
@Setter
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Size(min = 4, max = 20)
    private String login;
    @Column(nullable = false)
    @Size(min = 4, max = 20)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;
    private boolean enabled = true;
    private Long salt;
    @OneToMany(mappedBy = "user")
    private Set <Task> tasks = new HashSet <>();

    public Long getId() {
        return this.id;
    }

    public Long getSalt() {
        return this.salt;
    }

    public User(String login , String password , Role role , Long salt) {
        this.login = login;
        this.password = password;
        this.role = role;
        this.salt = salt;
    }

    public User(String login , String password , Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public UserDTO toUserDTO(String token) {
        return new UserDTO(this.login , this.role.getAuthority() , token);
    }

    @Override
    public Collection <? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role.toString()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) return true;
        if ( o == null || getClass() != o.getClass() ) return false;
        User user = (User) o;
        return this.getId() != null && Objects.equals(getId() , user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    public DeviceType getDeviceType() {
        switch (this.role) {
            case ROLE_IOS:
                return DeviceType.IOS;
            case ROLE_LINUX:
                return DeviceType.LINUX;
            case ROLE_ANDROID:
                return DeviceType.ANDROID;
            default:
                return DeviceType.INVALID_TYPE;
        }
    }

    public boolean isAdmin() {
        return this.role.equals(Role.ROLE_ADMIN);
    }
}
