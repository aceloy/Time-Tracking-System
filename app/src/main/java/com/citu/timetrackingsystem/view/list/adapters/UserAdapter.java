package com.citu.timetrackingsystem.view.list.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.citu.timetrackingsystem.R;
import com.citu.timetrackingsystem.model.User;
import com.citu.timetrackingsystem.view.list.viewholders.UserViewHolder;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    public List<User> mUsers;

    public UserAdapter(Context context, List<User> users) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
        mUsers = users;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_user, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        UserViewHolder userViewHolder = (UserViewHolder) viewHolder;
        userViewHolder.setValues(mUsers.get(position));
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public void swapItems(List<User> users) {
        mUsers = users;
        notifyDataSetChanged();
    }
}
