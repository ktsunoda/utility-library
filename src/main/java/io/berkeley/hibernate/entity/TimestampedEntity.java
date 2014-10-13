package io.berkeley.hibernate.entity;


import java.util.Date;


@SuppressWarnings("UnusedDeclaration")
public interface TimestampedEntity {

    public Date getCreatedDate();


    public void setCreatedDate(Date createdDate);


    public Date getLastModifiedDate();


    public void setLastModifiedDate(Date lastModifiedDate);
}
