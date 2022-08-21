package com.vaneezy.TestTask.Services;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SessionService {

    private final SessionRegistry sessionRegistry;

    public SessionService(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    public String getUsernameFromSession(String sessionId){
        SessionInformation sessionInformation = sessionRegistry.getSessionInformation(sessionId);
        User user = (User) sessionInformation.getPrincipal();
        return user.getUsername();
    }

    public List<String> getAllLoggedUsernames() {
        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        List<String> loggedUsernames = new ArrayList<>();

        UserDetails userDetails = null;
        User user = null;
        List<SessionInformation> sessionInformations = null;
        for (Object principal : allPrincipals) {
            userDetails = (UserDetails) principal;
            sessionInformations = sessionRegistry.getAllSessions(principal, false);
            if(sessionInformations.size() != 0) {
                user = (User) sessionInformations.get(0).getPrincipal();
                loggedUsernames.add(user.getUsername());
            }
        }
        return loggedUsernames;
    }
}
