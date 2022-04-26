package com.mc2022.template.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mc2022.template.Models.Model_wardrive;

import java.util.List;

@Dao
public interface Dao_wardrive {
    @Query("SELECT CASE WHEN EXISTS(SELECT 1 FROM wardrive_model) THEN 0 ELSE 1 END AS IsEmpty")
    boolean isempty();
    @Query("SELECT MAX(num)  FROM wardrive_model")
    int getlastid();
    @Query(("Delete from wardrive_model where :colname = :itemname"))
    void deleteitem(String colname, String itemname);
    @Query("Select * from wardrive_model")
    List<Model_wardrive> getList();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Model_wardrive data);

    @Update
    void update(Model_wardrive data);

    @Delete
    void delete(Model_wardrive data);

    @Query("Delete from wardrive_model")
    void deletewardrivetable();

}

