package com.imani.cash.domain.service.property.billing;

import com.imani.cash.domain.property.billing.MonthlyRentalBill;
import com.imani.cash.domain.property.billing.repository.IMonthlyRentalBillRepository;
import com.imani.cash.domain.user.repository.IUserRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author manyce400
 */
@Service(MonthlyRentalBillPayService.SPRING_BEAN)
public class MonthlyRentalBillPayService implements IMonthlyRentalBillPayService {



    @Autowired
    private IUserRecordRepository iUserRecordRepository;

    @Autowired
    private IMonthlyRentalBillRepository iMonthlyRentalBillRepository;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MonthlyRentalBillPayService.class);

    public static final String SPRING_BEAN = "com.imani.cash.domain.service.property.billing.MonthlyRentalBillPayService";

    @Transactional
    @Override
    public void payMonthylyRental(MonthlyRentalBill rentalBillToProcess) {
//        Assert.notNull(rentalBillToProcess, "RentalBillToProcess cannot be null");
//        Assert.notNull(rentalBillToProcess.getId(), "RentalBillToProcess ID cannot be null");
//        Assert.notNull(rentalBillToProcess.getPaidAmount(), "PaidAmount cannot be null");
//        Assert.notNull(rentalBillToProcess.getUserRecord(), "UserRecord cannot be null");
//
//        // Refresh the MonthlyRentalBill from persistent store to get its latest state
//        Optional<MonthlyRentalBill> jpaMonthlyRentalBill = iMonthlyRentalBillRepository.findById(rentalBillToProcess.getId());
//
//        if (jpaMonthlyRentalBill.isPresent()) {
//            // Refresh all the user details from persistent store
//            UserRecord jpaUserRecord = iUserRecordRepository.findByUserEmail(rentalBillToProcess.getUserRecord().getEmbeddedContactInfo().getEmail());
//
//            // Prevent double payment, make sure that this bill has not been fully paid or is currently closed.
//
//            LOGGER.info("Executing rental payment request for user: {}", jpaUserRecord.getEmbeddedContactInfo().getEmail());
//        }
    }


    boolean canMakePaymentOnBill(MonthlyRentalBill jpaMonthlyRentalBill, MonthlyRentalBill rentalBillToProcess) {
//        if(!jpaMonthlyRentalBill.isBillClosed() && !jpaMonthlyRentalBill.isFullyPaid()) {
//            // Make sure that the amount currently paid wont end up exceeding the total amount to be paid considering what has been paid already
//            double totalPaidPostPayment = calculateTotalAmountPaidPostPayment(jpaMonthlyRentalBill, rentalBillToProcess);
//
//            if(totalPaidPostPayment > jpaMonthlyRentalBill.getTotalMonthlyCharge()) {
//                LOGGER.info("New payment being made will cause the total payment to exceed monthly charge, denying this payment request...");
//                return false;
//            } else {
//                LOGGER.info("New payment being made on bill passes amount validation.");
//                return true;
//            }
//        }
//
//        LOGGER.warn("Received a monthly rental bill which is already closed out: {}", jpaMonthlyRentalBill);
        return false;
    }

    double calculateTotalAmountPaidPostPayment(MonthlyRentalBill jpaMonthlyRentalBill, MonthlyRentalBill rentalBillToProcess) {
//       Sum sum = new Sum();
//       double newAmtBeingPaid = rentalBillToProcess.getPaidAmount();
//       double currentAmtAlreadyPaid = jpaMonthlyRentalBill.getPaidAmount();
//       sum.increment(newAmtBeingPaid);
//       sum.increment(currentAmtAlreadyPaid);
//       LOGGER.info("Current amount due: {} total amount paid post payment completion: {}", jpaMonthlyRentalBill.getTotalMonthlyCharge(), sum.getResult());
//       return sum.getResult();
        return 0;
    }

}
