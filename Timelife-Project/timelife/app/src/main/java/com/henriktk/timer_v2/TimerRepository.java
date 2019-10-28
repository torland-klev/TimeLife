package com.henriktk.timer_v2;

import android.content.Context;
import java.util.List;

public class TimerRepository {
    protected final TimerDao timerDao;

    protected TimerRepository(Context context){
        TimerDatabase db = TimerDatabase.getDatabase(context);
        this.timerDao = db.timerDao();
    }

    protected List<Timer> getTimers(){
        return timerDao.getAll();
    }


    public long insert (Timer timer) {
        return timerDao.insert(timer);
    }

    public void delete (int id) { timerDao.remove(id); }

    public void update(int id, long time){ timerDao.update(id, time); }

    public int[] getIdsFromName(String name){ return timerDao.getIdsFromName(name); }
}
