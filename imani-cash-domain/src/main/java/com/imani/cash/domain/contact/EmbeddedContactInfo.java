package com.imani.cash.domain.contact;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author manyce400
 */
@Embeddable
public class EmbeddedContactInfo implements IContactInfo {


    @Column(name="MobilePhone", nullable=true, length=50)
    private Long mobilePhone;

    @Column(name="Phone", nullable=true, length=50)
    private Long phone;

    @Column(name="email", nullable=true, length=100)
    private String email;

    @Column(name="PreferredContactType", nullable=true, length=10)
    @Enumerated(EnumType.STRING)
    private ContactTypeE preferredContactType;


    @Override
    public Long getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(Long mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Override
    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public ContactTypeE getPreferredContactType() {
        return preferredContactType;
    }

    public void setPreferredContactType(ContactTypeE preferredContactType) {
        this.preferredContactType = preferredContactType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        EmbeddedContactInfo that = (EmbeddedContactInfo) o;

        return new EqualsBuilder()
                .append(mobilePhone, that.mobilePhone)
                .append(phone, that.phone)
                .append(email, that.email)
                .append(preferredContactType, that.preferredContactType)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(mobilePhone)
                .append(phone)
                .append(email)
                .append(preferredContactType)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("mobilePhone", mobilePhone)
                .append("phone", phone)
                .append("email", email)
                .append("preferredContactType", preferredContactType)
                .toString();
    }
}
