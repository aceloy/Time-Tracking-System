package com.citu.timetrackingsystem.view.fragments;


import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.citu.timetrackingsystem.R;
import com.citu.timetrackingsystem.data.contracts.TimeLogContract;
import com.citu.timetrackingsystem.helper.DateHelper;
import com.citu.timetrackingsystem.manager.SessionManager;
import com.citu.timetrackingsystem.model.TimeLog;
import com.citu.timetrackingsystem.model.User;
import com.citu.timetrackingsystem.view.list.adapters.TimeLogAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimeLogsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public FrameLayout mFrameLayout;
    public RecyclerView mRecyclerView;
    public TextView mTextViewNoTimeLogs;
    public FloatingActionButton mFabButtonTimer;

    private TimeLogAdapter mTimeLogAdapter;

    public List<TimeLog> mTimeLogs = new ArrayList<>();

    private LoaderManager mLoaderManager;

    private User mUser;
    private TimeLog mOnGoingTimeLog;

    public TimeLogsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_time_logs, container, false);
        mFrameLayout = view.findViewById(R.id.fragment_timeLogs_frameLayout);
        mRecyclerView = view.findViewById(R.id.fragment_timeLogs_recyclerView);
        mTextViewNoTimeLogs = view.findViewById(R.id.fragment_timeLogs_textView_no_timeLogs);
        mFabButtonTimer = view.findViewById(R.id.fragment_timeLogs_fab_timer);
        prepareRecyclerView();
        prepareActions();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mUser = SessionManager.getInstance(getContext()).getUser();

        mLoaderManager = getActivity().getSupportLoaderManager();
    }

    private void prepareRecyclerView() {
        mTimeLogAdapter = new TimeLogAdapter(getContext(), mTimeLogs);
        mRecyclerView.setAdapter(mTimeLogAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void updateRecyclerView() {
        showHideRecyclerView(mTimeLogs != null && mTimeLogs.size() > 0);
        mTimeLogAdapter.swapItems(mTimeLogs);
        mOnGoingTimeLog = mTimeLogAdapter.getTimeLogForToday();

        setFabTimerIcon(false);
        if (mOnGoingTimeLog != null)
            setFabTimerIcon(mTimeLogAdapter.getTimeLogForToday().isTimeIn());
    }

    public void showHideRecyclerView(boolean isShow) {
        mRecyclerView.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mTextViewNoTimeLogs.setVisibility(isShow ? View.GONE : View.VISIBLE);
    }

    private void prepareActions() {
        mFabButtonTimer.setOnClickListener(view -> showTimeInTimeOutDialog());
    }

    private void showTimeInTimeOutDialog() {
        String message = getString(R.string.action_time_in);
        if (mOnGoingTimeLog != null)
            message = getString((mOnGoingTimeLog.isTimeIn()) ? R.string.action_time_out : R.string.action_time_in);

        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.action_cancel),
                (dialog, which) -> dialog.dismiss());
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.action_confirm),
                (dialog, which) -> addUpdateTimeLog());
        alertDialog.show();
    }

    private void getTimeLogs() {
        if (getActivity().getSupportLoaderManager().getLoader(TimeLog.LOADER_TIME_LOGS) == null)
            mLoaderManager.initLoader(TimeLog.LOADER_TIME_LOGS, null, this);
        else
            mLoaderManager.restartLoader(TimeLog.LOADER_TIME_LOGS, null, this);
    }

    private void setFabTimerIcon(boolean isTimeIn) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), isTimeIn ? R.drawable.baseline_timer_off_white_24 : R.drawable.baseline_timer_white_24);
        mFabButtonTimer.setImageDrawable(drawable);
    }

    private void addUpdateTimeLog() {
        TimeLog newTimeLog = null;
        for (TimeLog timeLog : mTimeLogs) {
            if (timeLog.isToday()) {
                newTimeLog = timeLog;
                break;
            }
        }
        if (newTimeLog == null) {
            newTimeLog = new TimeLog();
            newTimeLog.setIdNumber(mUser.getIdNumber());
            newTimeLog.setTimeIn(DateHelper.getCurrentDateFormattedInISO8601());

            Uri uri = getActivity()
                    .getContentResolver()
                    .insert(TimeLogContract.TimeLogEntry.CONTENT_URI, newTimeLog.getContentValues(true));
        } else {
            newTimeLog.setTimeOut(DateHelper.getCurrentDateFormattedInISO8601());
            int updated = getActivity()
                    .getContentResolver()
                    .update(TimeLogContract.TimeLogEntry.CONTENT_URI,
                            newTimeLog.getContentValues(true),
                            TimeLogContract.TimeLogEntry.COLUMN_ID_NUMBER + " = ? ",
                            new String[]{String.valueOf(mUser.getIdNumber())}

                    );
        }

    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        User user = SessionManager.getInstance(getContext()).getUser();
        Uri uri = null;
        String selection = null;
        String[] selectionArgs = null;
        switch (i) {
            case TimeLog.LOADER_TIME_LOGS:
                uri = TimeLogContract.TimeLogEntry.CONTENT_URI;
                if (user.getRole() == User.ROLE_ADMIN) {

                } else {
                    selection = TimeLogContract.TimeLogEntry.COLUMN_ID_NUMBER + " = ?";
                    selectionArgs = new String[]{String.valueOf(user.getIdNumber())};
                }
                break;
        }
        if (uri == null)
            return null;

        return new CursorLoader(
                getActivity(),
                uri,
                null,
                selection,
                selectionArgs,
                null
        );
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null)
            return;

        switch (loader.getId()) {
            case TimeLog.LOADER_TIME_LOGS:
                List<TimeLog> timeLogs = new ArrayList<>();
                while (cursor.moveToNext()) {
                    timeLogs.add(new TimeLog(cursor));
                }

                mTimeLogs = timeLogs;

                updateRecyclerView();
                break;
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    @Override
    public void onResume() {
        super.onResume();
        getTimeLogs();
    }
}
