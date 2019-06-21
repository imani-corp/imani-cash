package com.imani.cash.domain.property.rental;

import com.google.common.collect.ImmutableSet;
import com.imani.cash.domain.AuditableRecord;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author manyce400
 */
@Entity
@Table(name="Apartment")
public class Apartment extends AuditableRecord {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;


    @Column(name="ApartmentNumber", nullable=false, length = 30)
    private String apartmentNumber;


    @Column(name="IsRented", nullable = true, columnDefinition = "TINYINT", length = 1)
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean isRented;


    // Tracks the floor that this apartment is on
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FloorID", nullable = true)
    private Floor floor;


    // Contains collection of all bedrooms in this apartment
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "apartment")
    private Set<Bedroom> bedrooms = new HashSet<>();


    public Apartment() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApartmentNumber() {
        return apartmentNumber;
    }

    public void setApartmentNumber(String apartmentNumber) {
        this.apartmentNumber = apartmentNumber;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }

    public Set<Bedroom> getBedrooms() {
        return ImmutableSet.copyOf(bedrooms);
    }

    public void addToBedrooms(Bedroom bedroom) {
        Assert.notNull(bedroom, "bedroom cannot be null");
        this.bedrooms.add(bedroom);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Apartment apartment = (Apartment) o;

        return new EqualsBuilder()
                .append(isRented, apartment.isRented)
                .append(id, apartment.id)
                .append(apartmentNumber, apartment.apartmentNumber)
                .append(floor, apartment.floor)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(apartmentNumber)
                .append(isRented)
                .append(floor)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("apartmentNumber", apartmentNumber)
                .append("isRented", isRented)
                .append("floor", floor)
                .toString();
    }
}
