package com.mc2022.template.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "gps_model")
public class Model_GPS {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int num;
    @NonNull
    @ColumnInfo(name = "time",defaultValue = "CURRENT_TIMESTAMP")
    private long time;
    @ColumnInfo(name = "longitude")
    @NonNull
    private double longitude;
    @ColumnInfo(name = "latitude")
    @NonNull
    private double latitude;
    @ColumnInfo(name= "name")
    @NonNull
    private String name;
    @ColumnInfo(name= "address")
    @NonNull
    private String address;

    public Model_GPS( int num, long time, double longitude,double latitude,String name, String address){
        this.num= num;
        this.time=time;
        this.longitude=longitude;
        this.latitude=latitude;
        this.name=name;
        this.address=address;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public void setAddress(@NonNull String address) {
        this.address = address;
    }

    public String toString(){
        return "Name: "+name+"\nAddress: "+address+"\nLongitude: "+longitude+"\nLatitude: "+latitude;
    }
}
