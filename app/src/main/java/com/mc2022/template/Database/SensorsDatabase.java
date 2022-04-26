package com.mc2022.template.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.mc2022.template.Models.Model_GPS;
import com.mc2022.template.Models.Model_LinearAcceleration;
import com.mc2022.template.Models.Model_MagneticField;
import com.mc2022.template.Models.Model_wardrive;

@Database(entities = { Model_MagneticField.class, Model_GPS.class, Model_LinearAcceleration.class, Model_wardrive.class},version=1)
public abstract class SensorsDatabase extends RoomDatabase {
    public abstract Dao_LinearAcceleration Daoacc();
    public abstract Dao_GPS Daogps();
    public abstract Dao_Magnetic Daomagnetic();
    public abstract Dao_wardrive Daowardrive();


    private static SensorsDatabase instance;
    public static synchronized  SensorsDatabase getInstance(Context context){
        if(instance==null){
            instance = Room.databaseBuilder(context.getApplicationContext(),SensorsDatabase.class,"sensor_database").allowMainThreadQueries().build();
            return instance;
        }else{
            return instance;
        }
    }


}

