package com.imani.cash.domain.service.user;

import com.imani.cash.domain.security.encryption.IOneWayClearTextEncryption;
import com.imani.cash.domain.security.encryption.OneWayClearTextEncryption;
import com.imani.cash.domain.user.UserRecord;
import com.imani.cash.domain.user.repository.IUserRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;

/**
 * @author manyce400
 */
@Service(UserRecordManagementService.SPRING_BEAN)
public class UserRecordManagementService implements IUserRecordManagementService {



    @Autowired
    private IUserRecordRepository iUserRecordRepository;


    @Autowired
    @Qualifier(OneWayClearTextEncryption.SPRING_BEAN)
    private IOneWayClearTextEncryption iOneWayClearTextEncryption;


    public static final String SPRING_BEAN = "com.imani.cash.domain.service.user.UserRecordManagementService";


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(UserRecordManagementService.class);


    @Transactional
    @Override
    public void registerUserRecord(UserRecord userRecord) {
        Assert.notNull(userRecord, "UserRecord cannot be null");
        LOGGER.info("Registering new userRecord:=> {}", userRecord);

        // encode the password passed
        String encoded = iOneWayClearTextEncryption.encryptClearText(userRecord.getPassword());
        userRecord.setPassword(encoded);
        iUserRecordRepository.save(userRecord);
    }

}
