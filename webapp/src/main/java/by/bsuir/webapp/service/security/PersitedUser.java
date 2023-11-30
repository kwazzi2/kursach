package by.bsuir.webapp.service.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class PersitedUser extends User {
    private Long accountId;

    public Long getAccountId() {
        return accountId;
    }

    public PersitedUser(Long accountId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.accountId = accountId;
    }

    public PersitedUser(Long accountId, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.accountId = accountId;
    }
}
