package com.imani.cash.domain.service.user;

import com.imani.cash.domain.user.UserRecord;
import com.imani.cash.domain.user.UserRecordAuthentication;

import java.util.List;

/**
 * @author manyce400
 */
public interface IUserRecordAuthenticationService {


    public UserRecordAuthentication authenticateAndLogInUserRecord(UserRecord userRecord);

    public UserRecordAuthentication authenticateAndLogOutUserRecord(UserRecord userRecord);

    public List<UserRecord> findAllUserRecord();

}
