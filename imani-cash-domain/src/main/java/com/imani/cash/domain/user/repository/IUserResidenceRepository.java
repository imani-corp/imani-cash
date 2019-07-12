package com.imani.cash.domain.user.repository;

import com.imani.cash.domain.user.UserRecord;
import com.imani.cash.domain.user.UserResidence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author manyce400
 */
@Repository
public interface IUserResidenceRepository extends JpaRepository<UserResidence, Long> {

    @Query("Select userResidence From UserResidence userResidence Where userResidence.userRecord = ?1")
    public List<UserResidence> findUserResidences(UserRecord userRecord);
}
