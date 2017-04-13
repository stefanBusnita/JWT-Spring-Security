package com.jwt.services.utils.sysadmin;

import com.jwt.model.sysadmin.Rights;
import org.h2.engine.Right;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Created by Stefan on 13.04.2017.
 */
@Service
public interface RightsService {

    public void saveRights(Rights rights);
    public Set<Rights> findAll();

}
