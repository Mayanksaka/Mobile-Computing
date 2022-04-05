package com.mc2022.template.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mc2022.template.Models.Model_Orientation;

import java.util.List;

@Dao
public interface DaoOrientation {

    @Query("SELECT CASE WHEN EXISTS(SELECT 1 FROM orientation_model) THEN 0 ELSE 1 END AS IsEmpty")
    boolean isempty();
    @Query("SELECT MAX(num)  FROM orientation_model")
    int getlastid();

    @Query(("Delete from orientation_model where :colname = :itemname"))
    void deleteitem(String colname, String itemname);

    @Query("Select * from orientation_model")
    List<Model_Orientation> getList();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Model_Orientation data);

    @Update
    void update(Model_Orientation data);

    @Delete
    void delete(Model_Orientation data);

    @Query("Delete from orientation_model")
    void delete_orientation_table();
}

