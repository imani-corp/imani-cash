package com.imani.cash.domain.payment.ach;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Tracks Bank account balances using Plaid integration model.
 *
 * @author manyce400
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Balance {


    private Double available;

    private Double current;

    private Double limit;

    @JsonProperty("iso_currency_code")
    private String currencyCode;

    public Balance() {

    }

    public Double getAvailable() {
        return available;
    }

    public void setAvailable(Double available) {
        this.available = available;
    }

    public Double getCurrent() {
        return current;
    }

    public void setCurrent(Double current) {
        this.current = current;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Balance balance = (Balance) o;

        return new EqualsBuilder()
                .append(available, balance.available)
                .append(current, balance.current)
                .append(limit, balance.limit)
                .append(currencyCode, balance.currencyCode)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(available)
                .append(current)
                .append(limit)
                .append(currencyCode)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("available", available)
                .append("current", current)
                .append("limit", limit)
                .append("currencyCode", currencyCode)
                .toString();
    }
}
