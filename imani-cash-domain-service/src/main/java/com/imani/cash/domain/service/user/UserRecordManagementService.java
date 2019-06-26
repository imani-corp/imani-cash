package com.imani.cash.domain.service.user;

import com.imani.cash.domain.security.encryption.IOneWayClearTextEncryption;
import com.imani.cash.domain.security.encryption.OneWayClearTextEncryption;
import com.imani.cash.domain.user.UserRecord;
import com.imani.cash.domain.user.message.UserRecordTransaction;
import com.imani.cash.domain.user.message.UserRecordTransactionTypeE;
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
    public UserRecordTransaction registerUserRecord(UserRecord userRecord) {
        Assert.notNull(userRecord, "UserRecord cannot be null");
        Assert.notNull(userRecord.getEmbeddedContactInfo(), "EmbeddedContactInfo cannot be null");

        // Execute validations, make sure no existing user with the same email.
        UserRecord jpaUserRecord = iUserRecordRepository.findByUserEmail(userRecord.getEmbeddedContactInfo().getEmail());

        if (jpaUserRecord == null) {
            LOGGER.info("Registering new UserRecord for user with email:=> {}", userRecord.getEmbeddedContactInfo().getEmail());
            String encoded = iOneWayClearTextEncryption.encryptClearText(userRecord.getPassword());
            userRecord.setPassword(encoded);
            iUserRecordRepository.save(userRecord);
            return UserRecordTransaction.instanceOf(Boolean.TRUE, UserRecordTransactionTypeE.RegisterNewUser, userRecord);
        }

        LOGGER.info("Existing user found.  Cannot register new user with email:=> {}", userRecord.getEmbeddedContactInfo().getEmail());
        return UserRecordTransaction.instanceOf(Boolean.FALSE, UserRecordTransactionTypeE.RegisterNewUser, userRecord);
    }

}
