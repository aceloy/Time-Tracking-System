package com.citu.timetrackingsystem.view.list.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.citu.timetrackingsystem.R;
import com.citu.timetrackingsystem.model.TimeLog;

public class TimeLogViewHolder extends RecyclerView.ViewHolder {

    public TextView mTextViewDate, mTextViewTimeIn, mTextViewTimeOut;

    private View mItemView;

    public TimeLogViewHolder(@NonNull View itemView) {
        super(itemView);
        mItemView = itemView;
        mTextViewDate = mItemView.findViewById(R.id.item_time_log_textView_date);
        mTextViewTimeIn = mItemView.findViewById(R.id.item_time_log_textView_time_in);
        mTextViewTimeOut = mItemView.findViewById(R.id.item_time_log_textView_time_out);
    }

    public void setValues(TimeLog timeLog) {
        if (timeLog == null) {
            return;
        }
        mTextViewDate.setText(timeLog.getFormattedCreatedDate());
        mTextViewTimeIn.setText(timeLog.getFormattedTimeIn());
        mTextViewTimeOut.setText(timeLog.getTimeOut());
    }
}
