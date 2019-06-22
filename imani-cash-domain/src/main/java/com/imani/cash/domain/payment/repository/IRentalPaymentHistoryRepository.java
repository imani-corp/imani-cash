package com.imani.cash.domain.payment.repository;

import com.imani.cash.domain.payment.PaymentStatusE;
import com.imani.cash.domain.payment.RentalPaymentHistory;
import com.imani.cash.domain.user.UserRecord;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author manyce400
 */
@Repository
public interface IRentalPaymentHistoryRepository extends JpaRepository<RentalPaymentHistory, Long> {

    @Query("Select  rentalPaymentHistory From RentalPaymentHistory rentalPaymentHistory Where rentalPaymentHistory.userRecord =?1 and rentalPaymentHistory.embeddedPayment.paymentDate >=?2")
    public List<RentalPaymentHistory> findUserRentalPaymentHistoryByDate(UserRecord userRecord, DateTime paymentDate);

    @Query("Select  rentalPaymentHistory From RentalPaymentHistory rentalPaymentHistory Where rentalPaymentHistory.userRecord =?1 and rentalPaymentHistory.embeddedPayment.paymentStatusE >=?2 and rentalPaymentHistory.embeddedPayment.paymentDate >=?3")
    public List<RentalPaymentHistory> findUserRentalPaymentHistoryByStatusAndDate(UserRecord userRecord, PaymentStatusE paymentStatusE, DateTime paymentDate);

}
