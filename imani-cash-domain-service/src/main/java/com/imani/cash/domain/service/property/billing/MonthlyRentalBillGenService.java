package com.imani.cash.domain.service.property.billing;

import com.imani.cash.domain.property.billing.MonthlyRentalBill;
import com.imani.cash.domain.property.billing.repository.IMonthlyRentalBillRepository;
import com.imani.cash.domain.property.rental.RentalAgreement;
import com.imani.cash.domain.property.rental.repository.IRentalAgreementRepository;
import com.imani.cash.domain.service.util.DateTimeUtil;
import com.imani.cash.domain.service.util.IDateTimeUtil;
import com.imani.cash.domain.user.UserPropertyService;
import com.imani.cash.domain.user.UserRecord;
import com.imani.cash.domain.user.repository.IUserPropertyServiceRepository;
import org.apache.commons.math3.stat.descriptive.summary.Sum;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author manyce400
 */
@Service(MonthlyRentalBillGenService.SPRING_BEAN)
public class MonthlyRentalBillGenService implements IMonthlyRentalBillGenService {


    @Autowired
    @Qualifier(DateTimeUtil.SPRING_BEAN)
    private IDateTimeUtil iDateTimeUtil;


    @Autowired
    private IMonthlyRentalBillRepository iMonthlyRentalBillRepository;


    @Autowired
    private IUserPropertyServiceRepository iUserPropertyServiceRepository;


    @Autowired
    private IRentalAgreementRepository iRentalAgreementRepository;


    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MonthlyRentalBillGenService.class);

    public static final String SPRING_BEAN = "com.imani.cash.domain.service.property.billing.MonthlyRentalBillGenService";

    @Transactional
    @Override
    public Optional<MonthlyRentalBill> generateMthlyBill(UserRecord userRecord) {
        Assert.notNull(userRecord, "UserRecord cannot be null");

        // Get active RentalAgreement for this user, we cant bill a user with no rental agreement
        RentalAgreement rentalAgreement = iRentalAgreementRepository.findActiveUserRentalAgreement(userRecord);

        if (rentalAgreement != null && rentalAgreement.isAgreementInEffect()) {
            // Current Month DateTime should start at the start of the month.  All bills are due 1'st day of month
            DateTime dateTimeAtStartOfMonth = iDateTimeUtil.getDateTimeAtStartOfMonth(DateTime.now());

            // First check to see if a bill has already been generated for this user for calculated start of month datetime
            MonthlyRentalBill existingMonthlyRentalBill = iMonthlyRentalBillRepository.getUserMonthlyRentalBill(userRecord, dateTimeAtStartOfMonth);

            if(existingMonthlyRentalBill == null) {
                LOGGER.info("Generating new monthly rental bill for user:=> ", userRecord.getEmbeddedContactInfo().getEmail());

                // Get the users monthly cost of rent per rental agreemnt
                Double mthlyRentalCost = rentalAgreement.getMonthlyRentalCost();

                // Calculate the total cost of all additional Property Services that this user has signed up for.
                Optional<Double> totalMthlyServiceCharge = calculateMthlyCostOfOptionalServices(userRecord);

                Double totalMonthlyCharge = calculateTotalMthlyCharge(mthlyRentalCost, totalMthlyServiceCharge);

                MonthlyRentalBill newMonthlyRentalBill1 = MonthlyRentalBill.builder()
                        .rentalAmountCharge(mthlyRentalCost)
                        .additionalServicesCharge(totalMthlyServiceCharge.isPresent() ? totalMthlyServiceCharge.get() : null)
                        .totalMonthlyCharge(totalMonthlyCharge)
                        .unpaidAmountCharge(0.0)
                        .paidAmount(0.0)
                        .rentalMonth(dateTimeAtStartOfMonth)
                        .userRecord(userRecord)
                        .rentalAgreement(rentalAgreement)
                        .billClosed(false)
                        .build();

                LOGGER.debug("Generated new monthly rental bill: {}", newMonthlyRentalBill1);
                iMonthlyRentalBillRepository.save(newMonthlyRentalBill1);
                return Optional.of(newMonthlyRentalBill1);
            }
        }

        LOGGER.info("Cannot generate a monthly bill for user:=> no rental agreement or an existing monthly bill was found", userRecord.getEmbeddedContactInfo().getEmail());
        return Optional.empty();
    }


    @Override
    public Optional<Double> calculateMthlyCostOfOptionalServices(UserRecord userRecord) {
        Assert.notNull(userRecord, "UserRecord cannot be null");

        // Get all the PropertyServices that this user has currently signed up for which are still active
        List<UserPropertyService> userPropertyServices = iUserPropertyServiceRepository.findAllActiveUserPropertyService(userRecord);

        Sum sum = new Sum();
        if(userPropertyServices != null) {
            userPropertyServices.forEach(userPropertyService -> {
                if (userPropertyService.isActive()) {
                    LOGGER.debug("Calculating total sum of additional property service:=>  @ monthly cost:=> ", userPropertyService.getPropertyService().getServiceName(), userPropertyService.getPropertyService().getServiceMonthlyCost());
                    sum.increment(userPropertyService.getPropertyService().getServiceMonthlyCost());
                }
            });
        }

        double result = sum.getResult();
        return result > 0 ? Optional.of(result) : Optional.empty();
    }

    double calculateTotalMthlyCharge(Double mthlyRentalCost, Optional<Double> totalMthlyServiceCharge) {
        LOGGER.info("Calculating the total monthly bill charge using mthlyRentalCost: {} and totalMthlyServiceCharge", mthlyRentalCost, totalMthlyServiceCharge);

        Sum total = new Sum();
        total.increment(mthlyRentalCost);

        if(totalMthlyServiceCharge.isPresent()) {
            total.increment(totalMthlyServiceCharge.get());
        }

        return total.getResult();
    }
}