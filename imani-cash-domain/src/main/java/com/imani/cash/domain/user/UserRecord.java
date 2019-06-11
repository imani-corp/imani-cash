package com.imani.cash.domain.user;

import com.google.common.collect.ImmutableSet;
import com.imani.cash.domain.AuditableRecord;
import com.imani.cash.domain.contact.EmbeddedContactInfo;
import com.imani.cash.domain.payment.ACHPaymentInfo;
import com.imani.cash.domain.property.PropertyInfo;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * UserRecord is the domain model for all users that can access Imani Cash to transact.
 *
 * @author manyce400
 */
@Entity
@Table(name="UserRecord")
public class UserRecord extends AuditableRecord {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;

    @Column(name="FirstName", nullable=false, length = 50)
    private String firstName;

    @Column(name="LastName", nullable=false, length = 50)
    private String lastName;

    @Embedded
    private EmbeddedContactInfo embeddedContactInfo;

    @Column(name="Password", nullable=false, length = 20)
    private String password;


    @Column(name="ResetPassword", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean resetPassword;


    // IF set to true then user is not allowed to access QPalX application
    @Column(name="AccountLocked", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean accountLocked;


    // Tracks the Property Address where this user currently resides
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PropertyInfoID", nullable = true)
    private PropertyInfo addressInfo;


    // Contains tall the payment Bank accounts that this user can issue and make payments out of.
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ACHPaymentInfo> paymentBankAccounts = new HashSet<>();



    public UserRecord() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public EmbeddedContactInfo getEmbeddedContactInfo() {
        return embeddedContactInfo;
    }

    public void setEmbeddedContactInfo(EmbeddedContactInfo embeddedContactInfo) {
        this.embeddedContactInfo = embeddedContactInfo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isResetPassword() {
        return resetPassword;
    }

    public void setResetPassword(boolean resetPassword) {
        this.resetPassword = resetPassword;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public PropertyInfo getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(PropertyInfo addressInfo) {
        this.addressInfo = addressInfo;
    }

    public Set<ACHPaymentInfo> getPaymentBankAccounts() {
        return ImmutableSet.copyOf(paymentBankAccounts);
    }

    public void addToPaymentBankAccounts(ACHPaymentInfo paymentBankAccount) {
        Assert.notNull(paymentBankAccount, "paymentBankAccount cannot be null");
        this.paymentBankAccounts.add(paymentBankAccount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserRecord that = (UserRecord) o;

        return new EqualsBuilder()
                .append(resetPassword, that.resetPassword)
                .append(accountLocked, that.accountLocked)
                .append(id, that.id)
                .append(firstName, that.firstName)
                .append(lastName, that.lastName)
                .append(embeddedContactInfo, that.embeddedContactInfo)
                .append(password, that.password)
                .append(addressInfo, that.addressInfo)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(firstName)
                .append(lastName)
                .append(embeddedContactInfo)
                .append(password)
                .append(resetPassword)
                .append(accountLocked)
                .append(addressInfo)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("embeddedContactInfo", embeddedContactInfo)
                .append("password", password)
                .append("resetPassword", resetPassword)
                .append("accountLocked", accountLocked)
                .append("addressInfo", addressInfo)
                .append("paymentBankAccounts", paymentBankAccounts)
                .toString();
    }
}
