package com.community.jboss.leadmanagement.Contacts;

import com.orm.SugarRecord;
import java.util.UUID;
import java.util.List;

/**
 * Created by jatin on 17-12-2017.
 */

public class Contact extends SugarRecord{
    private UUID contactUUID;
    private String contactName;
    private List contactNumbers;

    public Contact(UUID contactUUID, String contactName, List contactNumbers)
    {
        this.contactUUID = contactUUID;
        this.contactName = contactName;
        this.contactNumbers = contactNumbers;
    }

    public void setContactUUID()
    {
        this.contactUUID = contactUUID;
    }

    public void setContactName()
    {
        this.contactName = contactName;
    }

    public void setContactNumbers()
    {
        this.contactNumbers = contactNumbers;
    }

    public UUID getContactUUID(UUID contactUUID)
    {
        return contactUUID;
    }

    public String getContactName(String contactName)
    {
        return contactName;
    }

    public List getContactNumbers(List contactNumbers)
    {
        return contactNumbers;
    }

}
