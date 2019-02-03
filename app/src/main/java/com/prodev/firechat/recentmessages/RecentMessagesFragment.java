package com.prodev.firechat.recentmessages;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prodev.firechat.R;

public class RecentMessagesFragment extends Fragment {
    private Context mContext;
    private RecyclerView recyclerViewRecentMessages;

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
        View view = inflater.inflate(R.layout.fragment_recent_messages, container, false);
        configureViews(view);

        return view;
    }

    private void configureViews(View view) {
        recyclerViewRecentMessages = view.findViewById(R.id.recycler_view_recent_messages);
    }

}
