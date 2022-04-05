package com.mc2022.template.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mc2022.template.Models.Model_Linear_Accelerometer;

import java.util.List;

@Dao
public interface DaoLinearAcceleration {
    @Query("SELECT CASE WHEN EXISTS(SELECT 1 FROM accelerometer_Model) THEN 0 ELSE 1 END AS IsEmpty")
    boolean isempty();
    @Query("SELECT MAX(num)  FROM accelerometer_Model")
    int getlastid();
    @Query(("Delete from accelerometer_Model where :colname = :itemname"))
    void deleteitem(String colname, String itemname);

    @Query("Select * from accelerometer_Model")
    List<Model_Linear_Accelerometer> getList();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Model_Linear_Accelerometer data);

    @Update
    void update(Model_Linear_Accelerometer data);

    @Delete
    void delete(Model_Linear_Accelerometer data);

    @Query("Delete from accelerometer_Model")
    void deleteaccelerometertable();
}

