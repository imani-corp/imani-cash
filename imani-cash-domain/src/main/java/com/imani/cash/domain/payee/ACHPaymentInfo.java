package com.imani.cash.domain.payee;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author manyce400
 */
@Embeddable
public class ACHPaymentInfo {


    @Column(name="RoutingNumber", nullable=true)
    public Integer routingNumber;

    @Column(name="BankAcctNumber", nullable=true)
    public Integer bankAcctNumber;


    @Column(name="FinancialInstitution", nullable=true, length=100)
    private String financialInstitution;


    public ACHPaymentInfo() {

    }

    public Integer getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(Integer routingNumber) {
        this.routingNumber = routingNumber;
    }

    public Integer getBankAcctNumber() {
        return bankAcctNumber;
    }

    public void setBankAcctNumber(Integer bankAcctNumber) {
        this.bankAcctNumber = bankAcctNumber;
    }

    public String getFinancialInstitution() {
        return financialInstitution;
    }

    public void setFinancialInstitution(String financialInstitution) {
        this.financialInstitution = financialInstitution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ACHPaymentInfo that = (ACHPaymentInfo) o;

        return new EqualsBuilder()
                .append(routingNumber, that.routingNumber)
                .append(bankAcctNumber, that.bankAcctNumber)
                .append(financialInstitution, that.financialInstitution)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(routingNumber)
                .append(bankAcctNumber)
                .append(financialInstitution)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("routingNumber", routingNumber)
                .append("bankAcctNumber", bankAcctNumber)
                .append("financialInstitution", financialInstitution)
                .toString();
    }
}
