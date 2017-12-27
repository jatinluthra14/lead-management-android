package com.community.jboss.leadmanagement.Classes;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import java.util.List;

/**
 * Created by jatin on 26-12-2017.
 */

@Dao
public interface TagDAO {
    @Query("SELECT * FROM Tag")
    List<Tag> getTags();
}
