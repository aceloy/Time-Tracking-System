package com.citu.timetrackingsystem.view.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.citu.timetrackingsystem.R;
import com.citu.timetrackingsystem.data.contracts.UserContract;
import com.citu.timetrackingsystem.model.User;

public class UserActivity extends AppCompatActivity {

    public EditText
            mEditTextIDNumber,
            mEditTextPassword,
            mEditTextName,
            mEditTextAge,
            mEditTextGender,
            mEditTextAddress;

    public User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        mEditTextIDNumber = findViewById(R.id.activity_user_editText_id_number);
        mEditTextPassword = findViewById(R.id.activity_user_editText_password);
        mEditTextName = findViewById(R.id.activity_user_editText_name);
        mEditTextAge = findViewById(R.id.activity_user_editText_age);
        mEditTextGender = findViewById(R.id.activity_user_editText_gender);
        mEditTextAddress = findViewById(R.id.activity_user_editText_address);

        receivedIntentData();
        setValues();
    }

    private void receivedIntentData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null)
            return;

        mUser = (User) bundle.get("USER");
    }

    private void setValues() {
        boolean isUserExist = mUser != null;

        getSupportActionBar().setTitle(getString(isUserExist ? R.string.action_update : R.string.action_add));

        mEditTextIDNumber.setEnabled(!isUserExist);
        mEditTextPassword.setEnabled(!isUserExist);
        mEditTextPassword.setTransformationMethod(isUserExist ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());

        if (!isUserExist) {
            return;
        }
        mEditTextIDNumber.setText(String.valueOf(mUser.getIdNumber()));
        mEditTextPassword.setText(mUser.getPassword());
        mEditTextName.setText(mUser.getName());
        mEditTextAge.setText(String.valueOf(mUser.getAge()));
        mEditTextGender.setText(mUser.getGender());
        mEditTextAddress.setText(mUser.getAddress());
    }

    private User getUser() {
        int idNumber = Integer.parseInt(mEditTextIDNumber.getText().toString().trim());
        String password = mEditTextPassword.getText().toString().trim();
        String name = mEditTextName.getText().toString().trim();
        int age = Integer.parseInt(mEditTextAge.getText().toString().trim());
        String gender = mEditTextGender.getText().toString().trim();
        String address = mEditTextAddress.getText().toString().trim();
        return new User(idNumber, password, name, age, gender, address, User.ROLE_NORMAL);
    }

    private void addUser() {
        if (User.getUserByIDNumber(this, getUser().getIdNumber()) != null) {
            Toast.makeText(this, getString(R.string.message_user_already_exist), Toast.LENGTH_SHORT).show();
            return;
        }
        Uri uri = getContentResolver().insert(UserContract.UserEntry.CONTENT_URI, getUser().getContentValues(true));
        if (uri != null) {
            Toast.makeText(this, getString(R.string.message_user_added), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, getString(R.string.message_add_user_failed), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUser() {
        int update = getContentResolver().update(UserContract.UserEntry.CONTENT_URI, getUser().getContentValues(true), null, null);
        if (update > 0) {
            Toast.makeText(this, getString(R.string.message_user_updated), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, getString(R.string.message_update_user_failed), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_save:
                if (mUser == null)
                    addUser();
                else
                    updateUser();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
