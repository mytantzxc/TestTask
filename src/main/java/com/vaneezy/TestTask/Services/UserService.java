package com.vaneezy.TestTask.Services;

import com.vaneezy.TestTask.Entities.ApplicationUser.AppUser;
import com.vaneezy.TestTask.Entities.ApplicationUser.RegistrationRequest;
import com.vaneezy.TestTask.Entities.ApplicationUser.Role;
import com.vaneezy.TestTask.Repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByUsername(username);
        if(appUser == null) return null;

        User user = new User(
                appUser.getUsername(), appUser.getPassword(), Collections.singletonList(new SimpleGrantedAuthority(appUser.getRole().name()))
        );

        return user;
    }

    public boolean registerUser(RegistrationRequest registrationRequest) {
        UserDetails user = loadUserByUsername(registrationRequest.getUsername());
        if(user != null) return false;

        AppUser appUser = new AppUser(
                registrationRequest.getUsername(),
                registrationRequest.getEmail(),
                passwordEncoder.encode(registrationRequest.getPassword()),
                Role.USER
        );

        userRepository.save(appUser);
        return true;
    }

    public Iterable<AppUser> getAll() {
        return userRepository.findAll();
    }

    public Iterable<AppUser> getLoggedUsers(List<String> allLoggedUsernames) {
        return userRepository.findLoggedUsers(allLoggedUsernames);
    }

    public Iterable<AppUser> getUsersWithoutTrophies() {
        return userRepository.findUsersWithoutTrophies();
    }
}
