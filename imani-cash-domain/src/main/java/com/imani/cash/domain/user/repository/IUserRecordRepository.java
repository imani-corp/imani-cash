package com.imani.cash.domain.user.repository;

import com.imani.cash.domain.user.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author manyce400
 */
@Repository
public interface IUserRecordRepository extends JpaRepository<UserRecord, Long> {


}
