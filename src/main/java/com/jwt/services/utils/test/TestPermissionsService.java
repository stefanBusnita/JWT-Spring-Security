package com.jwt.services.utils.test;

import com.jwt.common.json.TestJson;
import org.springframework.stereotype.Service;

/**
 * Created by Stefan on 14.04.2017.
 */
@Service
public interface TestPermissionsService {

     TestJson getUserTestJson();
     TestJson getAdminTestJson();
     TestJson getGuestTestJson();
}
