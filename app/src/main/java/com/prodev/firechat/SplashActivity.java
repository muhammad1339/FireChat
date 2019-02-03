package com.prodev.firechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.prodev.firechat.recentmessages.RecentMessagesActivity;
import com.prodev.firechat.register.RegisterActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
        if (FirebaseAuth.getInstance().getUid() == null) {
            Intent intentRegister = new Intent(this, RegisterActivity.class);
            intentRegister.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentRegister);
        } else {
            Intent intentRecentMessages = new Intent(this, RecentMessagesActivity.class);
            intentRecentMessages.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentRecentMessages);
        }
    }


}
