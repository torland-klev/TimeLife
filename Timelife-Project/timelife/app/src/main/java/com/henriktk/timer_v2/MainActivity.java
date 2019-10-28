package com.henriktk.timer_v2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private TimerRepository repo;
    private TimerViewAdapter mAdapter;
    private List<Timer> timers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize data repository
        repo = new TimerRepository(this);
        timers = repo.getTimers();

        // Initialize recycler view
        RecyclerView recyclerView = findViewById(R.id.timer_list);

        // Linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new TimerViewAdapter(this, timers);
        recyclerView.setAdapter(mAdapter);

    }

    /**
     * Displays a dialog to user prompting for the name of the new timer.
     * Creates and stores the new timer, then calls on the adapter to update.
     * @param view View of the calling button
     */
    public void newTimer(View view) {
        Log.i("NewTimer", "Creating new timer.");

        // Display dialog to user asking for name of new timer

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Insert name of new timer");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                newTimer(input.getText().toString());
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.show();
    }

    /**
     * Creates a new timer.
     * @param name Name of the new timer.
     */
    private void newTimer(String name){
        Log.d("New Timer", "Adding new timer");
        Timer t = new Timer(name, 0);
        t.setId((int) repo.insert(t));
        timers.add(t);
        mAdapter.notifyItemInserted(mAdapter.getItemCount());
    }




    public void delete(View view) {
        Timer t = (Timer) view.getTag();
        int id = repo.getIdsFromName(t.getName())[0];
        Log.d("DELETE VIEW ID", Integer.toString(id));
        repo.delete(id);
        mAdapter.notifyItemRemoved(timers.indexOf(t));
        timers.remove(timers.indexOf(t));
    }

    private final Handler timerHandler = new Handler();
    private final HashMap<View, Runnable> runners = new HashMap<>();
    private final HashMap<View, Boolean> bits = new HashMap<>();

    public void start(final View view) {
        final Timer t = (Timer) ((Object[]) view.getTag())[0];
        final TextView timerView = (TextView) ((Object[]) view.getTag())[1];
        final int id = t.getId();

        Log.d("START VIEW ID", Integer.toString(id));
        if (!bits.containsKey(view)){
            bits.put(view, false);
        }
        if (!runners.containsKey(view)){
            Log.d("Not got!", Integer.toString(id));
            final Runnable runner = new Runnable() {
                @Override
                public void run() {
                    if (bits.get(view)) {
                        long newTime = t.addTime(1);
                        repo.update(id, newTime);
                        timerView.setText(Long.toString(newTime));
                        timerHandler.postDelayed(this, 1000);
                        Log.d("Running!", Integer.toString(id));
                    }
                }
            };
            runners.put(view, runner);
        }
        if (!bits.get(view)){
            bits.put(view, true);
            timerHandler.postDelayed(runners.get(view), 500);
        } else {
            timerHandler.removeCallbacks(runners.get(view));
            bits.put(view, false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Iterator hmIter = runners.entrySet().iterator();
        while (hmIter.hasNext()){
            timerHandler.removeCallbacks((Runnable)((Map.Entry) hmIter.next()).getValue());
        }
        hmIter = bits.keySet().iterator();
        while (hmIter.hasNext()){
            bits.put((View) hmIter.next(), false);
        }
    }

    //*************************************************************//
    //************************ Logging ****************************//
    //*************************************************************//

    private void listAllTimers(){
        List<Timer> list = repo.getTimers();
        ListIterator<Timer> li = list.listIterator();
        while(li.hasNext()){
            Log.d("Timers:\n", li.next().getName());
        }
    }

}
