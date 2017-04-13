package com.jwt.services.utils.sysadminImpl;

import com.jwt.dao.sysadmin.UserRepository;
import com.jwt.model.sysadmin.User;
import com.jwt.services.utils.sysadmin.UserService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * Created by Stefan on 13.04.2017.
 */
@Component
@Transactional
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){

    this.userRepository = userRepository;
    }


    @Override
    public void save(User user) {
        this.userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        User returned =  this.userRepository.findByUsername(username);
        Hibernate.initialize(returned.getUserRights());
        return returned;
    }
}
