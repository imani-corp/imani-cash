package com.imani.cash.domain.user.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.imani.cash.domain.user.UserRecord;
import org.springframework.util.Assert;

/**
 * @author manyce400
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRecordTransaction {



    private Boolean transactionSuccessful;

    private UserRecordTransactionTypeE userRecordTransactionTypeE;

    private UserRecord userRecord;


    public UserRecordTransaction(Boolean transactionSuccessful, UserRecordTransactionTypeE userRecordTransactionTypeE, UserRecord userRecord) {
        Assert.notNull(transactionSuccessful, "TransactionSuccessful cannot be null");
        Assert.notNull(userRecordTransactionTypeE, "UserRecordTransactionTypeE cannot be null");
        Assert.notNull(userRecord, "UserRecord cannot be null");
        this.transactionSuccessful = transactionSuccessful;
        this.userRecordTransactionTypeE = userRecordTransactionTypeE;
        this.userRecord = userRecord;
    }

    public Boolean getTransactionSuccessful() {
        return transactionSuccessful;
    }

    public UserRecordTransactionTypeE getUserRecordTransactionTypeE() {
        return userRecordTransactionTypeE;
    }

    public UserRecord getUserRecord() {
        return userRecord;
    }

    public static UserRecordTransaction instanceOf(Boolean transactionSuccessful, UserRecordTransactionTypeE userRecordTransactionTypeE, UserRecord userRecord) {
        return new UserRecordTransaction(transactionSuccessful, userRecordTransactionTypeE, userRecord);
    }

}