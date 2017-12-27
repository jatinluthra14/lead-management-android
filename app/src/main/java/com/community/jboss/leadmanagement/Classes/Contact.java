package com.community.jboss.leadmanagement.Classes;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by carbonyl on 09/12/2017.
 */

@Entity
public class Contact {

    @PrimaryKey
    @NonNull
    private String uuid;
    private String name;

    public Contact(String name) {
        this.uuid = UUID.randomUUID().toString();
        this.name = name;
    }

    public Contact(UUID uuid, String name) {
        this.uuid = uuid.toString();
        this.name = name;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid.toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return UUID.fromString(uuid);
    }

    public String getName() {
        return name;
    }

}
