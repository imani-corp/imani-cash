package com.imani.cash.domain.payment;

import com.imani.cash.domain.property.PropertyManager;
import com.imani.cash.domain.user.UserRecord;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

/**
 * @author manyce400
 */
@Entity
@Table(name="RentalPaymentHistory")
public class RentalPaymentHistory {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;


    // Tracks the user responsible for making the payment
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UserRecordID", nullable = false)
    private UserRecord userRecord;


    // Tracks the PropertyManager that the payment was made to
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PropertyManagerID", nullable = false)
    private PropertyManager propertyManager;


    // Tracks the Bank Account that the payment was made from.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ACHPaymentInfoID", nullable = true)
    private ACHPaymentInfo paymentBankAccount;


    // Date the payment was made on
    @Column(name = "PaymentDate", nullable = false, updatable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @CreatedDate
    private DateTime paymentDate;


    // Date the payment actually posted against the Property Managers Receivable account.
    @Column(name = "PaymentPostDate", nullable = true, updatable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @CreatedDate
    private DateTime paymentPostDate;


    @Column(name="PropertyTypeE", nullable=false, length=20)
    @Enumerated(EnumType.STRING)
    private PaymentStatusE paymentStatusE;

    public RentalPaymentHistory() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ACHPaymentInfo getPaymentBankAccount() {
        return paymentBankAccount;
    }

    public void setPaymentBankAccount(ACHPaymentInfo paymentBankAccount) {
        this.paymentBankAccount = paymentBankAccount;
    }

    public DateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(DateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public DateTime getPaymentPostDate() {
        return paymentPostDate;
    }

    public void setPaymentPostDate(DateTime paymentPostDate) {
        this.paymentPostDate = paymentPostDate;
    }

    public PaymentStatusE getPaymentStatusE() {
        return paymentStatusE;
    }

    public void setPaymentStatusE(PaymentStatusE paymentStatusE) {
        this.paymentStatusE = paymentStatusE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RentalPaymentHistory that = (RentalPaymentHistory) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(userRecord, that.userRecord)
                .append(propertyManager, that.propertyManager)
                .append(paymentBankAccount, that.paymentBankAccount)
                .append(paymentDate, that.paymentDate)
                .append(paymentPostDate, that.paymentPostDate)
                .append(paymentStatusE, that.paymentStatusE)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(userRecord)
                .append(propertyManager)
                .append(paymentBankAccount)
                .append(paymentDate)
                .append(paymentPostDate)
                .append(paymentStatusE)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("userRecord", userRecord)
                .append("propertyManager", propertyManager)
                .append("paymentBankAccount", paymentBankAccount)
                .append("paymentDate", paymentDate)
                .append("paymentPostDate", paymentPostDate)
                .append("paymentStatusE", paymentStatusE)
                .toString();
    }

}
