package com.prodev.firechat.data;

public interface UserRepoCallback {

    interface UserRepoLoginCallback {
        void onLoginSuccess();
    }

    interface UserRepoSignUpCallback {
        void onSignUpSuccess();
    }
}
