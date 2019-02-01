package com.citu.timetrackingsystem.view.list.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.citu.timetrackingsystem.R;
import com.citu.timetrackingsystem.model.User;

public class UserViewHolder extends RecyclerView.ViewHolder {

    public TextView mTextViewName;

    private View mItemView;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        mItemView = itemView;
        mTextViewName = mItemView.findViewById(R.id.item_user_name);
    }

    public void setValues(User user) {
        if (user == null) {
            return;
        }
        mTextViewName.setText(user.getName());
    }
}
