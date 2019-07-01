package com.imani.cash.domain.service.user;

import com.imani.cash.domain.user.UserLoginStatistic;
import com.imani.cash.domain.user.UserRecord;

/**
 * @author manyce400
 */
public interface IUserLoginStatisticService {


    public boolean validateUserLoginAttemptStatistic(UserLoginStatistic userLoginStatistic);

    public UserLoginStatistic recordUserLoginStatistic(UserRecord userRecord, UserLoginStatistic userLoginStatistic);


}
