package com.mc2022.template.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mc2022.template.Models.Model_Temperature;

import java.util.List;

@Dao
public interface DaoTempera {
    @Query("SELECT CASE WHEN EXISTS(SELECT 1 FROM temperature_model) THEN 0 ELSE 1 END AS IsEmpty")
    boolean isempty();
    @Query("SELECT MAX(num)  FROM temperature_model")
    int getlastid();

    @Query(("Delete from temperature_model where num = :itemname"))
    void deleteitem(int itemname);

    @Query("Select * from temperature_model")
    List<Model_Temperature> getList();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Model_Temperature data);

    @Update
    void update(Model_Temperature data);

    @Delete
    void delete(Model_Temperature data);

    @Query("Delete from temperature_model")
    void delete_temperature_table();
}
