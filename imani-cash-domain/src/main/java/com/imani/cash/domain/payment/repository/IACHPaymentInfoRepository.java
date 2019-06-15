package com.imani.cash.domain.payment.repository;

import com.imani.cash.domain.payment.ACHPaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author manyce400
 */
@Repository
public interface IACHPaymentInfoRepository extends JpaRepository<ACHPaymentInfo, Long> {


}
