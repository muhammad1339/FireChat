package com.prodev.firechat.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.prodev.firechat.R;
import com.prodev.firechat.Utils;
import com.prodev.firechat.recentmessages.RecentMessagesActivity;


public class LoginFragment extends Fragment implements RegisterContract.RegisterViewLogin {
    private Context mContext;
    private TextInputEditText etLoginEmail;
    private TextInputEditText etLoginPassword;
    private Button btnLogin;
    private Button btnCreateNewAccount;
    private RegisterContract.ChangeViewCallback mChangeViewCallback;
    private LoginPresenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mChangeViewCallback = (RegisterContract.ChangeViewCallback) context;
        mPresenter = new LoginPresenter(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mChangeViewCallback = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        configureView(view);
        btnLogin.setOnClickListener(view1 -> {
            collectData();
        });
        btnCreateNewAccount.setOnClickListener(view12 -> mChangeViewCallback.onCreateNewAccount());
        return view;
    }

    private void configureView(View view) {
        etLoginEmail = view.findViewById(R.id.edit_text_login_email);
        etLoginPassword = view.findViewById(R.id.edit_text_login_password);
        btnLogin = view.findViewById(R.id.button_login);
        btnCreateNewAccount = view.findViewById(R.id.button_create_new_account);
    }

    private void collectData() {
        String userMail = etLoginEmail.getText().toString();
        String userPassword = etLoginPassword.getText().toString();
        if (TextUtils.isEmpty(userMail)) {
            String msg = "User Email Can't be Empty";
            Utils.showToast(mContext, msg);
            return;
        }
        if (TextUtils.isEmpty(userPassword)) {
            String msg = "User Password Can't be Empty";
            Utils.showToast(mContext, msg);
            return;
        }

        mPresenter.userLogin(userMail, userPassword);
    }

    @Override
    public void onLoginComplete() {
        Intent intent = new Intent(mContext, RecentMessagesActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
