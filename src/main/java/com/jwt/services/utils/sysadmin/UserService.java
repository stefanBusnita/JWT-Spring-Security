package com.jwt.services.utils.sysadmin;

import com.jwt.model.sysadmin.User;
import org.springframework.stereotype.Service;

/**
 * Created by Stefan on 13.04.2017.
 */
@Service
public interface UserService {

    public void save(User user);

    public User findByUsername(String username);
}
