package com.imani.cash.domain.service.payment.ach.plaid;

import com.imani.cash.domain.payment.ACHPaymentInfo;
import com.imani.cash.domain.payment.ach.plaid.Balance;
import com.imani.cash.domain.user.UserRecord;

import java.util.Optional;

/**
 * @author manyce400
 */
public interface IPlaidAccountBalanceService {

    public Optional<Balance> getACHPaymentInfoBalances(UserRecord userRecord);

    public Optional<Balance> getACHPaymentInfoBalances(ACHPaymentInfo achPaymentInfo);

    public boolean availableBalanceCoversPayment(UserRecord userRecord, Double paymentAmnt);

    public boolean availableBalanceCoversPayment(ACHPaymentInfo achPaymentInfo, Double paymentAmnt);

}
