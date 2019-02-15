package com.citu.timetrackingsystem.view.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.citu.timetrackingsystem.R;
import com.citu.timetrackingsystem.manager.SessionManager;
import com.citu.timetrackingsystem.model.User;
import com.citu.timetrackingsystem.util.navigators.FragmentNavigator;
import com.citu.timetrackingsystem.view.fragments.TimeLogsFragment;
import com.citu.timetrackingsystem.view.fragments.UsersFragment;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public android.support.v7.widget.Toolbar mToolbar;
    public DrawerLayout mDrawerLayout;
    public NavigationView mNavigationView;

    private UsersFragment mUsersFragment;
    private TimeLogsFragment mTimeLogsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mToolbar = findViewById(R.id.home_toolbar);
        mDrawerLayout = findViewById(R.id.home_drawer_layout);
        mNavigationView = findViewById(R.id.home_navigation_view);

        prepareToolbar();
        prepareDrawerLayout();
        prepareNavigationView();

        if (savedInstanceState == null) {
            int id = mNavigationView.getMenu().getItem(0).getItemId();
            goToFragment(id);
            mNavigationView.setCheckedItem(id);
        }
    }

    private void prepareToolbar() {
        setSupportActionBar(mToolbar);
    }

    private void prepareDrawerLayout() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void prepareNavigationView() {
        mNavigationView.setNavigationItemSelectedListener(this);

        User user = SessionManager.getInstance(this).getUser();

        mNavigationView.getMenu().clear();

        if (user.getRole().equals(User.ROLE_ADMIN))
            mNavigationView.inflateMenu(R.menu.navigation_drawer_admin_menu);
        else
            mNavigationView.inflateMenu(R.menu.navigation_drawer_normal_menu);

    }

    public void goToFragment(int id) {
        switch (id) {
            case R.id.nav_users:
                if (mUsersFragment == null) {
                    mUsersFragment = new UsersFragment();
                }
                FragmentNavigator.goToUsersFragment(this, R.id.home_frame_layout, mUsersFragment);
                break;
            case R.id.nav_time_logs:
                if (mTimeLogsFragment == null) {
                    mTimeLogsFragment = new TimeLogsFragment();
                }
                FragmentNavigator.goToTimeLogsFragment(this, R.id.home_frame_layout, mTimeLogsFragment);
                break;
            case R.id.nav_logout:
                SessionManager.getInstance(this).removeSession();
                finish();
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        goToFragment(menuItem.getItemId());
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("", "");
    }
}
