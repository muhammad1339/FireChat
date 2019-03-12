package com.prodev.firechat.register;

import com.prodev.firechat.data.user.User;
import com.prodev.firechat.data.user.UserRepo;
import com.prodev.firechat.data.user.UserRepoCallback;

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
    public void onStartUserLogin() {
        mRegisterViewLogin.onStartLoading();
    }

    @Override
    public void onEndUserLogin() {
        mRegisterViewLogin.onEndLoading();
    }

    @Override
    public void userLogin(String userMail, String password) {
        mRepo.loginUserWithEmailAndPassword(new User("", userMail, password, ""));
    }
}
