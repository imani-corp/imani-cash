package com.imani.cash.domain.property.billing;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imani.cash.domain.AuditableRecord;
import com.imani.cash.domain.property.rental.RentalAgreement;
import com.imani.cash.domain.user.UserRecord;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

/**
 * @author manyce400
 */
@Entity
@Table(name="MonthlyRentalBill")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MonthlyRentalBill extends AuditableRecord {



    @JsonProperty("house_number")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;


    @Column(name="RentalAmountCharge", nullable=true)
    private Double rentalAmountCharge;


    @Column(name="AdditionalServicesCharge", nullable=true)
    private Double additionalServicesCharge;


    @Column(name="UnpaidAmountCharge", nullable=true)
    private Double unpaidAmountCharge;


    @Column(name="UnpaidAmountCharge", nullable=true)
    private Double totalMonthlyCharge;


    @Column(name="PaidAmount", nullable=true)
    private Double paidAmount;


    // Tracks the month for which this monthly rental bill is due for
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "RentalMonth", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime rentalMonth;


    // Bill should be closed at the end of the monthly billing cycle.
    // All unpaid/uncollected charges should be moved forward to the next billing cycle.
    @Column(name="BillClosed", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean billClosed;


    // Tracks the User renter
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "UserRecordID", nullable = false)
    private UserRecord userRecord;


    // Tracks the RentalAgreement linked to this monthly payment
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "RentalAgreementID", nullable = false)
    private RentalAgreement rentalAgreement;


    public MonthlyRentalBill() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRentalAmountCharge() {
        return rentalAmountCharge;
    }

    public void setRentalAmountCharge(Double rentalAmountCharge) {
        this.rentalAmountCharge = rentalAmountCharge;
    }

    public Double getAdditionalServicesCharge() {
        return additionalServicesCharge;
    }

    public void setAdditionalServicesCharge(Double additionalServicesCharge) {
        this.additionalServicesCharge = additionalServicesCharge;
    }

    public Double getUnpaidAmountCharge() {
        return unpaidAmountCharge;
    }

    public void setUnpaidAmountCharge(Double unpaidAmountCharge) {
        this.unpaidAmountCharge = unpaidAmountCharge;
    }

    public Double getTotalMonthlyCharge() {
        return totalMonthlyCharge;
    }

    public void setTotalMonthlyCharge(Double totalMonthlyCharge) {
        this.totalMonthlyCharge = totalMonthlyCharge;
    }

    public Double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public DateTime getRentalMonth() {
        return rentalMonth;
    }

    public void setRentalMonth(DateTime rentalMonth) {
        this.rentalMonth = rentalMonth;
    }

    public boolean isBillClosed() {
        return billClosed;
    }

    public void setBillClosed(boolean billClosed) {
        this.billClosed = billClosed;
    }

    public UserRecord getUserRecord() {
        return userRecord;
    }

    public void setUserRecord(UserRecord userRecord) {
        this.userRecord = userRecord;
    }

    public RentalAgreement getRentalAgreement() {
        return rentalAgreement;
    }

    public void setRentalAgreement(RentalAgreement rentalAgreement) {
        this.rentalAgreement = rentalAgreement;
    }

    public boolean isFullyPaid() {
        return totalMonthlyCharge.equals(paidAmount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        MonthlyRentalBill that = (MonthlyRentalBill) o;

        return new EqualsBuilder()
                .append(billClosed, that.billClosed)
                .append(id, that.id)
                .append(rentalAmountCharge, that.rentalAmountCharge)
                .append(additionalServicesCharge, that.additionalServicesCharge)
                .append(unpaidAmountCharge, that.unpaidAmountCharge)
                .append(totalMonthlyCharge, that.totalMonthlyCharge)
                .append(paidAmount, that.paidAmount)
                .append(rentalMonth, that.rentalMonth)
                .append(userRecord, that.userRecord)
                .append(rentalAgreement, that.rentalAgreement)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(rentalAmountCharge)
                .append(additionalServicesCharge)
                .append(unpaidAmountCharge)
                .append(totalMonthlyCharge)
                .append(paidAmount)
                .append(rentalMonth)
                .append(billClosed)
                .append(userRecord)
                .append(rentalAgreement)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("rentalAmountCharge", rentalAmountCharge)
                .append("additionalServicesCharge", additionalServicesCharge)
                .append("unpaidAmountCharge", unpaidAmountCharge)
                .append("totalMonthlyCharge", totalMonthlyCharge)
                .append("paidAmount", paidAmount)
                .append("rentalMonth", rentalMonth)
                .append("billClosed", billClosed)
                .append("userRecord", userRecord)
                .append("rentalAgreement", rentalAgreement)
                .toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private MonthlyRentalBill monthlyRentalBill = new MonthlyRentalBill();


        public Builder rentalAmountCharge(Double rentalAmountCharge) {
            monthlyRentalBill.rentalAmountCharge = rentalAmountCharge;
            return this;
        }

        public Builder additionalServicesCharge(Double additionalServicesCharge) {
            monthlyRentalBill.additionalServicesCharge = additionalServicesCharge;
            return this;
        }

        public Builder unpaidAmountCharge(Double unpaidAmountCharge) {
            monthlyRentalBill.unpaidAmountCharge = unpaidAmountCharge;
            return this;
        }

        public Builder totalMonthlyCharge(Double totalMonthlyCharge) {
            monthlyRentalBill.totalMonthlyCharge = totalMonthlyCharge;
            return this;
        }

        public Builder paidAmount(Double paidAmount) {
            monthlyRentalBill.paidAmount = paidAmount;
            return this;
        }

        public Builder rentalMonth(DateTime rentalMonth) {
            monthlyRentalBill.rentalMonth = rentalMonth;
            return this;
        }

        public Builder billClosed(boolean billClosed) {
            monthlyRentalBill.billClosed = billClosed;
            return this;
        }

        public Builder userRecord(UserRecord userRecord) {
            monthlyRentalBill.userRecord = userRecord;
            return this;
        }

        public Builder rentalAgreement(RentalAgreement rentalAgreement) {
            monthlyRentalBill.rentalAgreement = rentalAgreement;
            return this;
        }

        public MonthlyRentalBill build() {
            return monthlyRentalBill;
        }

    }
}
