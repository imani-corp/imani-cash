package com.imani.cash.domain.user.repository;

import com.imani.cash.domain.user.UserRecord;
import com.imani.cash.domain.user.UserResidence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author manyce400
 */
@Repository
public interface IUserResidenceRepository extends JpaRepository<UserResidence, Long> {

    // Imani user is currently limited to 1 residence at a time only.
    @Query("Select userResidence From UserResidence userResidence Where userResidence.userRecord = ?1")
    public UserResidence findUserResidence(UserRecord userRecord);

}
