package com.imani.cash.domain.service.payment.ach.plaid;

import com.imani.cash.domain.payment.PlaidProductE;
import com.imani.cash.domain.payment.ach.plaid.PlaidAPIExecMetric;
import com.imani.cash.domain.payment.ach.plaid.repository.IPlaidAPIExecMetricRepository;
import com.imani.cash.domain.service.util.DateTimeUtil;
import com.imani.cash.domain.service.util.IDateTimeUtil;
import com.imani.cash.domain.user.UserRecord;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author manyce400
 */
@Service(PlaidAPIExecMetricFinderService.SPRING_BEAN)
public class PlaidAPIExecMetricFinderService implements IPlaidAPIExecMetricFinderService {


    @Autowired
    private IPlaidAPIExecMetricRepository iPlaidAPIExecMetricRepository;

    @Autowired
    @Qualifier(DateTimeUtil.SPRING_BEAN)
    private IDateTimeUtil iDateTimeUtil;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(PlaidAPIExecMetricFinderService.class);

    public static final String SPRING_BEAN = "com.imani.cash.domain.service.payment.ach.plaid.PlaidAPIExecMetricFinderService";



    @Override
    public List<PlaidAPIExecMetric> findUserPlaidAPIExecMetricCurrentMonth(UserRecord userRecord, PlaidProductE plaidProductE) {
        Assert.notNull(userRecord, "userRecord cannot be null");
        Assert.notNull(plaidProductE, "plaidProductE cannot be null");

        DateTime now = DateTime.now();
        DateTime dateTimeAtStartOfMonth = iDateTimeUtil.getDateTimeAtStartOfMonth(now);
        DateTime dateTimeAtEndOfMonth = iDateTimeUtil.getDateTimeAtEndOfMonth(now);

        LOGGER.info("Finding all Plaid API Metrics for User:=> {} PlaidProduct:=> {} dateTimeAtStartOfMonth:=> {}  dateTimeAtEndOfMonth:=> {}", userRecord.getEmbeddedContactInfo().getEmail(), plaidProductE, dateTimeAtStartOfMonth, dateTimeAtEndOfMonth);
        return iPlaidAPIExecMetricRepository.findUserPlaidAPIExecMetricByProductBetweenDates(userRecord, plaidProductE, dateTimeAtStartOfMonth, dateTimeAtEndOfMonth);
    }
}
