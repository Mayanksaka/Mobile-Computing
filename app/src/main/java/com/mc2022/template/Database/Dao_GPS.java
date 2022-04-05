package com.mc2022.template.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mc2022.template.Models.Model_GPS;

import java.util.List;

@Dao
public interface Dao_GPS {
    @Query("SELECT CASE WHEN EXISTS(SELECT 1 FROM gps_model) THEN 0 ELSE 1 END AS IsEmpty")
    boolean isempty();
    @Query("SELECT MAX(num)  FROM gps_model")
    int getlastid();
    @Query(("Delete from gps_model where :colname = :itemname"))
    void deleteitem(String colname, String itemname);
    @Query("Select * from gps_model")
    List<Model_GPS> getList();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Model_GPS data);

    @Update
    void update(Model_GPS data);

    @Delete
    void delete(Model_GPS data);

    @Query("Delete from gps_model")
    void deletegpstable();

}

