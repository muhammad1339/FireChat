package com.prodev.firechat.recentmessages;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.prodev.firechat.R;

public class NoMessagesFragment extends Fragment {
    private RecentMessagesContract.ChangeViewCallback mChangeViewCallback;
    private Button buttonStartNewMessage;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mChangeViewCallback = (RecentMessagesContract.ChangeViewCallback) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mChangeViewCallback = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_no_messages, container, false);
        configureViews(view);
        buttonStartNewMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mChangeViewCallback.onStartNewMessage();
            }
        });
        return view;
    }

    private void configureViews(View view) {
        buttonStartNewMessage = view.findViewById(R.id.btn_start_new_message);
    }

}
