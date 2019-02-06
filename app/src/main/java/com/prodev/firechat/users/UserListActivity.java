package com.prodev.firechat.users;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;

import com.prodev.firechat.R;
import com.prodev.firechat.chat.ChatActivity;
import com.prodev.firechat.data.User;

import java.util.List;

public class UserListActivity extends AppCompatActivity implements UserListContract.UserListView {

    private RecyclerView mRecyclerViewUserList;
    private UserListPresenter mPresenter;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        setupView();
        mPresenter = new UserListPresenter(this, this);
        mPresenter.getAllUsers();
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
        Intent intent = new Intent(this, ChatActivity.class);
        // pass current clicked user Id or user to chat activity
        intent.putExtra("userID", user.getUid());
        startActivity(intent);
    }
}
