package com.prodev.firechat.register;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.prodev.firechat.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_holder_register,new SignUpFragment())
                .commit();
    }
}
