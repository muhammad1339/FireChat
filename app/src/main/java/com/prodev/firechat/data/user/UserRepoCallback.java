package com.prodev.firechat.data.user;

public interface UserRepoCallback {

    interface UserRepoLoginCallback {
        void onLoginSuccess();

        void onLoginFailure(String message);

        void onStartUserLogin();

        void onEndUserLogin();
    }

    interface UserRepoSignUpCallback {
        void onSignUpSuccess();

        void onSignUpFailure(String message);

        void onStartUserSignUp();

        void onEndUserSignUp();
    }
}
