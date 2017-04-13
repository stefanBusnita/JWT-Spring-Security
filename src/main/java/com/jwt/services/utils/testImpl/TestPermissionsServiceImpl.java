package com.jwt.services.utils.testImpl;

import com.jwt.common.json.TestJson;
import com.jwt.services.utils.test.TestPermissionsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

/**
 * Created by Stefan on 14.04.2017.
 */
@Component
public class TestPermissionsServiceImpl implements TestPermissionsService {

    @Override
    @PreAuthorize("hasRole('USER')")
    public TestJson getUserTestJson() {
        TestJson t = new TestJson();
        t.setResponse("User data returned");
        return t;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public TestJson getAdminTestJson() {
        TestJson t = new TestJson();
        t.setResponse("Admin data returned");
        return t;
    }

    @Override
    @PreAuthorize("hasRole('GUEST')")
    public TestJson getGuestTestJson() {
        TestJson t = new TestJson();
        t.setResponse("Guest data returned");
        return t;
    }
}
