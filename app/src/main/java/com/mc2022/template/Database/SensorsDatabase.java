package com.mc2022.template.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mc2022.template.Models.Model_GPS;

@Database(entities = {Model_Temperature.class, Model_Proximity.class, Model_Light.class, Model_Orientation.class, Model_Gyroscope.class, Model_GPS.class, Model_Linear_Accelerometer.class},version=2)
public abstract class SensorsDatabase extends RoomDatabase {
    public abstract DaoLinearAcceleration Daoacc();
    public abstract DaoProximity Daoproxi();
    public abstract DaoTempera Daotemp();
    public abstract Dao_GPS Daogps();
    public abstract DaoGyroscope Daogyro();
    public abstract DaoOrientation Daoorien();
    public abstract DaoLight Daolight();

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

