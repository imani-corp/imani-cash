package com.imani.cash.domain;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

/**
 * Parent class for tracking all audit changes automatically.  Subclasses can enable this feature by extending this class
 *
 * @author manyce400
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditableRecord {

    @Column(name = "CreateDate", nullable = false, updatable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @CreatedDate
    private DateTime createDate;

    @Column(name = "ModifyDate")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @LastModifiedDate
    private DateTime modifyDate;


    public DateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(DateTime createDate) {
        this.createDate = createDate;
    }

    public DateTime getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(DateTime modifyDate) {
        this.modifyDate = modifyDate;
    }

}