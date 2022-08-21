package com.vaneezy.TestTask.Entities;

import com.vaneezy.TestTask.Entities.ApplicationUser.AppUser;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Trophy {

    public Trophy() {
    }

    public Trophy(AppUser user, LocalDateTime added, Integer count) {
        this.user = user;
        this.added = added;
        this.count = count;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trophy_id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private AppUser user;

    private LocalDateTime added;

    private Integer count;

    public Long getTrophy_id() {
        return trophy_id;
    }

    public void setTrophy_id(Long trophy_id) {
        this.trophy_id = trophy_id;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public LocalDateTime getAdded() {
        return added;
    }

    public void setAdded(LocalDateTime added) {
        this.added = added;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
