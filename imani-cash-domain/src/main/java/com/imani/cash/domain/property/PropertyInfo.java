package com.imani.cash.domain.property;

import com.imani.cash.domain.AuditableRecord;
import com.imani.cash.domain.geographical.Borough;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * Models all data to uniquely identify a property by attributes captured at the municipality level.
 * For example in NYC all below attributes will help to uniquely identify a property and find all information
 * stored by the City on the property.
 *
 * TODO:  Currently this model is designed to support US Based properties.  In future refactor to support international addresses.
 *
 * @author manyce400
 */
@Entity
@Table(name="PropertyInfo")
public class PropertyInfo extends AuditableRecord {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;


    @Column(name="PropertyNumber", nullable=false, length=50)
    private Long propertyNumber;


    @Column(name="Name", nullable=false, length = 30)
    private String streetName;


    // Tax Block for the building
    @Column(name="Block", nullable=false, length = 30)
    private String block;


    // Tax lot for the building.
    @Column(name="Lot", nullable=false, length = 30)
    private String lot;


    // Buliding Identification Number is a unique number used to identify the building by City
    @Column(name="BIN", nullable=false, length=7)
    private Integer buildingIdentificationNumber;


    @Column(name="ZipCode", nullable=false, length=5)
    private Integer zipCode;

    @Column(name="Latitude", nullable=true)
    private Double latitude;

    @Column(name="Longitude", nullable=true)
    private Double longitude;


    @Column(name="PropertyTypeE", nullable=false, length=20)
    @Enumerated(EnumType.STRING)
    private PropertyTypeE propertyTypeE;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BoroughID", nullable = false)
    private Borough borough;


    // Maps to optional PropertyManagement firm responsible for managing the property
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PropertyManagerID", nullable = true)
    private PropertyManager propertyManager;


    public PropertyInfo() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPropertyNumber() {
        return propertyNumber;
    }

    public void setPropertyNumber(Long propertyNumber) {
        this.propertyNumber = propertyNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }

    public Integer getBuildingIdentificationNumber() {
        return buildingIdentificationNumber;
    }

    public void setBuildingIdentificationNumber(Integer buildingIdentificationNumber) {
        this.buildingIdentificationNumber = buildingIdentificationNumber;
    }

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public PropertyTypeE getPropertyTypeE() {
        return propertyTypeE;
    }

    public void setPropertyTypeE(PropertyTypeE propertyTypeE) {
        this.propertyTypeE = propertyTypeE;
    }

    public Borough getBorough() {
        return borough;
    }

    public void setBorough(Borough borough) {
        this.borough = borough;
    }

    public PropertyManager getPropertyManager() {
        return propertyManager;
    }

    public void setPropertyManager(PropertyManager propertyManager) {
        this.propertyManager = propertyManager;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PropertyInfo that = (PropertyInfo) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(propertyNumber, that.propertyNumber)
                .append(streetName, that.streetName)
                .append(block, that.block)
                .append(lot, that.lot)
                .append(buildingIdentificationNumber, that.buildingIdentificationNumber)
                .append(zipCode, that.zipCode)
                .append(latitude, that.latitude)
                .append(longitude, that.longitude)
                .append(propertyTypeE, propertyTypeE)
                .append(borough, that.borough)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(propertyNumber)
                .append(streetName)
                .append(block)
                .append(lot)
                .append(buildingIdentificationNumber)
                .append(latitude)
                .append(longitude)
                .append(zipCode)
                .append(propertyTypeE)
                .append(borough)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("propertyNumber", propertyNumber)
                .append("streetName", streetName)
                .append("block", block)
                .append("lot", lot)
                .append("buildingIdentificationNumber", buildingIdentificationNumber)
                .append("latitude", latitude)
                .append("longitude", longitude)
                .append("zipCode", zipCode)
                .append("propertyTypeE", propertyTypeE)
                .append("borough", borough)
                .append("propertyManager", propertyManager)
                .toString();
    }
}
