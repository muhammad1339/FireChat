package com.prodev.firechat.register;

import android.content.Context;
import android.net.Uri;

import com.prodev.firechat.data.User;
import com.prodev.firechat.data.UserRepo;

public class RegisterPresenter implements RegisterContract.RegisterPresenter {

    UserRepo mRepo;

    public RegisterPresenter(Context context) {
        this.mRepo = new UserRepo();
    }

    @Override
    public void saveUser(String uid, String userMail, String password, Uri imagePath) {
        mRepo.signUpWithEmailAndPassword(new User(uid, userMail, password, ""),imagePath);
    }

}
