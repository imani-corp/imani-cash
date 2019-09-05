package com.imani.cash.domain.user.repository;

import com.imani.cash.domain.user.UserPropertyService;
import com.imani.cash.domain.user.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author manyce400
 */
@Repository
public interface IUserPropertyServiceRepository extends JpaRepository<UserPropertyService, Long> {

    /**
     * Find and return all PropertyService that this user has signed up for.
     */
    @Query("Select userPropertyService From UserPropertyService userPropertyService Where userPropertyService.userRecord = ?1")
    public List<UserPropertyService> findAllUserPropertyService(UserRecord userRecord);


    /**
     * Find and return all PropertyService that this user has signed up for.
     */
    @Query("Select userPropertyService From UserPropertyService userPropertyService Where userPropertyService.userRecord = ?1 and userPropertyService.active = true")
    public List<UserPropertyService> findAllActiveUserPropertyService(UserRecord userRecord);
}
