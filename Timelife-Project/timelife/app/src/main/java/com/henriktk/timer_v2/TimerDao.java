package com.henriktk.timer_v2;

import androidx.room.Dao;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Insert;

import java.util.List;

@Dao
public interface TimerDao {
    @Query("SELECT * FROM timer_table")
    List<Timer> getAll();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Timer timer);

    @Query("DELETE FROM timer_table")
    void deleteAll();

}
