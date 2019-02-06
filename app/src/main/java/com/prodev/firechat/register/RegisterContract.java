package com.prodev.firechat.register;

import android.net.Uri;

import com.prodev.firechat.BasePresenter;
import com.prodev.firechat.BaseView;

public interface RegisterContract {
    interface RegisterViewSignUp {
        void onSignUpComplete();
    }

    interface RegisterViewLogin {
        void onLoginComplete();
    }

    interface SignUpPresenter {

        void saveUser(String uid, String userMail, String password, Uri imagePath);
    }

    interface LoginPresenter {

        void userLogin(String userMail, String password);
    }

    interface ChangeViewCallback {
        void onCreateNewAccount();

        void onHaveAccountClicked();
    }
}
