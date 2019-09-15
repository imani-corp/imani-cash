package com.imani.cash.domain.service.property.billing;

import com.imani.cash.domain.property.billing.MonthlyRentalBill;
import com.imani.cash.domain.property.billing.MonthlyRentalBillExplained;
import com.imani.cash.domain.property.billing.repository.IMonthlyRentalBillRepository;
import com.imani.cash.domain.service.payment.ach.plaid.IPlaidAccountBalanceService;
import com.imani.cash.domain.service.payment.ach.plaid.PlaidAccountBalanceService;
import com.imani.cash.domain.user.UserRecord;
import com.imani.cash.domain.user.UserResidence;
import com.imani.cash.domain.user.repository.IUserRecordRepository;
import org.apache.commons.math3.stat.descriptive.summary.Sum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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

    @Autowired
    @Qualifier(MonthlyRentalBillDescService.SPRING_BEAN)
    private IMonthlyRentalBillDescService iMonthlyRentalBillDescService;

    @Autowired
    @Qualifier(PlaidAccountBalanceService.SPRING_BEAN)
    private IPlaidAccountBalanceService iPlaidAccountBalanceService;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MonthlyRentalBillPayService.class);

    public static final String SPRING_BEAN = "com.imani.cash.domain.service.property.billing.MonthlyRentalBillPayService";

    @Transactional
    @Override
    public void payMonthylyRental(MonthlyRentalBillExplained monthlyRentalBillExplained) {
        Assert.notNull(monthlyRentalBillExplained, "monthlyRentalBillExplained cannot be null");

        UserResidence userResidence = monthlyRentalBillExplained.getUserResidence();
        UserRecord userRecord = userResidence.getUserRecord();

        // Find the current user's monthly rental bill
        MonthlyRentalBill jpaMonthlyRentalBill = iMonthlyRentalBillRepository.getUserMonthlyRentalBill(userRecord, monthlyRentalBillExplained.getRentalMonth());
        if (jpaMonthlyRentalBill != null) {
            // Refresh all the user details from persistent store
            UserRecord jpaUserRecord = iUserRecordRepository.findByUserEmail(userRecord.getEmbeddedContactInfo().getEmail());
            LOGGER.info("Executing rental payment request for user: {}", jpaUserRecord.getEmbeddedContactInfo().getEmail());

            // Prevent double payment, make sure that this bill has not been fully paid or is currently closed.
            Double computedTotalAmtDue = iMonthlyRentalBillDescService.calculateTotalAmountDue(jpaMonthlyRentalBill, userResidence.getUserResidencePropertyServices());
            boolean canMakePaymentOnBill = canMakePaymentOnBill(jpaMonthlyRentalBill, computedTotalAmtDue, monthlyRentalBillExplained.getAmtBeingPaid());

            if(canMakePaymentOnBill) {
                // Now validate real-time using Plaid that this user has enough funds in their available account balance to make this payment and execute.
                boolean hasAvailableBalanceForPayment = iPlaidAccountBalanceService.availableBalanceCoversPayment(userRecord, monthlyRentalBillExplained.getAmtBeingPaid());
            }
        }
    }


    boolean canMakePaymentOnBill(MonthlyRentalBill jpaMonthlyRentalBill,  Double computedTotalAmtDue, Double amtBeingPaid) {
        if(!jpaMonthlyRentalBill.isBillClosed()) {
            // Make sure that the amount currently paid wont end up exceeding the total amount to be paid considering what has been paid already
            double totalPaidPostPayment = calculateTotalAmountPaidAfterNewPaymentIsComplete(jpaMonthlyRentalBill, amtBeingPaid);

            if(totalPaidPostPayment > computedTotalAmtDue.doubleValue()) {
                LOGGER.warn("New payment being made in amount:=> {} will cause the total payment to exceed monthly charge, denying this payment request", amtBeingPaid);
                return false;
            } else {
                LOGGER.info("New payment being made on bill in amount:=> {} passes amount validation", amtBeingPaid);
                return true;
            }
        }

        LOGGER.warn("Received a monthly rental bill which is already closed out: {}", jpaMonthlyRentalBill);
        return false;
    }

    double calculateTotalAmountPaidAfterNewPaymentIsComplete(MonthlyRentalBill jpaMonthlyRentalBill, Double amtBeingPaid) {
       Sum sum = new Sum();
       double currentAmtAlreadyPaid = jpaMonthlyRentalBill.getAmountPaid();
       sum.increment(amtBeingPaid);
       sum.increment(currentAmtAlreadyPaid);
       return sum.getResult();
    }

}
