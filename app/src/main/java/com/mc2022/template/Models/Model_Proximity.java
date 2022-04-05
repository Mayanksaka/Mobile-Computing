package com.mc2022.template.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "proximity_model")
public class Model_Proximity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int num;
    @ColumnInfo(name = "time",defaultValue = "CURRENT_TIMESTAMP")
    @NonNull
    private long time;
    @ColumnInfo(name = "pvalue")
    @NonNull
    private float pvalue;

    public Model_Proximity(int num, long time, float pvalue) {
        this.num = num;
        this.time = time;
        this.pvalue = pvalue;
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

    public float getPvalue() {
        return pvalue;
    }

    public void setPvalue(float pvalue) {
        this.pvalue = pvalue;
    }

    @NonNull
    @Override
    public String toString() {
        return "num= "+ num + "time= "+ time +"pvalue= "+pvalue;
    }
}

