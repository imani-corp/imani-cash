package com.imani.cash.domain.service.user;

import com.imani.cash.domain.user.UserLoginStatistic;
import com.imani.cash.domain.user.UserRecord;
import com.imani.cash.domain.user.repository.IUserLoginStatisticRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;

/**
 * @author manyce400
 */
@Service(UserLoginStatisticService.SPRING_BEAN)
public class UserLoginStatisticService implements IUserLoginStatisticService {



    @Autowired
    private IUserLoginStatisticRepository iUserLoginStatisticRepository;


    public static final String SPRING_BEAN = "com.imani.cash.domain.service.user.UserLoginStatisticService";

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(UserLoginStatisticService.class);


    @Override
    public boolean validateUserLoginAttemptStatistic(UserLoginStatistic userLoginStatistic) {
        Assert.notNull(userLoginStatistic, "userLoginStatistic cannot be null");
        LOGGER.debug("Validating userLoginStatistic:=> {}", userLoginStatistic);

        if(StringUtils.hasLength(userLoginStatistic.getiManiClientVersion())
                && StringUtils.hasLength(userLoginStatistic.getDeviceOS())
                && StringUtils.hasLength(userLoginStatistic.getDeviceVersion())
                && userLoginStatistic.getDeviceTypeE() != null) {
            return true;
        }

        return false;
    }

    @Transactional
    @Override
    public UserLoginStatistic recordUserLoginStatistic(UserRecord userRecord, UserLoginStatistic userLoginStatistic) {
        Assert.notNull(userRecord, "UserRecord cannot be null");
        Assert.notNull(userLoginStatistic, "userLoginStatistic cannot be null");

        LOGGER.debug("Recording Login statistics for user:=> {} from userLoginStatistic:=> {}", userRecord.getEmbeddedContactInfo().getEmail(), userLoginStatistic);

        UserLoginStatistic jpaLoginStatistic = UserLoginStatistic.builder()
                .userRecord(userRecord)
                .loginDate(DateTime.now())
                .deviceTypeE(userLoginStatistic.getDeviceTypeE())
                .deviceOS(userLoginStatistic.getDeviceOS())
                .deviceVersion(userLoginStatistic.getDeviceVersion())
                .iManiClientVersion(userLoginStatistic.getiManiClientVersion())
                .build();

        iUserLoginStatisticRepository.save(jpaLoginStatistic);
        return jpaLoginStatistic;
    }

}
