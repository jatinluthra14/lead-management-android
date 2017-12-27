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
public interface NumberDAO {

    @Insert
    void addNumber(Number... number);

    @Query("SELECT * FROM Number WHERE number LIKE :number")
    List<Number> getNumberusingNumber(String number);

    @Query("SELECT * FROM Number WHERE number LIKE :contact")
    List<Number> getNumberusingContact(String contact);

    @Update
    void updateNumber(Number... numbers);

}
