package com.vaneezy.TestTask.Services;

import com.vaneezy.TestTask.Entities.ApplicationUser.AppUser;
import com.vaneezy.TestTask.Entities.Trophy;
import com.vaneezy.TestTask.Repositories.TrophyRepository;
import com.vaneezy.TestTask.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
public class TrophyService {

    private final UserRepository userRepository;
    private final TrophyRepository trophyRepository;

    @Autowired
    public TrophyService(UserRepository userRepository, TrophyRepository trophyRepository){
        this.userRepository = userRepository;
        this.trophyRepository = trophyRepository;
    }

    public Integer userTrophyCountSum(String username){
        return trophyRepository.userTrophyCountSum(username);
    }

    public Trophy addTrophyForUser(Integer count, String usernameFromSession) {
        AppUser user = userRepository.findByUsername(usernameFromSession);

        Trophy trophy = new Trophy(
                user,
                LocalDateTime.now(),
                count
        );

        Trophy saved = trophyRepository.save(trophy);

        return saved;
    }

    public Trophy deleteTrophy(Long id) {
        Trophy trophy = trophyRepository.findById(id)
                .orElseThrow(
                        () -> new NoSuchElementException("No trophy with this id")
                );
        trophyRepository.delete(trophy);
        return trophy;
    }

    public Iterable<Trophy> getUserTrophies(String username) {
        return trophyRepository.getTrophiesByUsername(username);
    }
}
