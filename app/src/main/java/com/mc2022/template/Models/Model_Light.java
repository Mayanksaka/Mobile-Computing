package com.mc2022.template.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "light_model")
public class Model_Light {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int num;
    @ColumnInfo(name = "time",defaultValue = "CURRENT_TIMESTAMP")
    @NonNull
    private long time;
    @ColumnInfo(name = "lvalue")
    @NonNull
    private double lvalue;

    public Model_Light(int num, long time, double lvalue) {
        this.num = num;
        this.time = time;
        this.lvalue = lvalue;
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

    public double getLvalue() {
        return lvalue;
    }

    public void setLvalue(double lvalue) {
        this.lvalue = lvalue;
    }

    @NonNull
    @Override
    public String toString() {
        return "num= "+ num + "time= "+ time +"lvalue= "+lvalue;
    }
}
