package com.henriktk.timer_v2;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Timer.class}, version = 4)
public abstract class TimerDatabase extends RoomDatabase{
    public abstract TimerDao timerDao();

    private static volatile TimerDatabase INSTANCE;

    // Make the database a singleton to prevent having multiple instances opened at the same time.
    static TimerDatabase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (TimerDatabase.class) {
                if (INSTANCE == null) {
                    // Create the database
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    TimerDatabase.class, "timelife-db")
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}

