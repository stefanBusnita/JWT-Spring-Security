package com.jwt.dao.sysadmin;

import com.jwt.model.sysadmin.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by Stefan on 13.04.2017.
 */
public interface UserRepository extends CrudRepository<User,Long>{

    User findByUsername(String username);

}
