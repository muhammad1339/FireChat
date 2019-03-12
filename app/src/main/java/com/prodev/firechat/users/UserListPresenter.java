package com.prodev.firechat.users;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;

import com.prodev.firechat.data.user.UserRepo;

public class UserListPresenter implements UserListContract.UserListPresenter {
    private UserRepo mRepo;
    private LifecycleOwner owner;
    private Context mContext;
    private UserListContract.UserListView mUserListView;

    public UserListPresenter(LifecycleOwner lifecycleOwner, Context context) {
        this.mRepo = new UserRepo();
        owner = lifecycleOwner;
        mContext = context;
        mUserListView = (UserListContract.UserListView) context;
    }

    public void getAllUsers() {
        mRepo.getAllUsers().observe(owner, users -> {
            mUserListView.populateUserList(users);
        });
    }
}
