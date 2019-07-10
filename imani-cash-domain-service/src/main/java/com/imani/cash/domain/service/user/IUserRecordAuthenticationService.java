package com.imani.cash.domain.service.user;

import com.imani.cash.domain.user.UserRecord;
import com.imani.cash.domain.user.UserRecordAuthentication;
import com.imani.cash.domain.user.gateway.message.UserTransactionGatewayMessage;

import java.util.List;

/**
 * @author manyce400
 */
public interface IUserRecordAuthenticationService {


    public UserRecordAuthentication authenticateAndLogInUserRecord(UserTransactionGatewayMessage userTransactionGatewayMessage);

    public UserRecordAuthentication authenticateAndLogOutUserRecord(UserTransactionGatewayMessage userTransactionGatewayMessage);

    public List<UserRecord> findAllUserRecord();

}
