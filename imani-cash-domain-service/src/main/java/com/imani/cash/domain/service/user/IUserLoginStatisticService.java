package com.imani.cash.domain.service.user;

import com.imani.cash.domain.user.UserLoginStatistic;
import com.imani.cash.domain.user.UserRecord;

import java.util.Optional;

/**
 * @author manyce400
 */
public interface IUserLoginStatisticService {


    public boolean validateUserLoginStatistic(UserLoginStatistic userLoginStatistic);

    public Optional<UserLoginStatistic> recordUserLoginStatistic(UserRecord userRecord, UserLoginStatistic userLoginStatistic);

    public Optional<UserLoginStatistic> recordUserLogoutStatistic(UserRecord userRecord, UserLoginStatistic userLoginStatistic);

    public Optional<UserLoginStatistic> findMatchingUserLoginStatistic(UserRecord jpaUserRecord, UserLoginStatistic userLoginStatistic);


}
