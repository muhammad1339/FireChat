package com.prodev.firechat.register;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.prodev.firechat.R;
import com.prodev.firechat.Utils;
import com.prodev.firechat.data.User;
import com.prodev.firechat.data.UserRepo;
import com.prodev.firechat.recentmessages.RecentMessagesPresenter;

import java.util.List;

public class RegisterActivity extends AppCompatActivity
        implements RegisterContract.ChangeViewCallback {
    public static final String TAG = RegisterActivity.class.getSimpleName();
    private SignUpFragment mSignUpFragment;
    private LoginFragment mLoginFragment;
    private FragmentManager mFragmentManager;
    private final int layoutHolder = R.id.fragment_holder_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFragmentManager = getSupportFragmentManager();
        mSignUpFragment = new SignUpFragment();
        mLoginFragment = new LoginFragment();
        Utils.replaceFragment(mFragmentManager, mSignUpFragment, layoutHolder);
    }

    @Override
    public void onCreateNewAccount() {
        Utils.replaceFragment(mFragmentManager, mSignUpFragment, layoutHolder);
    }

    @Override
    public void onHaveAccountClicked() {
        Utils.replaceFragment(mFragmentManager, mLoginFragment, layoutHolder);
    }


}
