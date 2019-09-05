package com.imani.cash.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.ImmutableSet;
import com.imani.cash.domain.AuditableRecord;
import com.imani.cash.domain.contact.EmbeddedContactInfo;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserRecord extends AuditableRecord  {


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


    // For security reasons, this field will not be returned in JSON of this object.
    // @JsonIgnore
    @Column(name="Password", nullable=false, length = 200)
    private String password;


    @Column(name="UserRecordType", nullable=false, length=20)
    @Enumerated(EnumType.STRING)
    private UserRecordTypeE userRecordTypeE;


    // Track the number of unsuccessful login attempts. This should be tracked by security protocols and appropriate actions taken
    // For security reasons, this field will not be returned in JSON of this object.
    @JsonIgnore
    @Column(name="UnsuccessfulLoginAttempts", nullable=true)
    private Integer unsuccessfulLoginAttempts;


    // For security reasons, this field will not be returned in JSON of this object.
    @JsonIgnore
    @Column(name="LoggedIn", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean loggedIn;


    // For security reasons, this field will not be returned in JSON of this object.
    @JsonIgnore
    @Column(name="ResetPassword", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean resetPassword;


    // IF set to true then user is not allowed to access QPalX application
    // For security reasons, this field will not be returned in JSON of this object.
    @JsonIgnore
    @Column(name="AccountLocked", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean accountLocked;


    // Track and update the last time this user was logged in to our system
    // For security reasons, this field will not be returned in JSON of this object.
    @JsonIgnore
    @Column(name = "LastLoginDate", nullable = true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime lastLoginDate;


    // Track and update the last time this user logged out
    // For security reasons, this field will not be returned in JSON of this object.
    @JsonIgnore
    @Column(name = "LastLogoutDate", nullable = true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime lastLogoutDate;


    // Tracks additional Property Services that this user has signed up for.
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userRecord")
    private Set<UserPropertyService> userPropertyServices = new HashSet<>();



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

    public UserRecordTypeE getUserRecordTypeE() {
        return userRecordTypeE;
    }

    public void setUserRecordTypeE(UserRecordTypeE userRecordTypeE) {
        this.userRecordTypeE = userRecordTypeE;
    }

    public Integer getUnsuccessfulLoginAttempts() {
        return unsuccessfulLoginAttempts;
    }

    public void setUnsuccessfulLoginAttempts(Integer unsuccessfulLoginAttempts) {
        this.unsuccessfulLoginAttempts = unsuccessfulLoginAttempts;
    }

    public boolean isResetPassword() {
        return resetPassword;
    }

    public void setResetPassword(boolean resetPassword) {
        this.resetPassword = resetPassword;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public DateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(DateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public DateTime getLastLogoutDate() {
        return lastLogoutDate;
    }

    public void setLastLogoutDate(DateTime lastLogoutDate) {
        this.lastLogoutDate = lastLogoutDate;
    }

    public Set<UserPropertyService> getUserPropertyServices() {
        return ImmutableSet.copyOf(userPropertyServices);
    }

    public void addUserPropertyService(UserPropertyService userPropertyService) {
        Assert.notNull(userPropertyService, "userPropertyService cannot be null");
        userPropertyServices.add(userPropertyService);
    }

    public void setUserPropertyServices(Set<UserPropertyService> userPropertyServices) {
        this.userPropertyServices = userPropertyServices;
    }

    public void updateSafeFieldsWherePresent(UserRecord userRecordToCopy) {
        Assert.notNull(userRecordToCopy, "userRecordToCopy cannot be null");

        if(StringUtils.hasLength(userRecordToCopy.getFirstName())) {
            this.setFirstName(userRecordToCopy.getFirstName());
        }

        if(StringUtils.hasLength(userRecordToCopy.getLastName())) {
            this.setLastName(userRecordToCopy.getLastName());
        }

        if(userRecordToCopy.getEmbeddedContactInfo().getPhone() != null) {
            this.getEmbeddedContactInfo().setPhone(userRecordToCopy.getEmbeddedContactInfo().getPhone());
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserRecord that = (UserRecord) o;

        return new EqualsBuilder()
                .append(loggedIn, that.loggedIn)
                .append(resetPassword, that.resetPassword)
                .append(accountLocked, that.accountLocked)
                .append(id, that.id)
                .append(firstName, that.firstName)
                .append(lastName, that.lastName)
                .append(embeddedContactInfo, that.embeddedContactInfo)
                .append(password, that.password)
                .append(userRecordTypeE, that.userRecordTypeE)
                .append(unsuccessfulLoginAttempts, that.unsuccessfulLoginAttempts)
                .append(lastLoginDate, that.lastLoginDate)
                .append(lastLogoutDate, that.lastLogoutDate)
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
                .append(userRecordTypeE)
                .append(unsuccessfulLoginAttempts)
                .append(loggedIn)
                .append(resetPassword)
                .append(accountLocked)
                .append(lastLoginDate)
                .append(lastLogoutDate)
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
                .append("userRecordTypeE", userRecordTypeE)
                .append("unsuccessfulLoginAttempts", unsuccessfulLoginAttempts)
                .append("loggedIn", loggedIn)
                .append("resetPassword", resetPassword)
                .append("accountLocked", accountLocked)
                .append("lastLoginDate", lastLoginDate)
                .append("lastLogoutDate", lastLogoutDate)
                .toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private UserRecord userRecord = new UserRecord();

        public Builder firstName(String firstName) {
            userRecord.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            userRecord.lastName = lastName;
            return this;
        }

        public Builder embeddedContactInfo(EmbeddedContactInfo embeddedContactInfo) {
            userRecord.embeddedContactInfo = embeddedContactInfo;
            return this;
        }

        public Builder password(String password) {
            userRecord.password = password;
            return this;
        }

        public Builder userRecordTypeE(UserRecordTypeE userRecordTypeE) {
            userRecord.userRecordTypeE = userRecordTypeE;
            return this;
        }

        public Builder unsuccessfulLoginAttempts(Integer unsuccessfulLoginAttempts) {
            userRecord.unsuccessfulLoginAttempts = unsuccessfulLoginAttempts;
            return this;
        }

        public Builder loggedIn(boolean loggedIn) {
            userRecord.loggedIn = loggedIn;
            return this;
        }

        public Builder resetPassword(boolean resetPassword) {
            userRecord.resetPassword = resetPassword;
            return this;
        }

        public Builder accountLocked(boolean accountLocked) {
            userRecord.accountLocked = accountLocked;
            return this;
        }

        public Builder lastLoginDate(DateTime lastLoginDate) {
            userRecord.lastLoginDate = lastLoginDate;
            return this;
        }

        public Builder lastLogoutDate(DateTime lastLogoutDate) {
            userRecord.lastLogoutDate = lastLogoutDate;
            return this;
        }

        public Builder addUserPropertyService(UserPropertyService userPropertyService) {
            userRecord.addUserPropertyService(userPropertyService);
            return this;
        }

        public UserRecord build() {
            return userRecord;
        }
    }

}
