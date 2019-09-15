package com.imani.cash.domain.payment.repository;

import com.imani.cash.domain.payment.ACHPaymentInfo;
import com.imani.cash.domain.user.UserRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author manyce400
 */
@Repository
public interface IACHPaymentInfoRepository extends JpaRepository<ACHPaymentInfo, Long> {


    @Query("Select aCHPaymentInfo From ACHPaymentInfo aCHPaymentInfo Where aCHPaymentInfo.userRecord =?1")
    public ACHPaymentInfo findUserACHPaymentInfo(UserRecord userRecord);

}
