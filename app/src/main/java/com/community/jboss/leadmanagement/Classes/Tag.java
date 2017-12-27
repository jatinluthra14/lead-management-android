package com.community.jboss.leadmanagement.Classes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by carbonyl on 09/12/2017.
 */

@Entity
public class Tag{
    @PrimaryKey @NonNull
    private String key;
    private String value;
    private Contact contact;

    public Tag(String key, String value, Contact contact) {
        this.key = key;
        this.value = value;
        this.contact = contact;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public Contact getContact() {
        return contact;
    }
}
