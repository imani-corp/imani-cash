package com.imani.cash.domain.service.payment;

import com.imani.cash.domain.payment.PaymentStatusE;
import com.imani.cash.domain.payment.RentalPaymentHistory;
import com.imani.cash.domain.payment.repository.IRentalPaymentHistoryRepository;
import com.imani.cash.domain.service.util.DateTimeUtil;
import com.imani.cash.domain.service.util.IDateTimeUtil;
import com.imani.cash.domain.user.UserRecord;
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
@Service(RentalPaymentHistoryService.SPRING_BEAN)
public class RentalPaymentHistoryService implements IRentalPaymentHistoryService {


    @Autowired
    private IRentalPaymentHistoryRepository iRentalPaymentHistoryRepository;

    @Autowired
    @Qualifier(DateTimeUtil.SPRING_BEAN)
    private IDateTimeUtil iDateTimeUtil;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RentalPaymentHistoryService.class);

    public static final String SPRING_BEAN = "com.imani.cash.domain.service.payment.RentalPaymentHistoryService";


    @Transactional
    @Override
    public void save(RentalPaymentHistory rentalPaymentHistory) {
        Assert.notNull(rentalPaymentHistory, "rentalPaymentHistory cannot be empty");
        LOGGER.debug("Saving rentalPaymentHistory: {}", rentalPaymentHistory);
        iRentalPaymentHistoryRepository.save(rentalPaymentHistory);
    }

    @Override
    public Optional<List<RentalPaymentHistory>> findUserRentalPaymentForCurrentMonth(UserRecord userRecord) {
        Assert.notNull(userRecord, "UserRecord cannot be empty");

        // Current Month DateTime should start at the start of the month.  All bills are due 1'st day of month
        DateTime now = DateTime.now();
        DateTime dateTimeAtStartOfMonth = iDateTimeUtil.getDateTimeAtStartOfMonth(now);
        DateTime dateTimeAtEndOfMonth = iDateTimeUtil.getDateTimeAtEndOfMonth(now);

        LOGGER.info("Finding all rental payments made for user:=> {} dateTimeAtStartOfMonth:=> {}  dateTimeAtEndOfMonth:=> {}", userRecord.getEmbeddedContactInfo().getEmail(), dateTimeAtStartOfMonth, dateTimeAtEndOfMonth);

        List<RentalPaymentHistory> rentalPaymentHistoryList = iRentalPaymentHistoryRepository.findUserRentalPaymentHistoryByDateRange(userRecord, dateTimeAtStartOfMonth, dateTimeAtEndOfMonth);

        if(rentalPaymentHistoryList.size() > 0) {
            return Optional.of(rentalPaymentHistoryList);
        }

        return Optional.empty();
    }

    @Override
    public boolean hasPendingUserRentalPaymentForCurrentMonth(UserRecord userRecord) {
        Optional<List<RentalPaymentHistory>> pendingRentalPaymentHistoryList = findPendingUserRentalPaymentForCurrentMonth(userRecord);
        return pendingRentalPaymentHistoryList.isPresent();
    }

    @Override
    public Optional<List<RentalPaymentHistory>> findPendingUserRentalPaymentForCurrentMonth(UserRecord userRecord) {
        Assert.notNull(userRecord, "UserRecord cannot be empty");

        // Current Month DateTime should start at the start of the month.  All bills are due 1'st day of month
        DateTime now = DateTime.now();
        DateTime dateTimeAtStartOfMonth = iDateTimeUtil.getDateTimeAtStartOfMonth(now);
        DateTime dateTimeAtEndOfMonth = iDateTimeUtil.getDateTimeAtEndOfMonth(now);

        LOGGER.info("Finding all pending rental payments made for user:=> {} dateTimeAtStartOfMonth:=> {}  dateTimeAtEndOfMonth:=> {}", userRecord.getEmbeddedContactInfo().getEmail(), dateTimeAtStartOfMonth, dateTimeAtEndOfMonth);

        List<RentalPaymentHistory> rentalPaymentHistoryList = iRentalPaymentHistoryRepository.findUserRentalPaymentHistoryByStatusAndDateRange(userRecord, PaymentStatusE.Pending, dateTimeAtStartOfMonth, dateTimeAtEndOfMonth);

        if(rentalPaymentHistoryList.size() > 0) {
            return Optional.of(rentalPaymentHistoryList);
        }

        return Optional.empty();
    }
}
