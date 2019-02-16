package com.prodev.firechat.data;

public interface UserRepoCallback {

    interface UserRepoLoginCallback {
        void onLoginSuccess();

        void onLoginFailure(String message);

        void onStartLoading();

        void onEndLoading();
    }

    interface UserRepoSignUpCallback {
        void onSignUpSuccess();

        void onSignUpFailure(String message);

        void onStartLoading();

        void onEndLoading();
    }
}
