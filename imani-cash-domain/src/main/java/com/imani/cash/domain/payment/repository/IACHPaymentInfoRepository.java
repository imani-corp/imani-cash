package com.imani.cash.domain.payment.repository;

import com.imani.cash.domain.payment.ACHPaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author manyce400
 */
public interface IACHPaymentInfoRepository extends JpaRepository<ACHPaymentInfo, Long> {


}
