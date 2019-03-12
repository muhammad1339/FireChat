package com.prodev.firechat.register;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.prodev.firechat.R;
import com.prodev.firechat.services.NetworkReceiver;
import com.prodev.firechat.utils.FunctionUtils;
import com.prodev.firechat.utils.ViewUtils;

public class RegisterActivity extends AppCompatActivity
        implements RegisterContract.ChangeViewCallback
        , NetworkReceiver.NetworkCallback {
    public static final String TAG = RegisterActivity.class.getSimpleName();
    private SignUpFragment mSignUpFragment;
    private LoginFragment mLoginFragment;
    private FragmentManager mFragmentManager;
    private final int layoutHolder = R.id.fragment_holder_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerReceiver(new NetworkReceiver(this), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        mFragmentManager = getSupportFragmentManager();
        mSignUpFragment = new SignUpFragment();
        mLoginFragment = new LoginFragment();
        ViewUtils.replaceFragment(mFragmentManager, mSignUpFragment, layoutHolder);
    }

    @Override
    public void onCreateNewAccount() {
        ViewUtils.replaceFragment(mFragmentManager, mSignUpFragment, layoutHolder);
    }

    @Override
    public void onHaveAccountClicked() {
        ViewUtils.replaceFragment(mFragmentManager, mLoginFragment, layoutHolder);
    }


    @Override
    public void onNetworkConnected() {

    }

    @Override
    public void onNetworkDisconnected() {

    }
}
