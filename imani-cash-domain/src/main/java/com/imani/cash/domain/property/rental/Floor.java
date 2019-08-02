package com.imani.cash.domain.property.rental;

import com.google.common.collect.ImmutableSet;
import com.imani.cash.domain.AuditableRecord;
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
@Table(name="Floor")
public class Floor extends AuditableRecord {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;


    @Column(name="FloorNumber", nullable=false)
    private Integer floorNumber;


    @Column(name="CodeName", nullable=true, length = 30)
    private String codeName;


    // Tracks the Property that this floor is on.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PropertyID", nullable = true)
    private Property property;


    // Contains the portfolio of properties managed by the property management firm
    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "floor")
    private Set<Apartment> apartments = new HashSet<>();


    public Floor() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(Integer floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public Set<Apartment> getApartments() {
        return ImmutableSet.copyOf(apartments);
    }

    public void addToApartments(Apartment apartment) {
        Assert.notNull(apartment, "apartment cannot be null");
        apartments.add(apartment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Floor floor = (Floor) o;

        return new EqualsBuilder()
                .append(id, floor.id)
                .append(floorNumber, floor.floorNumber)
                .append(codeName, floor.codeName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(floorNumber)
                .append(codeName)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("floorNumber", floorNumber)
                .append("codeName", codeName)
                .toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Floor floor = new Floor();

        public Builder floorNumber(Integer floorNumber) {
            floor.floorNumber = floorNumber;
            return this;
        }

        public Builder codeName(String codeName) {
            floor.codeName = codeName;
            return this;
        }

        public Builder property(Property property) {
            floor.property = property;
            return this;
        }

        public Floor build() {
            return floor;
        }
    }
}
