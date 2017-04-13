package com.jwt.model.sysadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Set;

/**
 * Created by Stefan on 13.04.2017.
 */
@Entity
@Table(name = "USERS")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long userId;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_private_data_id")
    private UserPrivateData userPrivateData;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Rights> userRights;


    public UserPrivateData getUserPrivateData() {
        return userPrivateData;
    }

    public void setUserPrivateData(UserPrivateData userPrivateData) {
        this.userPrivateData = userPrivateData;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Rights> getUserRights() {
        return userRights;
    }

    public void setUserRights(Set<Rights> userRights) {
        this.userRights = userRights;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userPrivateData=" + userPrivateData +
                ", userRights=" + userRights.toString() +
                '}';
    }
}
