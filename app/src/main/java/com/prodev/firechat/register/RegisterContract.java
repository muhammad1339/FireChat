package com.prodev.firechat.register;

import android.net.Uri;

import com.prodev.firechat.BasePresenter;
import com.prodev.firechat.BaseView;

public interface RegisterContract {
    public interface RegisterView {

        void onHaveAccountClicked();

    }

    public interface RegisterPresenter {

        void saveUser(String uid, String userMail, String password, Uri imagePath);

    }
}
