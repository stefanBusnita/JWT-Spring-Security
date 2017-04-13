package com.jwt.services.utils.sysadminImpl;

import com.jwt.dao.sysadmin.RightRepository;
import com.jwt.model.sysadmin.Rights;
import com.jwt.services.utils.sysadmin.RightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Set;

/**
 * Created by Stefan on 13.04.2017.
 */
@Component
@Transactional
public class RightsServiceImpl implements RightsService{

    private RightRepository rightRepository;

    @Autowired
    public RightsServiceImpl(RightRepository rightRepository){
        this.rightRepository = rightRepository;
    }

    @Override
    public void saveRights(Rights rights) {
        rightRepository.save(rights);
    }

    @Override
    public Set<Rights> findAll() {
        return (Set<Rights>) this.rightRepository.findAll();
    }


}
