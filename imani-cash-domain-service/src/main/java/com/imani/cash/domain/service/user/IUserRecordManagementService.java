package com.imani.cash.domain.service.user;

import com.imani.cash.domain.user.UserRecord;
import com.imani.cash.domain.user.message.UserRecordTransaction;

/**
 * @author manyce400
 */
public interface IUserRecordManagementService {

    /**
     * Registers and creates a new UserRecord.
     *
     * @param userRecord
     */
    public UserRecordTransaction registerUserRecord(UserRecord userRecord);


}
