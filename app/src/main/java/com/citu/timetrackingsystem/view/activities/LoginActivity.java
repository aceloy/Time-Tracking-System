package com.citu.timetrackingsystem.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.citu.timetrackingsystem.R;
import com.citu.timetrackingsystem.helper.DateHelper;
import com.citu.timetrackingsystem.manager.SessionManager;
import com.citu.timetrackingsystem.model.User;
import com.citu.timetrackingsystem.util.navigators.ActivityNavigator;

import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    public TextView mTextViewDate, mTextViewTime;
    public EditText mEditTextIDNumber, mEditTextPassword;
    public Button mButtonLogin;

    @Override
    protected void onStart() {
        super.onStart();
        if (SessionManager.getInstance(this).isLoggedIn())
        ActivityNavigator.goToHomeActivity(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mTextViewDate = findViewById(R.id.login_textView_date);
        mTextViewTime = findViewById(R.id.login_textView_time);
        mEditTextIDNumber = findViewById(R.id.login_editText_idNumber);
        mEditTextPassword = findViewById(R.id.login_editText_password);
        mButtonLogin = findViewById(R.id.login_button_password);

        prepareActions();

        startClock();
    }

    private void prepareActions() {
        mButtonLogin.setOnClickListener(view -> {
            String idNumber = mEditTextIDNumber.getText().toString().trim();
            String password = mEditTextPassword.getText().toString().trim();
            if (TextUtils.isEmpty(idNumber) || TextUtils.isEmpty(password)) {
                runOnUiThread(() -> Toast.makeText(LoginActivity.this, LoginActivity.this.getString(R.string.message_invalid_id_number_or_password), Toast.LENGTH_SHORT).show());
                return;
            }
            loginUser(Integer.parseInt(idNumber), password);
        });
    }

    private void startClock() {
        mTextViewDate.setText(DateHelper.getDateFormattedInMMddyy1(new Date()));
        new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTextViewTime.setText(DateHelper.getCurrentDateFormattedInHHMMss());
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        }.start();
    }

    private void loginUser(int idNumber, String password) {
        User user = User.getUserByIDNumberAndPassword(this, idNumber, password);

        if (user == null) {
            Toast.makeText(this, getString(R.string.message_invalid_id_number_or_password), Toast.LENGTH_SHORT).show();
        } else {
            SessionManager.getInstance(this).addSession(user.getIdNumber());
            ActivityNavigator.goToHomeActivity(this);
        }
    }
}
