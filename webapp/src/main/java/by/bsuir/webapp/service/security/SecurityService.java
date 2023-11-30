package by.bsuir.webapp.service.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    public boolean isCurrentUserAccountId(Long id) {
        PersitedUser user = (PersitedUser)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getAccountId().equals(id);
    }
}
