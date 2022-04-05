package com.mc2022.template.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mc2022.template.Models.Model_Gyroscope;

import java.util.List;

@Dao
public interface DaoGyroscope {
    @Query("SELECT CASE WHEN EXISTS(SELECT 1 FROM gyroscope_model) THEN 0 ELSE 1 END AS IsEmpty")
    boolean isempty();
    @Query("SELECT MAX(num)  FROM gyroscope_model")
    int getlastid();
    @Query(("Delete from gyroscope_model where :colname = :itemname"))
    void deleteitem(String colname, String itemname);

    @Query("Select * from gyroscope_model")
    List<Model_Gyroscope> getList();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Model_Gyroscope data);

    @Update
    void update(Model_Gyroscope data);

    @Delete
    void delete(Model_Gyroscope data);

    @Query("Delete from gyroscope_model")
    void deletegyroscopetable();
}
