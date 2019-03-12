package com.prodev.firechat.users;

import com.prodev.firechat.data.user.User;

import java.util.List;

public interface UserListContract {
    interface UserListView{
//        methods update view goes here
        void populateUserList(List<User> userItems);

        void onUserItemClicked(int position);
    }
    interface UserListPresenter{
//        methods handle model and logic goes here

    }
}
