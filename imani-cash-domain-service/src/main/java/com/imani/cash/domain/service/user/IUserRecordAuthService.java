package com.imani.cash.domain.service.user;

import com.imani.cash.domain.user.UserRecord;

import java.util.List;

/**
 * @author manyce400
 */
public interface IUserRecordAuthService {

    public List<UserRecord> findAllUserRecord();

}
