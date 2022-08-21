package com.vaneezy.TestTask.Controllers.RestControllers;

import com.vaneezy.TestTask.Entities.ApplicationUser.AppUser;
import com.vaneezy.TestTask.Services.SessionService;
import com.vaneezy.TestTask.Services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;
    private final SessionService sessionService;

    public UserController(UserService userService, SessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @GetMapping
    public ResponseEntity<Iterable<AppUser>> getAllUsers(){
        Iterable<AppUser> allUsers = userService.getAll();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/logged")
    public ResponseEntity<Iterable<AppUser>> getLoggedUsers(){
        List<String> allLoggedUsernames = sessionService.getAllLoggedUsernames();
        Iterable<AppUser> loggedUsers = userService.getLoggedUsers(allLoggedUsernames);
        return ResponseEntity.ok(loggedUsers);
    }

    @GetMapping("/notrophy")
    public ResponseEntity<Iterable<AppUser>> usersWithOutTrophies(){
        Iterable<AppUser> usersWithoutTrophies = userService.getUsersWithoutTrophies();

        return ResponseEntity.ok(usersWithoutTrophies);
    }
}
