package com.imani.cash.domain.payment;

import com.imani.cash.domain.AuditableRecord;
import com.imani.cash.domain.property.PropertyManager;
import com.imani.cash.domain.serviceprovider.ServiceProvider;
import com.imani.cash.domain.user.UserRecord;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * @author manyce400
 */
@Entity
@Table(name="ACHPaymentInfo")
public class ACHPaymentInfo extends AuditableRecord {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;


    // Bank Routing Numbers should be hashed
    @Column(name="RoutingNumber", nullable=true, length=100)
    public String routingNumber;


    // Bank Acct Numbers should be hashed
    @Column(name="BankAcctNumber", nullable=true, length=100)
    public String bankAcctNumber;


    @Column(name="FinancialInstitution", nullable=true, length=100)
    private String financialInstitution;


    // UserRecord that this Payment information belongs to
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserRecordID", nullable = true)
    private UserRecord userRecord;


    // PropertyManager that this Payment information belongs to
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PropertyManagerID", nullable = true)
    private PropertyManager propertyManager;


    // ServiceProvider that this Payment information belongs to.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PropertyManagerID", nullable = true)
    private ServiceProvider serviceProvider;


    // Identifies this ACH Bank Account as the primary account
    @Column(name="IsPrimary", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isPrimary;



    public ACHPaymentInfo() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoutingNumber() {
        return routingNumber;
    }

    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }

    public String getBankAcctNumber() {
        return bankAcctNumber;
    }

    public void setBankAcctNumber(String bankAcctNumber) {
        this.bankAcctNumber = bankAcctNumber;
    }

    public String getFinancialInstitution() {
        return financialInstitution;
    }

    public void setFinancialInstitution(String financialInstitution) {
        this.financialInstitution = financialInstitution;
    }

    public UserRecord getUserRecord() {
        return userRecord;
    }

    public void setUserRecord(UserRecord userRecord) {
        this.userRecord = userRecord;
    }

    public PropertyManager getPropertyManager() {
        return propertyManager;
    }

    public void setPropertyManager(PropertyManager propertyManager) {
        this.propertyManager = propertyManager;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ACHPaymentInfo that = (ACHPaymentInfo) o;

        return new EqualsBuilder()
                .append(isPrimary, that.isPrimary)
                .append(id, that.id)
                .append(routingNumber, that.routingNumber)
                .append(bankAcctNumber, that.bankAcctNumber)
                .append(financialInstitution, that.financialInstitution)
                .append(userRecord, that.userRecord)
                .append(propertyManager, that.propertyManager)
                .append(serviceProvider, that.serviceProvider)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(routingNumber)
                .append(bankAcctNumber)
                .append(financialInstitution)
                .append(userRecord)
                .append(propertyManager)
                .append(serviceProvider)
                .append(isPrimary)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("routingNumber", routingNumber)
                .append("bankAcctNumber", bankAcctNumber)
                .append("financialInstitution", financialInstitution)
                .append("userRecord", userRecord)
                .append("propertyManager", propertyManager)
                .append("serviceProvider", serviceProvider)
                .append("isPrimary", isPrimary)
                .toString();
    }

}
