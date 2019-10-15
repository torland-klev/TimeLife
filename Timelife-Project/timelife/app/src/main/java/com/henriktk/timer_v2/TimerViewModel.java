package com.henriktk.timer_v2;

import androidx.lifecycle.ViewModel;

import java.util.List;

public class TimerViewModel extends ViewModel {

    public final List<Timer> timerList;

    public TimerViewModel(TimerDao dao){
        timerList = dao.getAll();
    }
}
