package com.prodev.firechat.register;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.prodev.firechat.R;


public class LoginFragment extends Fragment {
    private Context mContext;
    private TextInputEditText etLoginEmail;
    private TextInputEditText etLoginPassword;
    private Button btnLogin;
    private Button btnCreateNewAccount;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        configureView(view);
        return view;
    }

    private void configureView(View view) {
        etLoginEmail = view.findViewById(R.id.edit_text_login_email);
        etLoginPassword = view.findViewById(R.id.edit_text_login_password);
        btnLogin = view.findViewById(R.id.button_login);
        btnCreateNewAccount = view.findViewById(R.id.button_create_new_account);
    }
}
