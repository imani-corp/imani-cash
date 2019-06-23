package com.imani.cash.domain.service.user;

import com.imani.cash.domain.user.UserRecord;

/**
 * @author manyce400
 */
public interface IUserRecordManagementService {

    /**
     * Registers and creates a new UserRecord.
     *
     * @param userRecord
     */
    public void registerUserRecord(UserRecord userRecord);


}
