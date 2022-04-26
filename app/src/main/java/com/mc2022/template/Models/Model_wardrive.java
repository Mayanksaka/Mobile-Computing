package com.mc2022.template.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "wardrive_model")
public class Model_wardrive {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int num;

    @ColumnInfo(name = "name")
    @NonNull
    private String name;
    @ColumnInfo(name = "room")
    @NonNull
    private String room_number;
    @ColumnInfo(name= "ssid_1")
    @NonNull
    private int ssid_1;
    @ColumnInfo(name= "ssid_2")
    @NonNull
    private int ssid_2;
    @ColumnInfo(name= "ssid_3")
    @NonNull
    private int ssid_3;
    @ColumnInfo(name= "ssid_4")
    @NonNull
    private int ssid_4;

    public Model_wardrive(int num, @NonNull String name, String room_number, int ssid_1, int ssid_2, int ssid_3, int ssid_4) {
        this.num = num;
        this.name = name;
        this.room_number = room_number;
        this.ssid_1 = ssid_1;
        this.ssid_2 = ssid_2;
        this.ssid_3 = ssid_3;
        this.ssid_4 = ssid_4;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getRoom_number() {
        return room_number;
    }

    public void setRoom_number(String room_number) {
        this.room_number = room_number;
    }

    public int getSsid_1() {
        return ssid_1;
    }

    public void setSsid_1(int ssid_1) {
        this.ssid_1 = ssid_1;
    }

    public int getSsid_2() {
        return ssid_2;
    }

    public void setSsid_2(int ssid_2) {
        this.ssid_2 = ssid_2;
    }

    public int getSsid_3() {
        return ssid_3;
    }

    public void setSsid_3(int ssid_3) {
        this.ssid_3 = ssid_3;
    }

    public int getSsid_4() {
        return ssid_4;
    }

    public void setSsid_4(int ssid_4) {
        this.ssid_4 = ssid_4;
    }

    public String toString(){
        return "Name: "+name+"\nroom: "+room_number+"\nssid_1: "+ssid_1+"\nssid_2: "+ssid_2+"\nssid_3: "+ssid_3+"\nssid_4: "+ssid_4;
    }
}
