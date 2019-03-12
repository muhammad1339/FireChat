package com.prodev.firechat.users;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.prodev.firechat.R;
import com.prodev.firechat.chat.ChatActivity;
import com.prodev.firechat.data.user.User;
import com.prodev.firechat.recentmessages.RecentMessagesActivity;
import com.prodev.firechat.services.NetworkReceiver;
import com.prodev.firechat.utils.FunctionUtils;
import com.prodev.firechat.utils.ViewUtils;

import java.util.List;

public class UserListActivity extends AppCompatActivity
        implements UserListContract.UserListView
        , NetworkReceiver.NetworkCallback {
    public static final String TAG = UserListActivity.class.getSimpleName();
    private RecyclerView mRecyclerViewUserList;
    private UserListPresenter mPresenter;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        //have no action bar
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        registerReceiver(new NetworkReceiver(this),new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        setupView();
        mPresenter = new UserListPresenter(this, this);
        mPresenter.getAllUsers();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intentRecentMessages = new Intent(this, RecentMessagesActivity.class);
        startActivity(intentRecentMessages);
    }

    private void setupView() {
        mRecyclerViewUserList = findViewById(R.id.recycler_view_user_list);
        mRecyclerViewUserList.setHasFixedSize(true);
        mRecyclerViewUserList.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewUserList.addItemDecoration(
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void populateUserList(List<User> userItems) {
        UserListAdapter mAdapter = new UserListAdapter(userItems, this);
        mRecyclerViewUserList.setAdapter(mAdapter);
        this.userList = userItems;
    }

    @Override
    public void onUserItemClicked(int position) {
        User user = userList.get(position);
        Log.d(TAG, "onUserItemClicked: " + user.toString());
        Intent intent = new Intent(this, ChatActivity.class);
        // pass current clicked user Id or user to chat activity
        intent.putExtra("toID", user.getUid());
        intent.putExtra("toEmail", user.getUserMail());
        intent.putExtra("toImageUrl", user.getUserImagePath());
        startActivity(intent);
    }

    @Override
    public void onNetworkConnected() {

    }

    @Override
    public void onNetworkDisconnected() {

    }
}
