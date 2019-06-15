package com.imani.cash.domain.serviceprovider;

import com.imani.cash.domain.AuditableRecord;
import com.imani.cash.domain.contact.EmbeddedContactInfo;
import com.imani.cash.domain.property.PropertyInfo;
import com.imani.cash.domain.user.UserRecord;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Service providers are other companies or individuals who provide various services.
 * Services may include below
 * <ul>
 *     <li>Home contractor</li>
 * </ul>
 *
 * @author manyce400
 */
@Entity
@Table(name="ServiceProvider")
public class ServiceProvider extends AuditableRecord {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;


    @Column(name="ProviderName", nullable=false, length = 30)
    private String providerName;

    // Tracks detailed information of optional license that is issued to this Provider.
    @Embedded
    private EmbeddedProviderLicense embeddedProviderLicense;


    // Contact information for this ServiceProvider
    @Embedded
    private EmbeddedContactInfo providerContactInfo;


    // Tracks a registered IMani user that is associated with this provider.
    // Service requests can be directely routed to this user.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AssociatedUserRecordID", nullable = true)
    private UserRecord associatedUserRecord;


    // Tracks the Business Property Address.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PropertyInfoID", nullable = true)
    private PropertyInfo providerAddressInfo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public EmbeddedProviderLicense getEmbeddedProviderLicense() {
        return embeddedProviderLicense;
    }

    public void setEmbeddedProviderLicense(EmbeddedProviderLicense embeddedProviderLicense) {
        this.embeddedProviderLicense = embeddedProviderLicense;
    }

    public EmbeddedContactInfo getProviderContactInfo() {
        return providerContactInfo;
    }

    public void setProviderContactInfo(EmbeddedContactInfo providerContactInfo) {
        this.providerContactInfo = providerContactInfo;
    }

    public UserRecord getAssociatedUserRecord() {
        return associatedUserRecord;
    }

    public void setAssociatedUserRecord(UserRecord associatedUserRecord) {
        this.associatedUserRecord = associatedUserRecord;
    }

    public PropertyInfo getProviderAddressInfo() {
        return providerAddressInfo;
    }

    public void setProviderAddressInfo(PropertyInfo providerAddressInfo) {
        this.providerAddressInfo = providerAddressInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ServiceProvider that = (ServiceProvider) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(providerName, that.providerName)
                .append(embeddedProviderLicense, that.embeddedProviderLicense)
                .append(providerContactInfo, that.providerContactInfo)
                .append(associatedUserRecord, that.associatedUserRecord)
                .append(providerAddressInfo, that.providerAddressInfo)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(providerName)
                .append(embeddedProviderLicense)
                .append(providerContactInfo)
                .append(associatedUserRecord)
                .append(providerAddressInfo)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("providerName", providerName)
                .append("embeddedProviderLicense", embeddedProviderLicense)
                .append("providerContactInfo", providerContactInfo)
                .append("associatedUserRecord", associatedUserRecord)
                .append("providerAddressInfo", providerAddressInfo)
                .toString();
    }

}
