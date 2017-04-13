package com.jwt.model.sysadmin;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Stefan on 13.04.2017.
 */
@Entity
@Table(name = "RIGHTS")
public class Rights {

    @Id
    @Column(name = "right_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long rightId;

    @Column(name = "right_name")
    private String rightName;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "user_rights", joinColumns = {@JoinColumn(name = "right_id")}, inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> users;

    public Long getRightId() {
        return rightId;
    }

    public void setRightId(Long rightId) {
        this.rightId = rightId;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Rights{" +
                "rightId=" + rightId +
                ", rightName='" + rightName + '\'' +
                '}';
    }
}