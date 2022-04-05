package com.mc2022.template.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mc2022.template.Models.Model_Proximity;

import java.util.List;

@Dao
public interface DaoProximity {
    @Query("SELECT CASE WHEN EXISTS(SELECT 1 FROM proximity_model) THEN 0 ELSE 1 END AS IsEmpty")
    boolean isempty();
    @Query("SELECT MAX(num)  FROM proximity_model")
    int getlastid();

    @Query("Delete from proximity_model where :colname = :itemname")
    void deleteitem(String colname, String itemname);

    @Query("Select * from proximity_model")
    List<Model_Proximity> getList();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Model_Proximity data);

    @Update
    void update(Model_Proximity data);

    @Delete
    void delete(Model_Proximity data);

    @Query("Delete from proximity_model")
    void delete_proximity_table();
}

