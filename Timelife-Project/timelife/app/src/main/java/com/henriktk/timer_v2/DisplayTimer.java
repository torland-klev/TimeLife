package com.henriktk.timer_v2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DisplayTimer extends AppCompatActivity {

    private String name = null;
    private long startTime = 0, time = 0;
    private Thread thread = null;
    private boolean running = false;
    private boolean hasRun = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_timer);
        name = getIntent().getStringExtra("NAME");
        time = getIntent().getLongExtra("TIME", 0);
        TextView tv_name = findViewById(R.id.timer_name);
        tv_name.setText(name);
        final TextView tv_time = findViewById(R.id.time);
        tv_time.setText(String.valueOf(time));

        thread = new Thread(){
            public void run() {
                try {
                    while(!thread.isInterrupted()){
                        Thread.sleep(10);
                        if (!running){

                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    time = System.currentTimeMillis() - startTime;
                                    tv_time.setText(String.valueOf(time));
                                }
                            });
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

    }

    public void stop(View view) {
        running = false;
    }

    public void start(View view) {
        startTime = System.currentTimeMillis() - time;
        if (!hasRun){
            hasRun = true;
            thread.start();
        }
        running = true;
    }

    @Override
    public void onBackPressed() {
        Log.d("DisplayTimer", "Back button pressed.");
        running = false;
        Intent mIntent = new Intent();
        mIntent.putExtra("TIME", time);
        mIntent.putExtra("NAME", name);
        setResult(Activity.RESULT_OK, mIntent);
        finish();
    }

}
