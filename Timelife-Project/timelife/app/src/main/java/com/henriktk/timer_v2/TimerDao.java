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

    @Query("UPDATE timer_table SET time = :time WHERE id = :id")
    void update(int id, long time);

    @Query("SELECT (id) FROM timer_table WHERE name = :name")
    int[] getIdsFromName(String name);

    @Query("DELETE FROM timer_table")
    void deleteAll();

}
