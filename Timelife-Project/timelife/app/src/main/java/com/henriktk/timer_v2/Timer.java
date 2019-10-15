package com.henriktk.timer_v2;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "timer_table")
public class Timer {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

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

    public void setId(int i ){ this.id = i; }

    public int getId() { return this.id; }

    public long getTime(){
        return this.time;
    }


}
