package com.imani.cash.domain.payee;

import com.imani.cash.domain.AuditableRecord;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;

/**
 * PayeeRecord is the domain model for an entity or person to whom money is paid.
 *
 * @author manyce400
 */
@Entity
@Table(name="PayeeRecord")
public class PayeeRecord extends AuditableRecord {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID", nullable=false)
    private Long id;

    @Column(name="PayeeType", nullable=true, length=10)
    @Enumerated(EnumType.STRING)
    private PayeeTypeE payeeTypeE;

    public PayeeRecord() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PayeeTypeE getPayeeTypeE() {
        return payeeTypeE;
    }

    public void setPayeeTypeE(PayeeTypeE payeeTypeE) {
        this.payeeTypeE = payeeTypeE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PayeeRecord that = (PayeeRecord) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(payeeTypeE, that.payeeTypeE)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(payeeTypeE)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("payeeTypeE", payeeTypeE)
                .toString();
    }
}
