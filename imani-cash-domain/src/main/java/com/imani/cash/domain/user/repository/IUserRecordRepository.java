package com.imani.cash.domain.user.repository;

import com.imani.cash.domain.user.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author manyce400
 */
public interface IUserRecordRepository extends JpaRepository<UserRecord, Long> {


}
