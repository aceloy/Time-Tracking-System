package com.citu.timetrackingsystem.util.navigators;

import android.content.Context;
import android.content.Intent;

import com.citu.timetrackingsystem.model.User;
import com.citu.timetrackingsystem.view.activities.HomeActivity;
import com.citu.timetrackingsystem.view.activities.UserActivity;

public class ActivityNavigator {

    public static void goToHomeActivity(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        context.startActivity(intent);
    }

    public static void goToUserActivity(Context context, User user) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra("USER", user);
        context.startActivity(intent);
    }
}
