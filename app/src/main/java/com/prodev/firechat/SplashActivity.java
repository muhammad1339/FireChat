package com.prodev.firechat;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.prodev.firechat.data.Constant;
import com.prodev.firechat.data.PrefManager;
import com.prodev.firechat.data.user.User;
import com.prodev.firechat.recentmessages.RecentMessagesActivity;
import com.prodev.firechat.register.RegisterActivity;
import com.prodev.firechat.services.NetworkReceiver;
import com.prodev.firechat.utils.FunctionUtils;

public class SplashActivity extends AppCompatActivity implements NetworkReceiver.NetworkCallback {
    public static final String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashScreenTheme);
        super.onCreate(savedInstanceState);
        registerReceiver(new NetworkReceiver(this), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

    }

    @Override
    public void onNetworkConnected() {
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

    @Override
    public void onNetworkDisconnected() {

    }
}
