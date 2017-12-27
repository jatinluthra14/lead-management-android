package com.community.jboss.leadmanagement.Classes;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.arch.persistence.room.Delete;
import java.util.List;

/**
 * Created by jatin on 26-12-2017.
 */

@Dao
public interface ContactDAO {

    @Insert()
    void addContact(Contact... contacts);

    @Query("SELECT * FROM Contact")
    List<Contact> getContacts();

    @Update
    void updateContact(Contact... contacts);

    @Delete
    void deleteContact(Contact... contacts);

}
