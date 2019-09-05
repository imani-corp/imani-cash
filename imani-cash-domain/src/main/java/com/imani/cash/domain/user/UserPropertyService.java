package com.imani.cash.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.imani.cash.domain.AuditableRecord;
import com.imani.cash.domain.property.billing.PropertyService;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;

import javax.persistence.*;

/**
 * Domain object to track the Property Services that a user has signed up for.
 *
 * @author manyce400
 */
@Entity
@Table(name="UserPropertyService")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPropertyService extends AuditableRecord {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;


    @Column(name="ServiceActive", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean active;


    // Tracks additional service at the Property that this user has signed up for.  This will impact user billing.
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PropertyServicesID", nullable = false)
    private PropertyService propertyService;


    // Tracks the User signed up for the PropertyService
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserRecord", nullable = false)
    private UserRecord userRecord;


    public UserPropertyService() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public PropertyService getPropertyService() {
        return propertyService;
    }

    public void setPropertyService(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    public UserRecord getUserRecord() {
        return userRecord;
    }

    public void setUserRecord(UserRecord userRecord) {
        this.userRecord = userRecord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        UserPropertyService that = (UserPropertyService) o;

        return new EqualsBuilder()
                .append(active, that.active)
                .append(id, that.id)
                .append(propertyService, that.propertyService)
                .append(userRecord, that.userRecord)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(active)
                .append(propertyService)
                .append(userRecord)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("active", active)
                .append("propertyService", propertyService)
                .append("userRecord", userRecord)
                .toString();
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {

        private UserPropertyService userPropertyService = new UserPropertyService();

        public Builder active(boolean active) {
            userPropertyService.active = active;
            return this;
        }

        public Builder propertyService(PropertyService propertyService) {
            userPropertyService.propertyService = propertyService;
            return this;
        }

        public Builder userRecord(UserRecord userRecord) {
            userPropertyService.userRecord = userRecord;
            return this;
        }

        public UserPropertyService build() {
            return userPropertyService;
        }
    }
}
