package com.imani.cash.domain.service.payment.ach.plaid;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.imani.cash.domain.payment.PaymentAPIExecResultE;
import com.imani.cash.domain.payment.PlaidProductE;
import com.imani.cash.domain.payment.ach.plaid.PlaidAPIExecMetric;
import com.imani.cash.domain.payment.ach.plaid.repository.IPlaidAPIExecMetricRepository;
import com.imani.cash.domain.service.concurrent.AppConcurrencyConfigurator;
import com.imani.cash.domain.service.util.DateTimeUtil;
import com.imani.cash.domain.service.util.IDateTimeUtil;
import com.imani.cash.domain.user.UserRecord;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author manyce400
 */
@Service(PlaidAPIExecMetricBuilderService.SPRING_BEAN)
public class PlaidAPIExecMetricBuilderService implements IPlaidAPIExecMetricBuilderService {


    @Autowired
    @Qualifier(AppConcurrencyConfigurator.SERVICE_THREAD_POOL)
    private ListeningExecutorService listeningExecutorService;

    @Autowired
    private IPlaidAPIExecMetricRepository iPlaidAPIExecMetricRepository;

    @Autowired
    @Qualifier(DateTimeUtil.SPRING_BEAN)
    private IDateTimeUtil iDateTimeUtil;

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(PlaidAPIExecMetricBuilderService.class);

    public static final String SPRING_BEAN = "com.imani.cash.domain.service.payment.ach.plaid.PlaidAPIExecMetricBuilderService";



    @Override
    public void buildBalancePlaidAPIExecMetricOnSuccess(final UserRecord userRecord, final DateTime apiInvocationStartDate, final DateTime apiInvocationEndDate) {
        Runnable runnable = () ->  {
            Assert.notNull(userRecord, "UserRecord cannot be null");
            Assert.notNull(apiInvocationStartDate, "apiInvocationStartDate cannot be null");
            Assert.notNull(apiInvocationEndDate, "apiInvocationEndDate cannot be null");

            LOGGER.debug("Building PlaidAPIExecMetric on successful balance call for User:=> {}", userRecord.getEmbeddedContactInfo().getEmail());

            PlaidAPIExecMetric plaidAPIExecMetric = PlaidAPIExecMetric.builder()
                    .userRecord(userRecord)
                    .plaidProductE(PlaidProductE.balance)
                    .apiInvocationStartDate(apiInvocationStartDate)
                    .apiInvocationEndDate(apiInvocationEndDate)
                    .paymentAPIExecResultE(PaymentAPIExecResultE.Success)
                    .build();
            iPlaidAPIExecMetricRepository.save(plaidAPIExecMetric);
        };

        listeningExecutorService.submit(runnable);
    }

    @Override
    public void buildBalancePlaidAPIExecMetricOnFailure(UserRecord userRecord, String apiExecError, DateTime apiInvocationStartDate, DateTime apiInvocationEndDate) {
        // Execute entire recording of metrics on a different thread.
        Runnable runnable = () -> {
            Assert.notNull(userRecord, "UserRecord cannot be null");
            Assert.notNull(apiInvocationStartDate, "apiInvocationStartDate cannot be null");
            Assert.notNull(apiInvocationEndDate, "apiInvocationEndDate cannot be null");

            LOGGER.debug("Building PlaidAPIExecMetric on failed balance call for User:=> {}", userRecord.getEmbeddedContactInfo().getEmail());

            PlaidAPIExecMetric plaidAPIExecMetric = PlaidAPIExecMetric.builder()
                    .userRecord(userRecord)
                    .plaidProductE(PlaidProductE.balance)
                    .apiInvocationStartDate(apiInvocationStartDate)
                    .apiInvocationEndDate(apiInvocationEndDate)
                    .paymentAPIExecResultE(PaymentAPIExecResultE.Failed)
                    .apiExecError(apiExecError)
                    .build();
            iPlaidAPIExecMetricRepository.save(plaidAPIExecMetric);
        };

        listeningExecutorService.submit(runnable);
    }
}
