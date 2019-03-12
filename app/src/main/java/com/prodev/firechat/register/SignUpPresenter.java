package com.prodev.firechat.register;

import android.net.Uri;

import com.prodev.firechat.data.user.User;
import com.prodev.firechat.data.user.UserRepo;
import com.prodev.firechat.data.user.UserRepoCallback;

public class SignUpPresenter
        implements RegisterContract.SignUpPresenter
        , UserRepoCallback.UserRepoSignUpCallback {

    private UserRepo mRepo;
    private RegisterContract.RegisterViewSignUp mRegisterViewSignUp;

    public SignUpPresenter(SignUpFragment fragment) {
        mRegisterViewSignUp = fragment;
        this.mRepo = new UserRepo(this, fragment.getContext());
    }

    @Override
    public void onSignUpSuccess() {
        mRegisterViewSignUp.onSignUpComplete();
    }

    @Override
    public void onSignUpFailure(String message) {
        mRegisterViewSignUp.onSignUpFailure(message);
    }

    @Override
    public void onStartUserSignUp() {
        mRegisterViewSignUp.onStartLoading();
    }

    @Override
    public void onEndUserSignUp() {
        mRegisterViewSignUp.onEndLoading();
    }

    @Override
    public void saveUser(String uid, String userMail, String password, Uri imagePath) {
        mRepo.signUpWithEmailAndPassword(new User(uid, userMail, password, ""), imagePath);
    }
}
