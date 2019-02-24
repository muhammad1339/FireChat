package com.prodev.firechat.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.prodev.firechat.recentmessages.RecentMessagesActivity;
import com.prodev.firechat.register.RegisterActivity;

public class CheckLoginService extends Service {
    public static final String TAG = CheckLoginService.class.getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        FirebaseApp.initializeApp(getBaseContext());
        String uid = FirebaseAuth.getInstance().getUid();
        Log.d(TAG, Thread.currentThread().getName());

        if (uid == null) {
            Intent intentRegister = new Intent(this, RegisterActivity.class);
            intentRegister.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentRegister);
        } else {
            Intent intentRecentMessages = new Intent(this, RecentMessagesActivity.class);
            intentRecentMessages.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentRecentMessages);
        }

        return START_STICKY;
    }
}
