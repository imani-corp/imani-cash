package com.imani.cash.domain.user.repository;

import com.imani.cash.domain.device.DeviceTypeE;
import com.imani.cash.domain.user.UserLoginStatistic;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author manyce400
 */
@Repository
public interface IUserLoginStatisticRepository extends JpaRepository<UserLoginStatistic, Long> {


    @Query("Select userLoginStatistic From UserLoginStatistic userLoginStatistic Where userLoginStatistic.email = ?1")
    public List<UserLoginStatistic> findAllUserLoginStatistic(String email);


    @Query("Select userLoginStatistic From UserLoginStatistic userLoginStatistic Where userLoginStatistic.email = ?1 and userLoginStatistic.deviceTypeE = ?2")
    public List<UserLoginStatistic> findAllUserLoginStatisticByDevice(String email, DeviceTypeE deviceTypeE);


    @Query("Select userLoginStatistic From UserLoginStatistic userLoginStatistic Where userLoginStatistic.email = ?1 and userLoginStatistic.deviceTypeE = ?2 and userLoginStatistic.loginDate = ?3")
    public List<UserLoginStatistic> findAllUserLoginStatisticByDeviceAndLoginDate(String email, DeviceTypeE deviceTypeE, DateTime loginDate);


}