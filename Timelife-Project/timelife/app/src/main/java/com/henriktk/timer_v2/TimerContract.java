package com.henriktk.timer_v2;

import android.provider.BaseColumns;

public final class TimerContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private TimerContract() {}

    public static class TimerEntry implements BaseColumns {
        public static final String TABLE_NAME = "timers";
        public static final String COLUMN_NAME_TITLE = "name";
        public static final String COLUMN_NAME_SUBTITLE = "time";
    }

    protected static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TimerEntry.TABLE_NAME + " (" +
                    TimerEntry._ID + " INTEGER PRIMARY KEY," +
                    TimerEntry.COLUMN_NAME_TITLE + " TEXT," +
                    TimerEntry.COLUMN_NAME_SUBTITLE + " INTEGER)";

    protected static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TimerEntry.TABLE_NAME;
}
