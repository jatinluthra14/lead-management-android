package com.community.jboss.leadmanagement;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.community.jboss.leadmanagement.Classes.Contact;
import com.community.jboss.leadmanagement.Classes.Number;
import com.community.jboss.leadmanagement.Classes.Tag;
import com.community.jboss.leadmanagement.Classes.ContactDAO;
import com.community.jboss.leadmanagement.Classes.NumberDAO;
import com.community.jboss.leadmanagement.Classes.TagDAO;

/**
 * Created by jatin on 26-12-2017.
 */

@Database(entities =
	{
		Contact.class,
		Number.class,
		Tag.class
	},
	version = 1,
	exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDAO contactDAO();
    public abstract NumberDAO numberDAO();
    public abstract TagDAO tagDAO();
}
