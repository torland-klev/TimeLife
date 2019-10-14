package com.henriktk.timer_v2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.List;
import java.util.ListIterator;


public class MainActivity extends AppCompatActivity {

    private TimerRepository repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repo = new TimerRepository(this);

    }


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
                Timer t = new Timer(input.getText().toString(), 0);
                repo.insert(t);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.show();

        List<Timer> list = repo.getTimers();
        ListIterator<Timer> li = list.listIterator();
        while(li.hasNext()){
            Log.d("Next", li.next().getName());
        }
    }
}
