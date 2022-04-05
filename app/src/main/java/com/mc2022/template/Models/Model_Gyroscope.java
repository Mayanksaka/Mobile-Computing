package com.mc2022.template.Models;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "gyroscope_model")
public class Model_Gyroscope {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int num;
    @ColumnInfo(name = "time",defaultValue = "CURRENT_TIMESTAMP")
    @NonNull
    private long time;
    @ColumnInfo(name = "x")
    @NonNull
    private double x_axis;
    @ColumnInfo(name = "y")
    @NonNull
    private double y_axis;
    @ColumnInfo(name = "z")
    @NonNull
    private double z_axis;

    public Model_Gyroscope(int num, long time, double x_axis, double y_axis, double z_axis) {
        this.num = num;
        this.time = time;
        this.x_axis = x_axis;
        this.y_axis = y_axis;
        this.z_axis = z_axis;
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

    public double getX_axis() {
        return x_axis;
    }

    public void setX_axis(double x_axis) {
        this.x_axis = x_axis;
    }

    public double getY_axis() {
        return y_axis;
    }

    public void setY_axis(double y_axis) {
        this.y_axis = y_axis;
    }

    public double getZ_axis() {
        return z_axis;
    }

    public void setZ_axis(double z_axis) {
        this.z_axis = z_axis;
    }

    @NonNull
    @Override
    public String toString() {
        return "num= "+ num + "time= "+ time +"x= "+x_axis+"y= "+y_axis+"z= "+z_axis;
    }
}

