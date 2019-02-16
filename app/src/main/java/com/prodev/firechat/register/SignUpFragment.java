package com.prodev.firechat.register;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.prodev.firechat.R;
import com.prodev.firechat.Utils;
import com.prodev.firechat.recentmessages.RecentMessagesActivity;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class SignUpFragment extends Fragment implements RegisterContract.RegisterViewSignUp {
    public static final int CAMERA_ID = 0;
    public static final int GALLERY_ID = 1;
    public static final String TAG = SignUpFragment.class.getSimpleName();
    private SignUpPresenter mSignUpPresenter;
    private Context mContext;
    private TextInputEditText etSignUpEmail;
    private TextInputEditText etSignUpPassword;
    private TextInputEditText etSignUpPasswordConfirm;
    private Button btnSignUp;
    private TextView txtHaveAccount;
    private CircleImageView imgSelectProfile;
    private Uri mUri;
    private RegisterContract.ChangeViewCallback mChangeViewCallback;
    private View loadingView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mSignUpPresenter = new SignUpPresenter(this);
        mChangeViewCallback = (RegisterContract.ChangeViewCallback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mChangeViewCallback = null;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        configureView(view);

        btnSignUp.setOnClickListener(view1 -> collectUserData());
        imgSelectProfile.setOnClickListener(view12 -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, GALLERY_ID);
        });
        txtHaveAccount.setOnClickListener(view13 -> mChangeViewCallback.onHaveAccountClicked());
        return view;
    }

    private void collectUserData() {
        String userMail = etSignUpEmail.getText().toString();
        String userPassword = etSignUpPassword.getText().toString();
        String userPasswordConfirm = etSignUpPasswordConfirm.getText().toString();
        if (TextUtils.isEmpty(userMail)) {
            String msg = "User Email Can't be Empty";
            etSignUpEmail.setError(msg);
            return;
        }
        if (TextUtils.isEmpty(userPassword)) {
            String msg = "User Password Can't be Empty";
            etSignUpPassword.setError(msg);
            return;
        }
        if (TextUtils.isEmpty(userPasswordConfirm)) {
            String msg = "userPasswordConfirm Can't be Empty";
            etSignUpPasswordConfirm.setError(msg);
            return;
        }
        if (!userPassword.equals(userPasswordConfirm)) {
            String msg = "userPassword doesn't match";
            etSignUpPasswordConfirm.setError(msg);
            return;
        }
        if (userPassword.length() < 6) {
            String msg = "Password should be 6 characters at least";
            etSignUpPassword.setError(msg);
            return;
        }
        if (mUri == null) {
            String msg = "Please ,Select Image Profile";
            Utils.showToast(mContext, msg);
            return;
        }
        mSignUpPresenter.saveUser("", userMail
                , userPassword, mUri);
    }

    private void configureView(View view) {
        loadingView = view.findViewById(R.id.sign_up_loading_layout);
        imgSelectProfile = view.findViewById(R.id.select_profile_image);
        etSignUpEmail = view.findViewById(R.id.edit_text_sign_up_email);
        etSignUpPassword = view.findViewById(R.id.edit_text_sign_up_password);
        etSignUpPasswordConfirm = view.findViewById(R.id.edit_text_sign_up_password_confirm);
        btnSignUp = view.findViewById(R.id.button_sign_up);
        txtHaveAccount = view.findViewById(R.id.text_view_sign_up_have_account);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_ID && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            if (uri != null) {
                Log.d(TAG, "onActivityResult: " + data.getData().toString());
                Glide.with(mContext).load(uri).into(imgSelectProfile);
                mUri = uri;
            }
        }

    }

    @Override
    public void onSignUpComplete() {
        Intent intent = new Intent(mContext, RecentMessagesActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onSignUpFailure(String message) {
        etSignUpEmail.setError(message);
        loadingView.setVisibility(View.GONE);
    }

    @Override
    public void onStartLoading() {
        loadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onEndLoading() {
        loadingView.setVisibility(View.GONE);
    }
}
