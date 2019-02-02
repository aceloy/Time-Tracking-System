package com.citu.timetrackingsystem.view.fragments;


import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
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
import android.widget.Toast;

import com.citu.timetrackingsystem.R;
import com.citu.timetrackingsystem.data.contracts.UserContract;
import com.citu.timetrackingsystem.manager.SessionManager;
import com.citu.timetrackingsystem.model.User;
import com.citu.timetrackingsystem.util.navigators.ActivityNavigator;
import com.citu.timetrackingsystem.view.list.ItemClickSupport;
import com.citu.timetrackingsystem.view.list.adapters.UserAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UsersFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    public FrameLayout mFrameLayout;
    public RecyclerView mRecyclerView;
    public TextView mTextViewNoUsers;
    public FloatingActionButton mFabButtonAdd;

    private UserAdapter mUserAdapter;

    public List<User> mUsers = new ArrayList<>();

    private LoaderManager mLoaderManager;

    public UsersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_users, container, false);
        mFrameLayout = view.findViewById(R.id.fragment_users_frameLayout);
        mRecyclerView = view.findViewById(R.id.fragment_users_recyclerView);
        mTextViewNoUsers = view.findViewById(R.id.fragment_users_textView_no_users);
        mFabButtonAdd = view.findViewById(R.id.fragment_users_fab_add);
        prepareRecyclerView();
        prepareActions();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mLoaderManager = getActivity().getSupportLoaderManager();
    }

    private void prepareRecyclerView() {
        mUserAdapter = new UserAdapter(getContext(), mUsers);
        mRecyclerView.setAdapter(mUserAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener((recyclerView, position, v) -> ActivityNavigator.goToUserActivity(getActivity(), mUsers.get(position)));

        ItemClickSupport.addTo(mRecyclerView).setOnItemLongClickListener((recyclerView, position, v) -> {
            showAlertDialogForDeleteUser(mUsers.get(position));
            return false;
        });
    }

    public void updateRecyclerView() {
        showHideRecyclerView(mUsers != null && mUsers.size() > 0);
        mUserAdapter.swapItems(mUsers);
    }

    public void showHideRecyclerView(boolean isShow) {
        mRecyclerView.setVisibility(isShow ? View.VISIBLE : View.GONE);
        mTextViewNoUsers.setVisibility(isShow ? View.GONE : View.VISIBLE);
    }

    private void showAlertDialogForDeleteUser(User user) {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setTitle("");
        alertDialog.setMessage(getString(R.string.message_delete_user) + " " + user.getName() + "?");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.action_cancel),
                (dialog, which) -> dialog.dismiss());
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.action_delete),
                (dialog, which) -> deleteUser(user));
        alertDialog.show();
    }

    private void prepareActions() {
        mFabButtonAdd.setOnClickListener(view -> ActivityNavigator.goToUserActivity(getActivity(), null));
    }

    private void getUsers() {
        if (getActivity().getSupportLoaderManager().getLoader(User.LOADER_USERS) == null)
            mLoaderManager.initLoader(User.LOADER_USERS, null, this);
        else
            mLoaderManager.restartLoader(User.LOADER_USERS, null, this);
    }

    private void deleteUser(User user) {
        int deleted = getActivity()
                .getContentResolver()
                .delete(ContentUris.withAppendedId(UserContract.UserEntry.CONTENT_URI, user.getId()), null, null);

        String message = getString(deleted > 0 ? R.string.message_user_deleted : R.string.message_delete_user_failed);
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        User user = SessionManager.getInstance(getContext()).getUser();
        Uri uri = null;
        String selection = null;
        String[] selectionArgs = null;
        switch (i) {
            case User.LOADER_USERS:
                uri = UserContract.UserEntry.CONTENT_URI;
                if (user.getRole() == User.ROLE_ADMIN) {
                    selection = UserContract.UserEntry.COLUMN_ID_NUMBER + " != ?";
                    selectionArgs = new String[] {String.valueOf(user.getIdNumber())};
                } else {
                    selection = UserContract.UserEntry.COLUMN_ID_NUMBER + " != ?" + " AND " + UserContract.UserEntry.COLUMN_ROLE + " != ?";
                    selectionArgs = new String[] {String.valueOf(user.getIdNumber()), User.ROLE_ADMIN};
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
            case User.LOADER_USERS:
                List<User> users = new ArrayList<>();
                while (cursor.moveToNext()) {
                    users.add(new User(cursor));
                }

                mUsers = users;

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
        getUsers();
    }

}
