package com.imani.cash.domain.payment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.ImmutableSet;
import com.imani.cash.domain.AuditableRecord;
import com.imani.cash.domain.user.UserRecord;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author manyce400
 */
@Entity
@Table(name="ACHPaymentInfo")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ACHPaymentInfo extends AuditableRecord {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;


    // Stripe Bank Acct Token that can be used to generate ACH payments
    @Column(name="StripeBankAcctToken", nullable=false, length=100)
    public String stripeBankAcctToken;


    // Bank AcctID tracked in Plaid post verification and authorization using Link
    @Column(name="PlaidAcctID", nullable=false, length=100)
    public String plaidAcctID;


    // Plaid Public Access token associated to the Item to which account is linked, required for all Plaid product integrations
    @Column(name="PlaidAccessToken", nullable=false, length=300)
    public String plaidAccessToken;


    @Column(name="acctName", nullable=true, length=100)
    public String acctName;


    @Column(name="OfficialAcctName", nullable=true, length=250)
    public String officialAcctName;

    @Column(name="AcctSubType", nullable=true, length=50)
    public String acctSubType;


    @Column(name="AcctType", nullable=true, length=50)
    public String acctType;


    @Column(name="FinancialInstitution", nullable=true, length=100)
    private String financialInstitution;

    // Identifies this ACH Bank Account as the primary account
    @Column(name="IsPrimary", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isPrimary;


    // UserRecord that this Payment information belongs to
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserRecordID", nullable = true)
    private UserRecord userRecord;


    @JsonIgnore
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "achPaymentInfo")
    private Set<ACHPaymentInfoPlaidProduct> achPaymentInfoPlaidProducts = new HashSet<>();



    public ACHPaymentInfo() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStripeBankAcctToken() {
        return stripeBankAcctToken;
    }

    public void setStripeBankAcctToken(String stripeBankAcctToken) {
        this.stripeBankAcctToken = stripeBankAcctToken;
    }

    public String getPlaidAcctID() {
        return plaidAcctID;
    }

    public void setPlaidAcctID(String plaidAcctID) {
        this.plaidAcctID = plaidAcctID;
    }

    public String getPlaidAccessToken() {
        return plaidAccessToken;
    }

    public void setPlaidAccessToken(String plaidAccessToken) {
        this.plaidAccessToken = plaidAccessToken;
    }

    public String getAcctName() {
        return acctName;
    }

    public void setAcctName(String acctName) {
        this.acctName = acctName;
    }

    public String getOfficialAcctName() {
        return officialAcctName;
    }

    public void setOfficialAcctName(String officialAcctName) {
        this.officialAcctName = officialAcctName;
    }

    public String getAcctSubType() {
        return acctSubType;
    }

    public void setAcctSubType(String acctSubType) {
        this.acctSubType = acctSubType;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getFinancialInstitution() {
        return financialInstitution;
    }

    public void setFinancialInstitution(String financialInstitution) {
        this.financialInstitution = financialInstitution;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public UserRecord getUserRecord() {
        return userRecord;
    }

    public void setUserRecord(UserRecord userRecord) {
        this.userRecord = userRecord;
    }

    public Set<ACHPaymentInfoPlaidProduct> getAchPaymentInfoPlaidProducts() {
        return ImmutableSet.copyOf(achPaymentInfoPlaidProducts);
    }

    public void addPlaidProductE(PlaidProductE plaidProductE) {
        Assert.notNull(plaidProductE, "PlaidProductE cannot be null");
        ACHPaymentInfoPlaidProduct achPaymentInfoPlaidProduct = ACHPaymentInfoPlaidProduct.builder()
                .plaidProductE(plaidProductE)
                .achPaymentInfo(this)
                .build();
        achPaymentInfoPlaidProducts.add(achPaymentInfoPlaidProduct);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("stripeBankAcctToken", stripeBankAcctToken)
                .append("plaidAcctID", plaidAcctID)
                .append("plaidAccessToken", plaidAccessToken)
                .append("acctName", acctName)
                .append("officialAcctName", officialAcctName)
                .append("acctSubType", acctSubType)
                .append("acctType", acctType)
                .append("financialInstitution", financialInstitution)
                .append("isPrimary", isPrimary)
                .append("userRecord", userRecord)
                .append("achPaymentInfoPlaidProducts", achPaymentInfoPlaidProducts)
                .toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private ACHPaymentInfo achPaymentInfo = new ACHPaymentInfo();

        public Builder stripeBankAcctToken(String stripeBankAcctToken) {
            achPaymentInfo.stripeBankAcctToken = stripeBankAcctToken;
            return this;
        }

        public Builder plaidAcctID(String plaidAcctID) {
            achPaymentInfo.plaidAcctID = plaidAcctID;
            return this;
        }

        public Builder plaidAccessToken(String plaidAccessToken) {
            achPaymentInfo.plaidAccessToken = plaidAccessToken;
            return this;
        }

        public Builder acctName(String acctName) {
            achPaymentInfo.acctName = acctName;
            return this;
        }

        public Builder officialAcctName(String officialAcctName) {
            achPaymentInfo.officialAcctName = officialAcctName;
            return this;
        }

        public Builder acctSubType(String acctSubType) {
            achPaymentInfo.acctSubType = acctSubType;
            return this;
        }

        public Builder acctType(String acctType) {
            achPaymentInfo.acctType = acctType;
            return this;
        }

        public Builder financialInstitution(String financialInstitution) {
            achPaymentInfo.financialInstitution = financialInstitution;
            return this;
        }

        public Builder isPrimary(boolean isPrimary) {
            achPaymentInfo.isPrimary = isPrimary;
            return this;
        }

        public Builder userRecord(UserRecord userRecord) {
            achPaymentInfo.userRecord = userRecord;
            return this;
        }

        public Builder plaidProductE(PlaidProductE plaidProductE) {
            achPaymentInfo.addPlaidProductE(plaidProductE);
            return this;
        }

        public ACHPaymentInfo build() {
            return achPaymentInfo;
        }
    }

}
