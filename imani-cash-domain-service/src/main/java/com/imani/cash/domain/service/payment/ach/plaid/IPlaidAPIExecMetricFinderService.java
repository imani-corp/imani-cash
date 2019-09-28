package com.imani.cash.domain.service.payment.ach.plaid;

import com.imani.cash.domain.payment.PlaidProductE;
import com.imani.cash.domain.payment.ach.plaid.PlaidAPIExecMetric;
import com.imani.cash.domain.user.UserRecord;

import java.util.List;

/**
 * @author manyce400
 */
public interface IPlaidAPIExecMetricFinderService {


    /**
     * Find a User's Plaid API Metrics by a specific PlaidProductE.
     *
     * @param userRecord
     * @return List<PlaidAPIExecMetric>
     */
    public List<PlaidAPIExecMetric> findUserPlaidAPIExecMetricCurrentMonth(UserRecord userRecord, PlaidProductE plaidProductE);

}
