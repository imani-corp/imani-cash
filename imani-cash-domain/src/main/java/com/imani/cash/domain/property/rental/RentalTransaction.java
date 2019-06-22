package com.imani.cash.domain.property.rental;

import com.imani.cash.domain.user.UserRecord;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Optional;

/**
 * @author manyce400
 */
@Entity
@Table(name="RentalTransaction")
public class RentalTransaction {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;


    @Column(name="TransactionSuccessful", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean transactionSuccessful;


    @Column(name="TransactionMessage", nullable=true, length = 100)
    private String transactionMessage;

    @Column(name="RentalTransactionType", nullable=false, length=20)
    @Enumerated(EnumType.STRING)
    private RentalTransactionTypeE rentalTransactionTypeE;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ApartmentID", nullable = true)
    private Apartment apartment;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserRecordID", nullable = true)
    private UserRecord tenant;


    @Column(name = "TransactionDate", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @CreatedDate
    private DateTime transactionDate;


    public RentalTransaction() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isTransactionSuccessful() {
        return transactionSuccessful;
    }

    public void setTransactionSuccessful(boolean transactionSuccessful) {
        this.transactionSuccessful = transactionSuccessful;
    }

    public Optional<String> getTransationMessage() {
        return transactionMessage == null ? Optional.empty() : Optional.of(transactionMessage);
    }

    public void setTransactionMessage(String transactionMessage) {
        this.transactionMessage = transactionMessage;
    }

    public RentalTransactionTypeE getRentalTransactionTypeE() {
        return rentalTransactionTypeE;
    }

    public void setRentalTransactionTypeE(RentalTransactionTypeE rentalTransactionTypeE) {
        this.rentalTransactionTypeE = rentalTransactionTypeE;
    }

    public Apartment getApartment() {
        return apartment;
    }

    public void setApartment(Apartment apartment) {
        this.apartment = apartment;
    }

    public UserRecord getTenant() {
        return tenant;
    }

    public void setTenant(UserRecord tenant) {
        this.tenant = tenant;
    }

    public DateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(DateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RentalTransaction that = (RentalTransaction) o;

        return new EqualsBuilder()
                .append(transactionSuccessful, that.transactionSuccessful)
                .append(id, that.id)
                .append(transactionMessage, that.transactionMessage)
                .append(rentalTransactionTypeE, that.rentalTransactionTypeE)
                .append(apartment, that.apartment)
                .append(tenant, that.tenant)
                .append(transactionDate, that.transactionDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(transactionSuccessful)
                .append(transactionMessage)
                .append(rentalTransactionTypeE)
                .append(apartment)
                .append(tenant)
                .append(transactionDate)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("transactionSuccessful", transactionSuccessful)
                .append("transactionMessage", transactionMessage)
                .append("rentalTransactionTypeE", rentalTransactionTypeE)
                .append("apartment", apartment)
                .append("tenant", tenant)
                .append("transactionDate", transactionDate)
                .toString();
    }


    public static final Builder builder() {
        return new Builder();
    }


    public static final class Builder {

        private RentalTransaction rentalTransaction = new RentalTransaction();

        public Builder transactionSuccessful(boolean transactionSuccessful) {
            rentalTransaction.transactionSuccessful = transactionSuccessful;
            return this;
        }

        public Builder transactionMessage(String transactionMessage) {
            rentalTransaction.transactionMessage = transactionMessage;
            return this;
        }

        public Builder rentalTransactionTypeE(RentalTransactionTypeE rentalTransactionTypeE) {
            rentalTransaction.rentalTransactionTypeE = rentalTransactionTypeE;
            return this;
        }

        public Builder apartment(Apartment apartment) {
            rentalTransaction.apartment = apartment;
            return this;
        }

        public Builder userRecord(UserRecord tenant) {
            rentalTransaction.tenant = tenant;
            return this;
        }

        public Builder transactionDate(DateTime transactionDate) {
            rentalTransaction.transactionDate = transactionDate;
            return this;
        }

        public RentalTransaction build() {
            return rentalTransaction;
        }
    }
}
