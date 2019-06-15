package com.imani.cash.domain.property;

import com.google.common.collect.ImmutableSet;
import com.imani.cash.domain.AuditableRecord;
import com.imani.cash.domain.contact.EmbeddedContactInfo;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author manyce400
 */
@Entity
@Table(name="PropertyManager")
public class PropertyManager extends AuditableRecord {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;


    @Column(name="name", nullable=false, length = 50)
    private String name;


    @Embedded
    private EmbeddedContactInfo embeddedContactInfo;


    @Column(name="PaymentGatewayAcctID", nullable=true, length = 100)
    private String paymentGatewayAcctID;

    // Tracks the Business Property Address.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PropertyInfoID", nullable = true)
    private PropertyInfo businessAddressInfo;


    // Contains the portfolio of properties managed by the property management firm
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "propertyManager")
    private Set<PropertyInfo> portfolio = new HashSet<>();


    public PropertyManager() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EmbeddedContactInfo getEmbeddedContactInfo() {
        return embeddedContactInfo;
    }

    public void setEmbeddedContactInfo(EmbeddedContactInfo embeddedContactInfo) {
        this.embeddedContactInfo = embeddedContactInfo;
    }

    public String getPaymentGatewayAcctID() {
        return paymentGatewayAcctID;
    }

    public void setPaymentGatewayAcctID(String paymentGatewayAcctID) {
        this.paymentGatewayAcctID = paymentGatewayAcctID;
    }

    public PropertyInfo getBusinessAddressInfo() {
        return businessAddressInfo;
    }

    public void setBusinessAddressInfo(PropertyInfo businessAddressInfo) {
        this.businessAddressInfo = businessAddressInfo;
    }

    public Set<PropertyInfo> getPortfolio() {
        return ImmutableSet.copyOf(portfolio);
    }

    public void addToPortfolio(PropertyInfo propertyInfo) {
        Assert.notNull(propertyInfo, "propertyInfo cannot be null");
        this.portfolio.add(propertyInfo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PropertyManager that = (PropertyManager) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(name, that.name)
                .append(embeddedContactInfo, that.embeddedContactInfo)
                .append(paymentGatewayAcctID, that.paymentGatewayAcctID)
                .append(businessAddressInfo, that.businessAddressInfo)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(embeddedContactInfo)
                .append(paymentGatewayAcctID)
                .append(businessAddressInfo)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("embeddedContactInfo", embeddedContactInfo)
                .append("paymentGatewayAcctID", paymentGatewayAcctID)
                .append("businessAddressInfo", businessAddressInfo)
                .append("portfolio", portfolio)
                .toString();
    }
    
}