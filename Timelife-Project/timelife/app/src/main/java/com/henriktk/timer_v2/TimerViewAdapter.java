package com.henriktk.timer_v2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TimerViewAdapter extends RecyclerView.Adapter<TimerViewAdapter.ViewHolder> {

    private List<Timer> timers;
    private LayoutInflater mInflater;

    public TimerViewAdapter(Context context, List<Timer> timers){
        this.timers = timers;
        mInflater = LayoutInflater.from(context);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Timer t = timers.get(position);
        Object[] tags = {t, holder.time};
        String name = t.getName();
        Long ltime = t.getTime();
        String time = Long.toString(ltime);
        holder.name.setText(name);
        holder.time.setText(time);
        holder.delete.setTag(t);
        holder.start.setTag(tags);
    }


    @Override
    public int getItemCount() {
        return timers.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name, time;
        Button delete, start;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.timerTextName);
            time = itemView.findViewById(R.id.timerTextTime);
            start = itemView.findViewById(R.id.buttonStart);
            delete = itemView.findViewById(R.id.buttonDelete);
            delete.setVisibility(View.GONE);
            start.setVisibility(View.GONE);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (delete.getVisibility() == View.VISIBLE){
                delete.setVisibility(View.GONE);
                start.setVisibility(View.GONE);
            } else if (delete.getVisibility() == View.GONE){
                delete.setVisibility(View.VISIBLE);
                start.setVisibility(View.VISIBLE);
            }
        }



    }
}
