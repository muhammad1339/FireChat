package com.prodev.firechat.recentmessages;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.prodev.firechat.R;
import com.prodev.firechat.Utils;
import com.prodev.firechat.data.User;
import com.prodev.firechat.data.UserRepo;
import com.prodev.firechat.register.RegisterActivity;
import com.prodev.firechat.users.UserListActivity;

public class RecentMessagesActivity extends AppCompatActivity implements RecentMessagesContract.ChangeViewCallback {

    private RecentMessagesFragment mRecentMessagesFragment;
    private NoMessagesFragment mNoMessagesFragment;
    private FragmentManager mFragmentManager;
    private final int layoutHolder = R.id.fragment_holder_recent_messages;
    private FloatingActionButton fabSendNewMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_messages);
        fabSendNewMessage = findViewById(R.id.fab_new_message);
        mFragmentManager = getSupportFragmentManager();
        mNoMessagesFragment = new NoMessagesFragment();
        mRecentMessagesFragment = new RecentMessagesFragment();
        //check if there is no messages
//        Utils.replaceFragment(mFragmentManager, mRecentMessagesFragment, layoutHolder);
        // else replace with recent messages list
        Utils.replaceFragment(mFragmentManager, mRecentMessagesFragment, layoutHolder);
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

}
