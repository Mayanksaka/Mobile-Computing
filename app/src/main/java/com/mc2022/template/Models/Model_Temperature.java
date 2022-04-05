package com.mc2022.template.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "temperature_model")
public class Model_Temperature {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int num;
    @ColumnInfo(name = "time",defaultValue = "CURRENT_TIMESTAMP")
    @NonNull
    private long time;
    @ColumnInfo(name = "lvalue")
    @NonNull
    private double tvalue;

    public Model_Temperature(int num, long time, double tvalue) {
        this.num = num;
        this.time = time;
        this.tvalue = tvalue;
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

    public double getTvalue() {
        return tvalue;
    }

    public void setTvalue(double tvalue) {
        this.tvalue = tvalue;
    }

    @NonNull
    @Override
    public String toString() {
        return "num= "+ num + "time= "+ time +"tvalue= "+tvalue;
    }
}
