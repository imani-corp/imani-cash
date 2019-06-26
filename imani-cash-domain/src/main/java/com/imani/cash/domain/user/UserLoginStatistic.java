package com.imani.cash.domain.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.imani.cash.domain.AuditableRecord;
import com.imani.cash.domain.device.DeviceTypeE;
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
@Table(name="UserLoginStatistic")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLoginStatistic extends AuditableRecord {




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;


    // Unique tracker and username
    @Column(name="Email", nullable=true, length=100)
    private String email;


    @Column(name="DeviceType", nullable=true, length=20)
    @Enumerated(EnumType.STRING)
    private DeviceTypeE deviceTypeE;


    @Column(name="DeviceVersion", nullable=true, length=100)
    private String deviceVersion;


    @Column(name="DeviceOS", nullable=true, length=100)
    private String deviceOS;


    @Column(name = "LastLoginDate", nullable = true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime loginDate;


    @Column(name = "LastLogoutDate", nullable = true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime logoutDate;


    public UserLoginStatistic() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public DeviceTypeE getDeviceTypeE() {
        return deviceTypeE;
    }

    public void setDeviceTypeE(DeviceTypeE deviceTypeE) {
        this.deviceTypeE = deviceTypeE;
    }

    public String getDeviceVersion() {
        return deviceVersion;
    }

    public void setDeviceVersion(String deviceVersion) {
        this.deviceVersion = deviceVersion;
    }

    public String getDeviceOS() {
        return deviceOS;
    }

    public void setDeviceOS(String deviceOS) {
        this.deviceOS = deviceOS;
    }

    public DateTime getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(DateTime loginDate) {
        this.loginDate = loginDate;
    }

    public DateTime getLogoutDate() {
        return logoutDate;
    }

    public void setLogoutDate(DateTime logoutDate) {
        this.logoutDate = logoutDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserLoginStatistic that = (UserLoginStatistic) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(email, that.email)
                .append(deviceTypeE, that.deviceTypeE)
                .append(deviceVersion, that.deviceVersion)
                .append(deviceOS, that.deviceOS)
                .append(loginDate, that.loginDate)
                .append(logoutDate, that.logoutDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(email)
                .append(deviceTypeE)
                .append(deviceVersion)
                .append(deviceOS)
                .append(loginDate)
                .append(logoutDate)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("email", email)
                .append("deviceTypeE", deviceTypeE)
                .append("deviceVersion", deviceVersion)
                .append("deviceOS", deviceOS)
                .append("loginDate", loginDate)
                .append("logoutDate", logoutDate)
                .toString();
    }


    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private UserLoginStatistic userLoginStatistic = new UserLoginStatistic();

        public Builder email(String email) {
            userLoginStatistic.email = email;
            return this;
        }

        public Builder deviceTypeE(DeviceTypeE deviceTypeE) {
            userLoginStatistic.deviceTypeE = deviceTypeE;
            return this;
        }

        public Builder deviceVersion(String deviceVersion) {
            userLoginStatistic.deviceVersion = deviceVersion;
            return this;
        }

        public Builder deviceOS(String deviceOS) {
            userLoginStatistic.deviceOS = deviceOS;
            return this;
        }

        public Builder loginDate(DateTime loginDate) {
            userLoginStatistic.loginDate = loginDate;
            return this;
        }

        public Builder logoutDate(DateTime logoutDate) {
            userLoginStatistic.logoutDate = logoutDate;
            return this;
        }

        public UserLoginStatistic build() {
            return userLoginStatistic;
        }

    }


}
