package com.henriktk.timer_v2;

import android.content.Context;
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
    private ItemClickListener mClickListener;

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
        String name = t.getName();
        Long ltime = t.getTime();
        String time = Long.toString(ltime);
        holder.name.setText(name);
        holder.time.setText(time);
        holder.delete.setTag(t.getId());
        holder.start.setTag(t.getId());
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
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }

    }


    // convenience method for getting data at click position
    Timer getItem(int id) {
        return timers.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
