package com.imani.cash.domain.property.rental;

import com.imani.cash.domain.property.PropertyManager;
import com.imani.cash.domain.property.PropertyOwner;
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


    // Tracked to determine the tenant user involved in this transaction
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TenantUserRecordID", nullable = false)
    private UserRecord tenantUserRecord;


    // Tracked to indicate that transaction involved a Property Manager
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PropertyManagerID", nullable = true)
    private PropertyManager propertyManager;


    // Tracked to indicate that transaction involved a Property Owner
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PropertyOwnerID", nullable = true)
    private PropertyOwner propertyOwner;


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

    public String getTransactionMessage() {
        return transactionMessage;
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

    public UserRecord getTenantUserRecord() {
        return tenantUserRecord;
    }

    public void setTenantUserRecord(UserRecord tenantUserRecord) {
        this.tenantUserRecord = tenantUserRecord;
    }

    public PropertyManager getPropertyManager() {
        return propertyManager;
    }

    public void setPropertyManager(PropertyManager propertyManager) {
        this.propertyManager = propertyManager;
    }

    public PropertyOwner getPropertyOwner() {
        return propertyOwner;
    }

    public void setPropertyOwner(PropertyOwner propertyOwner) {
        this.propertyOwner = propertyOwner;
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
                .append(tenantUserRecord, that.tenantUserRecord)
                .append(propertyManager, that.propertyManager)
                .append(propertyOwner, that.propertyOwner)
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
                .append(tenantUserRecord)
                .append(propertyManager)
                .append(propertyOwner)
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
                .append("tenantUserRecord", tenantUserRecord)
                .append("propertyManager", propertyManager)
                .append("propertyOwner", propertyOwner)
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

        public Builder tenantUserRecord(UserRecord tenantUserRecord) {
            rentalTransaction.tenantUserRecord = tenantUserRecord;
            return this;
        }

        public Builder propertyManager(PropertyManager propertyManager) {
            rentalTransaction.propertyManager = propertyManager;
            return this;
        }

        public Builder propertyOwner(PropertyOwner propertyOwner) {
            rentalTransaction.propertyOwner = propertyOwner;
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
