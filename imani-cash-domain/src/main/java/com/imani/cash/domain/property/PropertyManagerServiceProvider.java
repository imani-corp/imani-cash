package com.imani.cash.domain.property;

import com.imani.cash.domain.serviceprovider.ServiceProvider;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

/**
 * Tracks all Service Providers that are tied to a specific Property Manager.
 *
 * @author manyce400
 */
@Entity
@Table(name="PropertyManagerServiceProvider")
public class PropertyManagerServiceProvider {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;


    @Column(name = "ServiceStartDate", nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @CreatedDate
    private DateTime serviceStartDate;


    @Column(name = "ServiceEndDate", nullable = true)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @CreatedDate
    private DateTime serviceEndDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ServiceProviderID", nullable = false)
    private ServiceProvider serviceProvider;


    public PropertyManagerServiceProvider() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getServiceStartDate() {
        return serviceStartDate;
    }

    public void setServiceStartDate(DateTime serviceStartDate) {
        this.serviceStartDate = serviceStartDate;
    }

    public DateTime getServiceEndDate() {
        return serviceEndDate;
    }

    public void setServiceEndDate(DateTime serviceEndDate) {
        this.serviceEndDate = serviceEndDate;
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PropertyManagerServiceProvider that = (PropertyManagerServiceProvider) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(serviceStartDate, that.serviceStartDate)
                .append(serviceEndDate, that.serviceEndDate)
                .append(serviceProvider, that.serviceProvider)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(serviceStartDate)
                .append(serviceEndDate)
                .append(serviceProvider)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("serviceStartDate", serviceStartDate)
                .append("serviceEndDate", serviceEndDate)
                .append("serviceProvider", serviceProvider)
                .toString();
    }

}
