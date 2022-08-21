package com.vaneezy.TestTask.Config.Security;

import com.vaneezy.TestTask.Services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomLogoutHandler implements LogoutHandler {

    private final SessionRegistry sessionRegistry;
    @Autowired
    public CustomLogoutHandler(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        SessionInformation sessionInformation = sessionRegistry.getSessionInformation(request.getSession().getId());
        sessionInformation.expireNow();
    }
}
