package by.bsuir.webapp.service.security.handler;

import by.bsuir.webapp.model.Account;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RefererRedirectionAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        if(isAdmin(authentication)) {
            getRedirectStrategy().sendRedirect(request, response, "admin/accounts");
            clearAuthenticationAttributes(request);
        }

        DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        if(defaultSavedRequest != null){
            getRedirectStrategy().sendRedirect(request, response, defaultSavedRequest.getRedirectUrl());
        }else{
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }

    private boolean isAdmin(Authentication authentication) {
        for (final GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
            String authorityName = grantedAuthority.getAuthority();
            if(authorityName.equals(Account.Role.ADMIN.name())) {
                return true;
            }
        }
        return false;
    }
}
