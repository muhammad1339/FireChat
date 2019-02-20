package com.prodev.firechat.recentmessages;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.prodev.firechat.R;
import com.prodev.firechat.data.Chat;
import com.prodev.firechat.data.User;

import java.util.List;
import java.util.Map;

import static com.prodev.firechat.data.ChatRepo.TAG;

public class RecentMessagesFragment extends Fragment implements RecentMessagesContract.RecentMessagesView {
    private Context mContext;
    private RecyclerView recyclerViewRecentMessages;
    private RecentMessagesPresenter mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mPresenter = new RecentMessagesPresenter(this, this);
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
        String uid = FirebaseAuth.getInstance().getUid();
        mPresenter.getRecentMessagesForCurrentUser(uid);
        return view;
    }

    private void configureViews(View view) {
        recyclerViewRecentMessages = view.findViewById(R.id.recycler_view_recent_messages);
        recyclerViewRecentMessages.setHasFixedSize(true);
        recyclerViewRecentMessages.setItemAnimator(new DefaultItemAnimator());
        recyclerViewRecentMessages.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void populateRecentMessages(Map<Chat, User> chatUserMap, List<Chat> chatList) {
        Log.d(TAG, "populateRecentMessages: "+chatUserMap.size());
        RecentMessagesAdapter adapter = new RecentMessagesAdapter(mContext, chatUserMap, chatList);
        recyclerViewRecentMessages.setAdapter(adapter);
    }
}
