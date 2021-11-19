package pl.projectfiveg.models.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_LINUX("ROLE_LINUX"),
    ROLE_IOS("ROLE_IOS"),
    ROLE_ANDROID("ROLE_ANDROID"),
    ROLE_WEB_CLIENT("ROLE_WEB_CLIENT"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}
