package pro.bashkatov.pokerapi.entity;

import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@NoArgsConstructor
public class Role implements GrantedAuthority {
    String authority = "ROLE_USER";

    public Role(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return null;
    }
}
