package com.imani.cash.domain.user;

import com.imani.cash.domain.AuditableRecord;
import com.imani.cash.domain.contact.EmbeddedContactInfo;
import com.imani.cash.domain.payee.PayeeRecord;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.*;

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

    // User's with PayeeRecord are also enabled to receive payments through Imani
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PayeeRecordID", nullable = true)
    private PayeeRecord payeeRecord;

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

    public PayeeRecord getPayeeRecord() {
        return payeeRecord;
    }

    public void setPayeeRecord(PayeeRecord payeeRecord) {
        this.payeeRecord = payeeRecord;
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
                .append(payeeRecord, that.payeeRecord)
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
                .append(payeeRecord)
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
                .append("payeeRecord", payeeRecord)
                .toString();
    }

}
