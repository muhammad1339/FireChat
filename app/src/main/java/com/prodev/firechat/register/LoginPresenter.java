package com.prodev.firechat.register;

import android.net.Uri;
import android.support.v4.app.FragmentActivity;

import com.prodev.firechat.data.User;
import com.prodev.firechat.data.UserRepo;
import com.prodev.firechat.data.UserRepoCallback;

public class LoginPresenter
        implements RegisterContract.LoginPresenter
        , UserRepoCallback.UserRepoLoginCallback {

    private UserRepo mRepo;
    private RegisterContract.RegisterViewLogin mRegisterViewLogin;

    public LoginPresenter(LoginFragment fragment) {
        mRegisterViewLogin = fragment;
        this.mRepo = new UserRepo(this, fragment.getContext());
    }

    @Override
    public void onLoginSuccess() {
        mRegisterViewLogin.onLoginComplete();
    }

    @Override
    public void onLoginFailure(String message) {
        mRegisterViewLogin.onSignUpFailure(message);
    }

    @Override
    public void onStartLoading() {
        mRegisterViewLogin.onStartLoading();
    }

    @Override
    public void onEndLoading() {
        mRegisterViewLogin.onEndLoading();
    }

    @Override
    public void userLogin(String userMail, String password) {
        mRepo.loginUserWithEmailAndPassword(new User("", userMail, password, ""));
    }
}
