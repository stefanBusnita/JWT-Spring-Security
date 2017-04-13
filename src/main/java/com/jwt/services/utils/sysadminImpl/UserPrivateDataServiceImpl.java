package com.jwt.services.utils.sysadminImpl;

import com.jwt.dao.sysadmin.UserPrivateDataRepository;
import com.jwt.model.sysadmin.UserPrivateData;
import com.jwt.services.utils.sysadmin.UserPrivateDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

/**
 * Created by Stefan on 13.04.2017.
 */
@Component
@Transactional
public class UserPrivateDataServiceImpl implements UserPrivateDataService{

    private UserPrivateDataRepository userPrivateDataRepository;

    @Autowired
    public UserPrivateDataServiceImpl(UserPrivateDataRepository userPrivateDataRepository){
        this.userPrivateDataRepository = userPrivateDataRepository;
    }

    @Override
    public UserPrivateData findById(Long id) {
        return this.userPrivateDataRepository.findOne(id);
    }
}
