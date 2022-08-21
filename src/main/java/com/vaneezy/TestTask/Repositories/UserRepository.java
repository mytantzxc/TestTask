package com.vaneezy.TestTask.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.vaneezy.TestTask.Entities.ApplicationUser.AppUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    @Query(
            "SELECT a_user From AppUser a_user " +
                    "Where a_user.username =:username"
    )
    AppUser findByUsername(@Param("username") String username);

    @Query(
            "SELECT a_user From AppUser a_user " +
                    "WHERE a_user.username IN (:usernames)"
    )
    Iterable<AppUser> findLoggedUsers(@Param("usernames") List<String> allLoggedUsernames);

    @Query(
            value = "SELECT id, user_password, email, role, username FROM app_user " +
                    "WHERE id NOT IN (" +
                        "SELECT user_id FROM trophy" +
                    ")", nativeQuery = true
    )
    Iterable<AppUser> findUsersWithoutTrophies();
}
