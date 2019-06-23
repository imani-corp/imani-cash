package com.imani.cash.domain.service.user;

import com.imani.cash.domain.user.UserRecord;
import com.imani.cash.domain.user.repository.IUserRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author manyce400
 */
@Service(UserRecordAuthService.SPRING_BEAN)
public class UserRecordAuthService implements IUserRecordAuthService {




    @Autowired
    private IUserRecordRepository iUserRecordRepository;


    public static final String SPRING_BEAN = "com.imani.cash.domain.service.user.UserRecordAuthService";


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(UserRecordAuthService.class);



    @Override
    public List<UserRecord> findAllUserRecord() {
        LOGGER.info("Finding all UserRecord.....");
        return iUserRecordRepository.findAll();
    }


}
