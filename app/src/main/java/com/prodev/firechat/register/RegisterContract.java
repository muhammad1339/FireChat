package com.prodev.firechat.register;

import android.net.Uri;

import com.prodev.firechat.BasePresenter;
import com.prodev.firechat.BaseView;

public interface RegisterContract {
    interface RegisterView {

    }

    interface RegisterPresenter {

        void saveUser(String uid, String userMail, String password, Uri imagePath);

    }

    interface ChangeViewCallback {
        void onCreateNewAccount();

        void onHaveAccountClicked();
    }
}
