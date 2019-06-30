package com.imani.cash.domain.service.user;

import com.imani.cash.domain.user.UserRecord;
import com.imani.cash.domain.user.gateway.message.UserTransactionGatewayMessage;

/**
 * @author manyce400
 */
public interface IUserRecordManagementService {

    /**
     * Registers and creates a new UserRecord.
     *
     * @param userRecord
     */
    public UserTransactionGatewayMessage registerUserRecord(UserRecord userRecord);


}
