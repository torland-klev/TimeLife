package com.henriktk.timer_v2;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Timer.class}, version = 1)
public abstract class TimerDatabase extends RoomDatabase{
    public abstract TimerDao timerDao();

    private static volatile TimerDatabase INSTANCE;

    // Make the database a singleton to prevent having multiple instances opened at the same time.
    static TimerDatabase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (TimerDatabase.class) {
                if (INSTANCE == null) {
                    // Create the database
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), TimerDatabase.class, "timelife-db").allowMainThreadQueries().addCallback(rdbCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback rdbCallback = new RoomDatabase.Callback(){

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final TimerDao mDao;

        PopulateDbAsync(TimerDatabase db) {
            mDao = db.timerDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Timer word = new Timer("First Timer", 0);
            mDao.insert(word);
            return null;
        }
    }

}

