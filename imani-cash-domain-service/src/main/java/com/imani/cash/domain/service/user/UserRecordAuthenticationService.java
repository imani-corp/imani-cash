package com.imani.cash.domain.service.user;

import com.imani.cash.domain.user.UserRecord;
import com.imani.cash.domain.user.UserRecordAuthentication;
import com.imani.cash.domain.user.gateway.message.UserTransactionGatewayMessage;
import com.imani.cash.domain.user.repository.IUserRecordRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author manyce400
 */
@Service(UserRecordAuthenticationService.SPRING_BEAN)
public class UserRecordAuthenticationService implements IUserRecordAuthenticationService {




    @Autowired
    private IUserRecordRepository iUserRecordRepository;


    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    @Qualifier(UserLoginStatisticService.SPRING_BEAN)
    private IUserLoginStatisticService iUserLoginStatisticService;


    public static final String SPRING_BEAN = "com.imani.cash.domain.service.user.UserRecordAuthenticationService";


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(UserRecordAuthenticationService.class);


    /**
     * Authenticate UserRecord and record a login event.
     *
     * @param userTransactionGatewayMessage
     * @return UserRecordAuthentication
     */
    @Transactional
    @Override
    public UserRecordAuthentication authenticateAndLogInUserRecord(UserTransactionGatewayMessage userTransactionGatewayMessage) {
        Assert.notNull(userTransactionGatewayMessage, "UserTransactionGatewayMessage cannot be null");
        Assert.notNull(userTransactionGatewayMessage.getUserRecord(), "UserRecord cannot be null");
        Assert.notNull(userTransactionGatewayMessage.getUserRecord().getEmbeddedContactInfo(), "EmbeddedContactInfo cannot be null");
        Assert.isTrue(userTransactionGatewayMessage.getUserLoginStatistic().isPresent(), "UserLoginStatistic cannot be empty");

        UserRecord userRecord = userTransactionGatewayMessage.getUserRecord();
        String email = userTransactionGatewayMessage.getUserRecord().getEmbeddedContactInfo().getEmail();
        String password = userTransactionGatewayMessage.getUserRecord().getPassword();

        LOGGER.debug("Attempting to and authenticate userRecord:=>  {}", email);

        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            UserRecord jpaUserRecord = iUserRecordRepository.findByUserEmail(email);
            iUserLoginStatisticService.recordUserLoginStatistic(jpaUserRecord, userTransactionGatewayMessage.getUserLoginStatistic().get());
            return executeUserRecordLogin(userTransactionGatewayMessage.getUserRecord());
        } catch (BadCredentialsException e) {
            LOGGER.info("Invalid credentials supplied for UserRecord:=> {} abandoning login process...", userRecord.getEmbeddedContactInfo().getEmail());
            Integer unsucessfulLoginAttempts = trackUnsuccessfulLoginAttempts(userRecord);
            return getBadCredentialsUserRecordAuthentication(userRecord, unsucessfulLoginAttempts);
        } catch (LockedException e) {
            LOGGER.info("Account is currently locked for UserRecord:=> {} abandoning login process...", userRecord.getEmbeddedContactInfo().getEmail());
            Integer unsucessfulLoginAttempts = trackUnsuccessfulLoginAttempts(userRecord);
            return getLockedUserRecordAuthentication(userRecord, unsucessfulLoginAttempts);
        }
    }


    @Transactional
    @Override
    public UserRecordAuthentication authenticateAndLogOutUserRecord(UserRecord userRecord) {
        Assert.notNull(userRecord, "UserRecord cannot be null");
        Assert.notNull(userRecord.getEmbeddedContactInfo(), "EmbeddedContactInfo cannot be null");

        // Verify user by email and that the user is also currently logged in
        UserRecord jpaUserRecord = iUserRecordRepository.findByUserEmail(userRecord.getEmbeddedContactInfo().getEmail());
        if(jpaUserRecord.isLoggedIn()) {
            LOGGER.info("Logging out UserRecord with email:=> {}", userRecord.getEmbeddedContactInfo().getEmail());
            jpaUserRecord.setLastLogoutDate(DateTime.now());
            jpaUserRecord.setLoggedIn(false);
            iUserRecordRepository.save(jpaUserRecord);
            return getSuccesfulLogOutUserRecordAuthentication(userRecord);
        }

        return null;
    }

    UserRecordAuthentication executeUserRecordLogin(UserRecord userRecord) {
        LOGGER.info("UserRecord has been authenticated successfully completing login steps...");

        // Fetch the actual UserRecord so we can update statistics on number of times this user has tried to login unsuccessfully to lock their account
        UserRecord jpaUserRecord = iUserRecordRepository.findByUserEmail(userRecord.getEmbeddedContactInfo().getEmail());
        jpaUserRecord.setLoggedIn(true);
        jpaUserRecord.setUnsuccessfulLoginAttempts(0);
        jpaUserRecord.setLastLoginDate(DateTime.now());
        iUserRecordRepository.save(jpaUserRecord);
        return getSuccesfulUserRecordAuthentication(userRecord);
    }

    UserRecordAuthentication getSuccesfulUserRecordAuthentication(UserRecord userRecord) {
        UserRecordAuthentication userRecordAuthentication = UserRecordAuthentication.builder()
                .authenticated(Boolean.TRUE)
                .loggedIn(Boolean.TRUE)
                .accountLocked(Boolean.FALSE)
                .userRecord(userRecord)
                .unsuccessfulLoginAttempts(0)
                .build();
        return userRecordAuthentication;
    }

    UserRecordAuthentication getSuccesfulLogOutUserRecordAuthentication(UserRecord userRecord) {
        UserRecordAuthentication userRecordAuthentication = UserRecordAuthentication.builder()
                .authenticated(Boolean.TRUE)
                .loggedIn(Boolean.FALSE)
                .accountLocked(Boolean.FALSE)
                .userRecord(userRecord)
                .unsuccessfulLoginAttempts(0)
                .build();
        return userRecordAuthentication;
    }


    UserRecordAuthentication getBadCredentialsUserRecordAuthentication(UserRecord userRecord, Integer unsucessfulLoginAttempts) {
        UserRecordAuthentication userRecordAuthentication = UserRecordAuthentication.builder()
                .authenticated(Boolean.FALSE)
                .loggedIn(Boolean.FALSE)
                .accountLocked(Boolean.FALSE)
                .userRecord(userRecord)
                .unsuccessfulLoginAttempts(unsucessfulLoginAttempts)
                .build();
        return userRecordAuthentication;
    }

    UserRecordAuthentication getLockedUserRecordAuthentication(UserRecord userRecord, Integer unsucessfulLoginAttempts) {
        UserRecordAuthentication userRecordAuthentication = UserRecordAuthentication.builder()
                .authenticated(Boolean.FALSE)
                .loggedIn(Boolean.FALSE)
                .accountLocked(Boolean.TRUE)
                .userRecord(userRecord)
                .unsuccessfulLoginAttempts(unsucessfulLoginAttempts)
                .build();
        return userRecordAuthentication;
    }

    Integer trackUnsuccessfulLoginAttempts(UserRecord userRecord) {
        // Fetch the actual UserRecord so we can update statistics on number of times this user has tried to login unsuccessfully to lock their account
        UserRecord jpaUserRecord = iUserRecordRepository.findByUserEmail(userRecord.getEmbeddedContactInfo().getEmail());

        int unsucessfulLoginAttempts = jpaUserRecord.getUnsuccessfulLoginAttempts();

        if(unsucessfulLoginAttempts < 3) {
            LOGGER.info("User has attempted to login unsuccessfully less than 3 times, updating metrics....");
            unsucessfulLoginAttempts++;
            jpaUserRecord.setUnsuccessfulLoginAttempts(unsucessfulLoginAttempts);
            iUserRecordRepository.save(jpaUserRecord);
        } else {
            // User has hit the max number of attempts for login, lockout this user
            LOGGER.info("User has hit the maximum number of login attempts, locking user account....");
            jpaUserRecord.setAccountLocked(true);
            iUserRecordRepository.save(jpaUserRecord);
        }

        return unsucessfulLoginAttempts;
    }


    @Override
    public List<UserRecord> findAllUserRecord() {
        LOGGER.info("Finding all UserRecord.....");
        return iUserRecordRepository.findAll();
    }


}
