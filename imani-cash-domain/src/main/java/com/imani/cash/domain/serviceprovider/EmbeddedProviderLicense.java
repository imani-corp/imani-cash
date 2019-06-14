package com.imani.cash.domain.serviceprovider;

import com.imani.cash.domain.geographical.City;
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
@Embeddable
public class EmbeddedProviderLicense {




    @Column(name="email", nullable=true, length=100)
    private String licenceNumber;


    @Column(name="PreferredContactType", nullable=true, length=20)
    @Enumerated(EnumType.STRING)
    private ServiceProviderTypeE serviceProviderTypeE;


    @Column(name="LicenseStatus", nullable=true, length=20)
    @Enumerated(EnumType.STRING)
    private LicenseStatusE licenseStatusE;


    @Column(name="LicenseType", nullable=true, length=20)
    @Enumerated(EnumType.STRING)
    private LicenseTypeE licenseTypeE;


    // Captures the City responsible for issueing this license
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IssueCityID", nullable = true)
    private City issueCity;


    @Column(name = "LicenseIssueDate", nullable = false, updatable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @CreatedDate
    private DateTime licenseIssueDate;


    @Column(name = "LicenseEndDate", nullable = false, updatable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @CreatedDate
    private DateTime licenseEndDate;


    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public ServiceProviderTypeE getServiceProviderTypeE() {
        return serviceProviderTypeE;
    }

    public void setServiceProviderTypeE(ServiceProviderTypeE serviceProviderTypeE) {
        this.serviceProviderTypeE = serviceProviderTypeE;
    }

    public LicenseStatusE getLicenseStatusE() {
        return licenseStatusE;
    }

    public void setLicenseStatusE(LicenseStatusE licenseStatusE) {
        this.licenseStatusE = licenseStatusE;
    }

    public LicenseTypeE getLicenseTypeE() {
        return licenseTypeE;
    }

    public void setLicenseTypeE(LicenseTypeE licenseTypeE) {
        this.licenseTypeE = licenseTypeE;
    }

    public City getIssueCity() {
        return issueCity;
    }

    public void setIssueCity(City issueCity) {
        this.issueCity = issueCity;
    }

    public DateTime getLicenseIssueDate() {
        return licenseIssueDate;
    }

    public void setLicenseIssueDate(DateTime licenseIssueDate) {
        this.licenseIssueDate = licenseIssueDate;
    }

    public DateTime getLicenseEndDate() {
        return licenseEndDate;
    }

    public void setLicenseEndDate(DateTime licenseEndDate) {
        this.licenseEndDate = licenseEndDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        EmbeddedProviderLicense that = (EmbeddedProviderLicense) o;

        return new EqualsBuilder()
                .append(licenceNumber, that.licenceNumber)
                .append(serviceProviderTypeE, that.serviceProviderTypeE)
                .append(licenseStatusE, that.licenseStatusE)
                .append(licenseTypeE, that.licenseTypeE)
                .append(issueCity, that.issueCity)
                .append(licenseIssueDate, that.licenseIssueDate)
                .append(licenseEndDate, that.licenseEndDate)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(licenceNumber)
                .append(serviceProviderTypeE)
                .append(licenseStatusE)
                .append(licenseTypeE)
                .append(issueCity)
                .append(licenseIssueDate)
                .append(licenseEndDate)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("licenceNumber", licenceNumber)
                .append("serviceProviderTypeE", serviceProviderTypeE)
                .append("licenseStatusE", licenseStatusE)
                .append("licenseTypeE", licenseTypeE)
                .append("issueCity", issueCity)
                .append("licenseIssueDate", licenseIssueDate)
                .append("licenseEndDate", licenseEndDate)
                .toString();
    }

}
