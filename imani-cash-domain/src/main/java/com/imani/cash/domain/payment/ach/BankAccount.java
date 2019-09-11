package com.imani.cash.domain.payment.ach;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

import java.util.HashSet;
import java.util.Set;

/**
 * @author manyce400
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BankAccount {


    @JsonProperty("iso_currency_code")
    private String accountID;

    private String name;

    @JsonProperty("official_name")
    private String officialName;

    private String type;

    private String subType;

    @JsonProperty("balances")
    private Set<Balance> accountBalances = new HashSet<>();


    public BankAccount() {

    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubType() {
        return subType;
    }

    public void setSubType(String subType) {
        this.subType = subType;
    }

    public Set<Balance> getAccountBalances() {
        return ImmutableSet.copyOf(accountBalances);
    }

    public void addBalance(Balance balance) {
        Assert.notNull(balance, "Balance cannot be null");
        accountBalances.add(balance);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("accountID", accountID)
                .append("name", name)
                .append("officialName", officialName)
                .append("type", type)
                .append("subType", subType)
                .append("accountBalances", accountBalances)
                .toString();
    }
}
