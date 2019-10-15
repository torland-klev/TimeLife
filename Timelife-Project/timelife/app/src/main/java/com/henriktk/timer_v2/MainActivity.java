package com.henriktk.timer_v2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;
import java.util.ListIterator;


public class MainActivity extends AppCompatActivity implements TimerViewAdapter.ItemClickListener{

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
        mAdapter.setClickListener(this);
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
        repo.insert(t);
        timers.add(t);
        mAdapter.notifyItemInserted(mAdapter.getItemCount());
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + mAdapter.getItem(position).getName() + " on row number " + position, Toast.LENGTH_SHORT).show();
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
