package com.imani.cash.domain.zdata.automation.nyc;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.imani.cash.domain.property.IHasPropertyData;
import com.imani.cash.domain.property.PropertyTypeE;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Domain model that captures all properties of an NYC Property which can be source from OpenData API's or data dump files.
 *
 * @author manyce400
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NYCProperty implements IHasPropertyData {



    private Integer buildingID;

    private Long boroID;

    private String boro;

    private String houseNumber;

    private String lowHouseNumber;

    private String highHouseNumber;

    @JsonProperty("street_name")
    private String streetName;

    @JsonProperty("postcode")
    private String zip;

    @JsonProperty("block")
    private String block;

    @JsonProperty("lot")
    private String lot;

    @JsonProperty("bin_number")
    private String bin;

    private Integer communityBoard;

    private String censusTract;

    private String managementProgram;

    private Integer doBBuildingClassID;

    private String doBBuildingClass;

    private Integer legalStories;

    private Integer legalClassA;

    private Integer legalClassB;

    private Integer registrationID;

    private String lifeCycle;

    private Integer recordStatusID;

    private String recordStatus;

    private static final String PUBLIC_HOUSING_ID = "NYCHA";


    public NYCProperty() {

    }

    public Integer getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(Integer buildingID) {
        this.buildingID = buildingID;
    }

    public Long getBoroID() {
        return boroID;
    }

    public void setBoroID(Long boroID) {
        this.boroID = boroID;
    }

    public String getBoro() {
        return boro;
    }

    public void setBoro(String boro) {
        this.boro = boro;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getLowHouseNumber() {
        return lowHouseNumber;
    }

    public void setLowHouseNumber(String lowHouseNumber) {
        this.lowHouseNumber = lowHouseNumber;
    }

    public String getHighHouseNumber() {
        return highHouseNumber;
    }

    public void setHighHouseNumber(String highHouseNumber) {
        this.highHouseNumber = highHouseNumber;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
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

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public Integer getCommunityBoard() {
        return communityBoard;
    }

    public String getCensusTract() {
        return censusTract;
    }

    public void setCensusTract(String censusTract) {
        this.censusTract = censusTract;
    }

    public void setCommunityBoard(Integer communityBoard) {
        this.communityBoard = communityBoard;
    }

    public String getManagementProgram() {
        return managementProgram;
    }

    public void setManagementProgram(String managementProgram) {
        this.managementProgram = managementProgram;
    }

    public Integer getDoBBuildingClassID() {
        return doBBuildingClassID;
    }

    public void setDoBBuildingClassID(Integer doBBuildingClassID) {
        this.doBBuildingClassID = doBBuildingClassID;
    }

    public String getDoBBuildingClass() {
        return doBBuildingClass;
    }

    public void setDoBBuildingClass(String doBBuildingClass) {
        this.doBBuildingClass = doBBuildingClass;
    }

    public Integer getLegalStories() {
        return legalStories;
    }

    public void setLegalStories(Integer legalStories) {
        this.legalStories = legalStories;
    }

    public Integer getLegalClassA() {
        return legalClassA;
    }

    public void setLegalClassA(Integer legalClassA) {
        this.legalClassA = legalClassA;
    }

    public Integer getLegalClassB() {
        return legalClassB;
    }

    public void setLegalClassB(Integer legalClassB) {
        this.legalClassB = legalClassB;
    }

    public Integer getRegistrationID() {
        return registrationID;
    }

    public void setRegistrationID(Integer registrationID) {
        this.registrationID = registrationID;
    }

    public String getLifeCycle() {
        return lifeCycle;
    }

    public void setLifeCycle(String lifeCycle) {
        this.lifeCycle = lifeCycle;
    }

    public Integer getRecordStatusID() {
        return recordStatusID;
    }

    public void setRecordStatusID(Integer recordStatusID) {
        this.recordStatusID = recordStatusID;
    }

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    @Override
    public String getPropertyNumber() {
        return getHouseNumber();
    }

    @Override
    public String getZipCode() {
        return getZip();
    }

    @Override
    public Boolean isPublicHousing() {
        if(getManagementProgram() != null) {
            return getManagementProgram().equals(PUBLIC_HOUSING_ID);
        }

        return false;
    }

    @Override
    public PropertyTypeE getPropertyTypeE() {
        return PropertyTypeE.MultiFamily;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("buildingID", buildingID)
                .append("boroID", boroID)
                .append("boro", boro)
                .append("houseNumber", houseNumber)
                .append("lowHouseNumber", lowHouseNumber)
                .append("highHouseNumber", highHouseNumber)
                .append("streetName", streetName)
                .append("zip", zip)
                .append("block", block)
                .append("lot", lot)
                .append("bin", bin)
                .append("communityBoard", communityBoard)
                .append("censusTract", censusTract)
                .append("managementProgram", managementProgram)
                .append("doBBuildingClassID", doBBuildingClassID)
                .append("doBBuildingClass", doBBuildingClass)
                .append("legalStories", legalStories)
                .append("legalClassA", legalClassA)
                .append("legalClassB", legalClassB)
                .append("registrationID", registrationID)
                .append("lifeCycle", lifeCycle)
                .append("recordStatusID", recordStatusID)
                .append("recordStatus", recordStatus)
                .toString();
    }
}
