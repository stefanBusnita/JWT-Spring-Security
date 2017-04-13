package com.jwt.services.utils.sysadmin;

import com.jwt.model.sysadmin.UserPrivateData;
import org.springframework.stereotype.Service;

/**
 * Created by Stefan on 13.04.2017.
 */
@Service
public interface UserPrivateDataService {

    public UserPrivateData findById(Long id);
}
