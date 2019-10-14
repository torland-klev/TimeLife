package com.henriktk.timer_v2;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "timer_table")
public class Timer {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "time")
    private long time;

    public Timer(String name, long time){
        this.name = name;
        this.time = time;
    }

    public String getName(){
        return this.name;
    }

    public long getTime(){
        return this.time;
    }
}
