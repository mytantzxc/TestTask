package com.vaneezy.TestTask.Controllers.RestControllers;

import com.vaneezy.TestTask.Entities.Trophy;
import com.vaneezy.TestTask.Services.SessionService;
import com.vaneezy.TestTask.Services.TrophyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("api/v1/trophy")
public class TrophyController {

    private final TrophyService trophyService;
    private final SessionService sessionService;

    @Autowired
    public TrophyController(TrophyService trophyService, SessionService sessionService){
        this.trophyService = trophyService;
        this.sessionService = sessionService;
    }

    @GetMapping
    public ResponseEntity<?> getUserTrophiesSum(HttpSession session){
        String usernameFromSession = sessionService.getUsernameFromSession(session.getId());
        Integer trophyCountSum = trophyService.userTrophyCountSum(usernameFromSession);
        return ResponseEntity.ok(Map.of(
                "username", usernameFromSession,
                "Trophy sum", trophyCountSum
        ));
    }

    //все трофеи юзера
    @GetMapping("/list")
    public ResponseEntity<Iterable<Trophy>> getUserTrophies(HttpSession session){
        String usernameFromSession = sessionService.getUsernameFromSession(session.getId());
        Iterable<Trophy> userTrophies = trophyService.getUserTrophies(usernameFromSession);

        return ResponseEntity.ok(userTrophies);
    }

    @PostMapping("/add")
    public ResponseEntity<Trophy> addTrophy(@RequestBody int count, HttpSession session){
        String usernameFromSession = sessionService.getUsernameFromSession(session.getId());
        Trophy savedTrophy = trophyService.addTrophyForUser(count, usernameFromSession);

        return ResponseEntity.ok(savedTrophy);
    }

    @DeleteMapping("/del/{trophyId}")
    public ResponseEntity<Trophy> deleteTrophy(@PathVariable("trophyId") Long id){
        Trophy deletedTrophy = trophyService.deleteTrophy(id);

        return ResponseEntity.ok(deletedTrophy);
    }
}
