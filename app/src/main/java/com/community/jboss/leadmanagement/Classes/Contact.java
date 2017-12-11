package com.community.jboss.leadmanagement.Classes;

import com.orm.SugarRecord;
import com.orm.dsl.Table;
import com.orm.dsl.Unique;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by carbonyl on 09/12/2017.
 */

public class Contact extends SugarRecord {

    private String uuid;
    private String name;

    public Contact() {

    }

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

    public List<Number> getNumbers() {
        return Number.find(Number.class, "contact = ?", this.getId().toString());
    }

    public Map<String, String> getTags() {
        List<Tag> tagList = Tag.find(Tag.class, "contact = ?", this.getId().toString());
        Map<String, String> tags = new HashMap<>();
        for (Tag tag: tagList) {
            tags.put(tag.getKey(), tag.getValue());
        }
        return tags;
    }
}
