package com.imani.cash.domain.property.rental;

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
@Table(name="RentalAgreement")
public class RentalAgreement {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;


    @Column(name="AgreementDocument", nullable=false, length = 100)
    private String agreementDocument;


    // Tracks if tenant accepted the rental agreement
    @Column(name="TenantAcceptedAgreement", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean tenantAcceptedAgreement;


    @Column(name = "TenantAcceptanceDate", nullable = true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @CreatedDate
    private DateTime tenantAcceptanceDate;


    // Tracks if property manager has accepted agreement
    @Column(name="PropertyManagerAcceptedAgreement", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean propertyManagerAcceptedAgreement;


    @Column(name = "PropertyManagerAcceptanceDate", nullable = true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @CreatedDate
    private DateTime propertyManagerAcceptanceDate;


    @Column(name = "EffectiveDate", nullable = false, updatable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @CreatedDate
    private DateTime effectiveDate;


    @Column(name = "TerminationDate", nullable = false, updatable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @CreatedDate
    private DateTime terminationDate;


    // Tracks the User renter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RentedByUserID", nullable = false)
    private UserRecord rentedByUser;


    // Tracks the PropertyManager that established the agreement.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PropertyManagerID", nullable = true)
    private PropertyManager propertyManager;


    public RentalAgreement() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAgreementDocument() {
        return agreementDocument;
    }

    public void setAgreementDocument(String agreementDocument) {
        this.agreementDocument = agreementDocument;
    }

    public boolean isTenantAcceptedAgreement() {
        return tenantAcceptedAgreement;
    }

    public void setTenantAcceptedAgreement(boolean tenantAcceptedAgreement) {
        this.tenantAcceptedAgreement = tenantAcceptedAgreement;
    }

    public DateTime getTenantAcceptanceDate() {
        return tenantAcceptanceDate;
    }

    public void setTenantAcceptanceDate(DateTime tenantAcceptanceDate) {
        this.tenantAcceptanceDate = tenantAcceptanceDate;
    }

    public boolean isPropertyManagerAcceptedAgreement() {
        return propertyManagerAcceptedAgreement;
    }

    public void setPropertyManagerAcceptedAgreement(boolean propertyManagerAcceptedAgreement) {
        this.propertyManagerAcceptedAgreement = propertyManagerAcceptedAgreement;
    }

    public DateTime getPropertyManagerAcceptanceDate() {
        return propertyManagerAcceptanceDate;
    }

    public void setPropertyManagerAcceptanceDate(DateTime propertyManagerAcceptanceDate) {
        this.propertyManagerAcceptanceDate = propertyManagerAcceptanceDate;
    }

    public DateTime getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(DateTime effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public DateTime getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(DateTime terminationDate) {
        this.terminationDate = terminationDate;
    }

    public UserRecord getRentedByUser() {
        return rentedByUser;
    }

    public void setRentedByUser(UserRecord rentedByUser) {
        this.rentedByUser = rentedByUser;
    }

    public PropertyManager getPropertyManager() {
        return propertyManager;
    }

    public void setPropertyManager(PropertyManager propertyManager) {
        this.propertyManager = propertyManager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RentalAgreement that = (RentalAgreement) o;

        return new EqualsBuilder()
                .append(tenantAcceptedAgreement, that.tenantAcceptedAgreement)
                .append(propertyManagerAcceptedAgreement, that.propertyManagerAcceptedAgreement)
                .append(id, that.id)
                .append(agreementDocument, that.agreementDocument)
                .append(tenantAcceptanceDate, that.tenantAcceptanceDate)
                .append(propertyManagerAcceptanceDate, that.propertyManagerAcceptanceDate)
                .append(effectiveDate, that.effectiveDate)
                .append(terminationDate, that.terminationDate)
                .append(rentedByUser, that.rentedByUser)
                .append(propertyManager, that.propertyManager)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(agreementDocument)
                .append(tenantAcceptedAgreement)
                .append(tenantAcceptanceDate)
                .append(propertyManagerAcceptedAgreement)
                .append(propertyManagerAcceptanceDate)
                .append(effectiveDate)
                .append(terminationDate)
                .append(rentedByUser)
                .append(propertyManager)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("agreementDocument", agreementDocument)
                .append("tenantAcceptedAgreement", tenantAcceptedAgreement)
                .append("tenantAcceptanceDate", tenantAcceptanceDate)
                .append("propertyManagerAcceptedAgreement", propertyManagerAcceptedAgreement)
                .append("propertyManagerAcceptanceDate", propertyManagerAcceptanceDate)
                .append("effectiveDate", effectiveDate)
                .append("terminationDate", terminationDate)
                .append("rentedByUser", rentedByUser)
                .append("propertyManager", propertyManager)
                .toString();
    }


    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {

        private RentalAgreement rentalAgreement = new RentalAgreement();

        public Builder agreementDocument(String agreementDocument) {
            rentalAgreement.agreementDocument = agreementDocument;
            return this;
        }

        public Builder tenantAcceptedAgreement(boolean tenantAcceptedAgreement) {
            rentalAgreement.tenantAcceptedAgreement = tenantAcceptedAgreement;
            return this;
        }

        public Builder tenantAcceptanceDate(DateTime tenantAcceptanceDate) {
            rentalAgreement.tenantAcceptanceDate = tenantAcceptanceDate;
            return this;
        }

        public Builder propertyManagerAcceptedAgreement(boolean propertyManagerAcceptedAgreement) {
            rentalAgreement.propertyManagerAcceptedAgreement = propertyManagerAcceptedAgreement;
            return this;
        }

        public Builder propertyManagerAcceptanceDate(DateTime propertyManagerAcceptanceDate) {
            rentalAgreement.propertyManagerAcceptanceDate = propertyManagerAcceptanceDate;
            return this;
        }

        public Builder effectiveDate(DateTime effectiveDate) {
            rentalAgreement.effectiveDate = effectiveDate;
            return this;
        }

        public Builder terminationDate(DateTime terminationDate) {
            rentalAgreement.terminationDate = terminationDate;
            return this;
        }

        public Builder rentedByUser(UserRecord rentedByUser) {
            rentalAgreement.rentedByUser = rentedByUser;
            return this;
        }

        public Builder propertyManager(PropertyManager propertyManager) {
            rentalAgreement.propertyManager = propertyManager;
            return this;
        }

        public RentalAgreement build() {
            return rentalAgreement;
        }

    }

}
