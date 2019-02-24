package com.prodev.firechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.prodev.firechat.data.User;
import com.prodev.firechat.recentmessages.RecentMessagesActivity;
import com.prodev.firechat.register.RegisterActivity;
import com.prodev.firechat.services.CheckLoginService;

public class SplashActivity extends AppCompatActivity {
    public static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashScreenTheme);
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
        User userObject = PrefManager.getUserObject(SplashActivity.this, Constant.USER_NODE);
        if (userObject == null) {
            Intent intentRegister = new Intent(SplashActivity.this, RegisterActivity.class);
            intentRegister.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentRegister);
        } else {
            Intent intentRecentMessages = new Intent(SplashActivity.this, RecentMessagesActivity.class);
            intentRecentMessages.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentRecentMessages);
        }
    }

}
