package com.prodev.firechat.register;

import android.net.Uri;

import com.prodev.firechat.data.User;
import com.prodev.firechat.data.UserRepo;
import com.prodev.firechat.data.UserRepoCallback;

public class SignUpPresenter
        implements RegisterContract.SignUpPresenter
        , UserRepoCallback.UserRepoSignUpCallback {

    private UserRepo mRepo;
    private RegisterContract.RegisterViewSignUp mRegisterViewSignUp;

    public SignUpPresenter(SignUpFragment fragment) {
        mRegisterViewSignUp = fragment;
        this.mRepo = new UserRepo(this,fragment.getContext());
    }

    @Override
    public void onSignUpSuccess() {
        mRegisterViewSignUp.onSignUpComplete();
    }

    @Override
    public void saveUser(String uid, String userMail, String password, Uri imagePath) {
        mRepo.signUpWithEmailAndPassword(new User(uid, userMail, password, ""), imagePath);
    }
}
