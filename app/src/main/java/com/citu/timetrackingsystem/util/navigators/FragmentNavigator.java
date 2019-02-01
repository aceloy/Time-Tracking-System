package com.citu.timetrackingsystem.util.navigators;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.citu.timetrackingsystem.R;
import com.citu.timetrackingsystem.view.fragments.TimeLogsFragment;
import com.citu.timetrackingsystem.view.fragments.UsersFragment;

public class FragmentNavigator {

    private static void setTitle(FragmentActivity fragmentActivity, String title) {
        ((AppCompatActivity) fragmentActivity)
                .getSupportActionBar()
                .setTitle(title);
    }

    public static void goToUsersFragment(FragmentActivity fragmentActivity, int id, UsersFragment usersFragment) {
        setTitle(fragmentActivity, fragmentActivity.getString(R.string.action_users));
        fragmentActivity
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(id, usersFragment)
                .commit();
    }

    public static void goToTimeLogsFragment(FragmentActivity fragmentActivity, int id, TimeLogsFragment timeLogsFragment) {
        setTitle(fragmentActivity, fragmentActivity.getString(R.string.action_time_logs));
        fragmentActivity
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(id, timeLogsFragment)
                .commit();
    }
}
