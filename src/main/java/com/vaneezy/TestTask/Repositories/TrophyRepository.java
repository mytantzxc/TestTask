package com.vaneezy.TestTask.Repositories;

import com.vaneezy.TestTask.Entities.ApplicationUser.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import com.vaneezy.TestTask.Entities.Trophy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface TrophyRepository extends JpaRepository<Trophy, Long> {
    @Query(
            value = "SELECT SUM(count) FROM trophy t " +
                    "JOIN app_user app_u ON app_u.id = t.user_id " +
                    "WHERE app_u.username = ?1", nativeQuery = true
    )
    Integer userTrophyCountSum(String username);

    @Query(
            value = "SELECT trophy_id, added, count, user_id FROM Trophy t " +
                    "JOIN app_user app_u ON app_u.id = t.user_id " +
                    "WHERE app_u.username = ?1", nativeQuery = true
    )
    Iterable<Trophy> getTrophiesByUsername(String username);
}
