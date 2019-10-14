package com.henriktk.timer_v2;

import android.content.Context;
import java.util.List;

public class TimerRepository {
    private TimerDao timerDao;

    protected TimerRepository(Context context){
        TimerDatabase db = TimerDatabase.getDatabase(context);
        this.timerDao = db.timerDao();
    }

    protected List<Timer> getTimers(){
        return timerDao.getAll();
    }


    public void insert (Timer timer) {
        timerDao.insert(timer);
    }


}
