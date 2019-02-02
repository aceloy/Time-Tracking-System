package com.citu.timetrackingsystem.view.list.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.citu.timetrackingsystem.R;
import com.citu.timetrackingsystem.model.TimeLog;
import com.citu.timetrackingsystem.view.list.viewholders.TimeLogViewHolder;

import java.util.List;

public class TimeLogAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    public List<TimeLog> mTimeLogs;

    public TimeLogAdapter(Context context, List<TimeLog> timeLogs) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mTimeLogs = timeLogs;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_time_log, parent, false);
        return new TimeLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        TimeLogViewHolder timeLogViewHolder = (TimeLogViewHolder) viewHolder;
        timeLogViewHolder.setValues(mTimeLogs.get(position));
    }

    @Override
    public int getItemCount() {
        return mTimeLogs.size();
    }

    public void swapItems(List<TimeLog> timeLogs) {
        mTimeLogs = timeLogs;
        notifyDataSetChanged();
    }

    public TimeLog getTimeLogForToday() {
        for (TimeLog timeLog : mTimeLogs) {
            if (timeLog.isToday())
                return timeLog;
        }
        return null;
    }
}
