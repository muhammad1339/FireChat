package com.prodev.firechat.users;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;

import com.prodev.firechat.data.User;
import com.prodev.firechat.data.UserRepo;

import java.util.List;

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
