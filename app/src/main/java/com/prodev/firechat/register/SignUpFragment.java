package com.prodev.firechat.register;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.prodev.firechat.R;
import com.prodev.firechat.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class SignUpFragment extends Fragment implements RegisterContract.RegisterView {
    public static final int CAMERA_ID = 0;
    public static final int GALLERY_ID = 1;
    public static final String TAG = SignUpFragment.class.getSimpleName();
    private RegisterPresenter mRegisterPresenter;
    private Context mContext;
    private TextInputEditText etSignUpEmail;
    private TextInputEditText etSignUpPassword;
    private TextInputEditText etSignUpPasswordConfirm;
    private Button btnSignUp;
    private TextView txtHaveAccount;
    private CircleImageView imgSelectProfile;
    private Uri mUri;
    private RegisterContract.ChangeViewCallback mChangeViewCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mRegisterPresenter = new RegisterPresenter(context);
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

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectUserData();
            }
        });
        imgSelectProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_ID);
            }
        });
        txtHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChangeViewCallback.onHaveAccountClicked();
            }
        });
        return view;
    }

    private void collectUserData() {
        String userMail = etSignUpEmail.getText().toString();
        String userPassword = etSignUpPassword.getText().toString();
        String userPasswordConfirm = etSignUpPasswordConfirm.getText().toString();
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
        if (TextUtils.isEmpty(userPasswordConfirm)) {
            String msg = "userPasswordConfirm Can't be Empty";
            Utils.showToast(mContext, msg);
            return;
        }
        if (!userPassword.equals(userPasswordConfirm)) {
            String msg = "userPassword doesn't match";
            Utils.showToast(mContext, msg);
            return;
        }
        if (mUri == null) {
            String msg = "Please ,Select Image Profile";
            Utils.showToast(mContext, msg);
            return;
        }
        mRegisterPresenter.saveUser("", userMail
                , userPassword, mUri);
    }

    private void configureView(View view) {
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
}
