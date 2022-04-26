package com.mc2022.template.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mc2022.template.Models.Model_MagneticField;

import java.util.List;

@Dao
public interface Dao_Magnetic {
    @Query("SELECT CASE WHEN EXISTS(SELECT 1 FROM magnetic_field_Model) THEN 0 ELSE 1 END AS IsEmpty")
    boolean isempty();
    @Query("SELECT MAX(num)  FROM magnetic_field_Model")
    int getlastid();
    @Query(("Delete from magnetic_field_Model where :colname = :itemname"))
    void deleteitem(String colname, String itemname);
    @Query("Select * from magnetic_field_Model")
    List<Model_MagneticField> getList();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Model_MagneticField data);

    @Update
    void update(Model_MagneticField data);

    @Delete
    void delete(Model_MagneticField data);

    @Query("Delete from magnetic_field_Model")
    void deletegpstable();

}

