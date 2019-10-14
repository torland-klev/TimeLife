package com.henriktk.timer_v2;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final int TIMER_REQUEST_CODE = 420, size_dp = 8;
    private final String storage_filename = "/main.tmp";
    private static int view_id = 420;
    private HashMap<String, Long> timers = new HashMap<>();
    private HashMap<String, TextView> views = new HashMap<>();
    private String newName = "";

    private int dp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dp = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, size_dp, getResources()
                        .getDisplayMetrics());
        readCache();
        fillActivity();
    }

    @SuppressLint("ResourceType")
    private void fillActivity() {
        final ConstraintLayout layout = findViewById(R.id.main_layout);
        boolean first = true;
        for (Map.Entry<String, Long> pair : timers.entrySet()){
            final String name = pair.getKey();
            final Long time = pair.getValue();
            if (views.containsKey(name)){
                views.get(name).setText(String.format("%s\n%s", name, time.toString()));
            } else {
                view_id++;
                Log.d("FillACtivity", "Name " + name + " time " + time);
                ConstraintSet set = new ConstraintSet();
                final TextView tv = new TextView(this);
                views.put(name, tv);
                tv.setClickable(true);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToTimeActivity(name, time);
                    }
                });
                tv.setId(view_id);
                layout.addView(tv);
                set.clone(layout);
                tv.setTextColor(Color.LTGRAY);
                tv.setGravity(Gravity.CENTER);
                tv.setText(String.format("%s\n%s", name, time.toString()));
                set.connect(tv.getId(), ConstraintSet.TOP, R.id.welcome, ConstraintSet.BOTTOM, 4*dp);
                set.connect(tv.getId(), ConstraintSet.START, R.id.main_layout, ConstraintSet.START);
                set.connect(tv.getId(), ConstraintSet.END, R.id.main_layout, ConstraintSet.END);
                if (first) {
                    first = false;
                    set.connect(R.id.newTimerButton, ConstraintSet.TOP, tv.getId(), ConstraintSet.BOTTOM, 4*dp);
                } else {
                    set.connect((tv.getId()-1), ConstraintSet.TOP, tv.getId(), ConstraintSet.BOTTOM, 4*dp);
                    set.connect(R.id.newTimerButton, ConstraintSet.TOP, tv.getId()-timers.size()+1, ConstraintSet.BOTTOM, 4*dp);
                }
                set.applyTo(layout);
            }

        }
        writeCache();
    }

    private void readCache(){
        try {
            Log.d("MAIN", "Attempting to read file");
            FileInputStream fis = new FileInputStream(this.getCacheDir() + storage_filename);
            ObjectInputStream ois = new ObjectInputStream(fis);
            timers = (HashMap<String, Long>) ois.readObject();
            fis.close();
            ois.close();
        } catch (FileNotFoundException e) {
            Log.d("MAIN","File was not found.");
        } catch (IOException e) {
            Log.d("MAIN","Problem reading from file.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void writeCache(){
        try {
            File f = new File(this.getCacheDir() + storage_filename);
            FileOutputStream out = new FileOutputStream(f);
            ObjectOutputStream obj = new ObjectOutputStream(out);
            obj.writeObject(timers);
            obj.close();
            out.close();
            Log.d("MAIN", "Cache was written " + f.getAbsolutePath());
        } catch (FileNotFoundException e) {
            Log.d("MAIN", "File not found.");
        } catch (IOException e) {
            Log.d("MAIN", "Could not write to file.");
            e.printStackTrace();
        }
    }

    protected void onStop() {
        super.onStop();
        writeCache();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TIMER_REQUEST_CODE){
            if (resultCode == RESULT_OK){
                Log.d("Main:onActivityResult", "DisplayTimer returned result");
                Long newTime = data.getLongExtra("TIME", 0);
                String newName = data.getStringExtra("NAME");
                timers.put(newName, newTime);
                fillActivity();
            }
        }
    }

    protected void  goToTimeActivity(String name, long time){
        Intent intent = new Intent(this, DisplayTimer.class);
        intent.putExtra("NAME", name);
        intent.putExtra("TIME", time);
        startActivityForResult(intent,TIMER_REQUEST_CODE);
    }

    public void newTimer(final View view) {
        newName = null;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New timer name");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newName = input.getText().toString();
                if (timers.containsKey(newName)){
                    new AlertDialog.Builder(view.getContext()).setMessage("Name taken.").show();
                } else {
                    timers.put(newName, (long) 0);
                    Log.d("NewTimerName", newName);
                }
                fillActivity();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }
}
