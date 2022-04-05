package com.mc2022.template.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mc2022.template.Models.Model_Light;

import java.util.List;

@Dao
public interface DaoLight {
    @Query("SELECT CASE WHEN EXISTS(SELECT 1 FROM gyroscope_model) THEN 0 ELSE 1 END AS IsEmpty")
    boolean isempty();
    @Query("SELECT MAX(num)  FROM light_model")
    int getlastid();
    @Query(("Delete from light_model where num = :itemname"))
    void deleteitem(int itemname);

    @Query("Select * from light_model")
    List<Model_Light> getList();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Model_Light data);

    @Update
    void update(Model_Light data);

    @Delete
    void delete(Model_Light data);

    @Query("Delete from light_model")
    void delete_light_table();
}
