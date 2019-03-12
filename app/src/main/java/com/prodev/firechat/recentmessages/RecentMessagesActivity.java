package com.prodev.firechat.recentmessages;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.prodev.firechat.R;
import com.prodev.firechat.services.NetworkReceiver;
import com.prodev.firechat.utils.FunctionUtils;
import com.prodev.firechat.utils.ViewUtils;
import com.prodev.firechat.users.UserListActivity;

public class RecentMessagesActivity extends AppCompatActivity
        implements RecentMessagesContract.ChangeViewCallback
        , NetworkReceiver.NetworkCallback {

    private RecentMessagesFragment mRecentMessagesFragment;
    private NoMessagesFragment mNoMessagesFragment;
    private FragmentManager mFragmentManager;
    private final int layoutHolder = R.id.fragment_holder_recent_messages;
    private FloatingActionButton fabSendNewMessage;
    public static final String TAG = RecentMessagesActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_messages);
        registerReceiver(new NetworkReceiver(this), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        fabSendNewMessage = findViewById(R.id.fab_new_message);
        mFragmentManager = getSupportFragmentManager();
        mNoMessagesFragment = new NoMessagesFragment();
        mRecentMessagesFragment = new RecentMessagesFragment();
        //check if there is no messages
//        ViewUtils.replaceFragment(mFragmentManager, mRecentMessagesFragment, layoutHolder);
        // else replace with recent messages list
        ViewUtils.replaceFragment(mFragmentManager, mRecentMessagesFragment, layoutHolder);
        fabSendNewMessage.setOnClickListener(view -> {
            Intent intent = new Intent(this, UserListActivity.class);
            startActivity(intent);
        });
    }


    @Override
    public void onStartNewMessage() {
        //load list of users
        Intent intent = new Intent(this, UserListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onNetworkConnected() {

    }

    @Override
    public void onNetworkDisconnected() {

    }
}
