package com.community.jboss.leadmanagement.Classes;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

/**
 * Created by carbonyl on 09/12/2017.
 */

public class Number extends SugarRecord {

    private String number;
    private Contact contact;

    public Number() {

    }

    public Number(String number, Contact contact) {
        this.number = number;
        this.contact = contact;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public String getNumber() {
        return number;
    }

    public Contact getContact() {
        return contact;
    }
}
