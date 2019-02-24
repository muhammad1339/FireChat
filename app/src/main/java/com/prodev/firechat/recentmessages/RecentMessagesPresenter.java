package com.prodev.firechat.recentmessages;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.prodev.firechat.data.Chat;
import com.prodev.firechat.data.ChatRepo;
import com.prodev.firechat.data.ChatRepoCallback;
import com.prodev.firechat.data.User;
import com.prodev.firechat.data.UserRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.prodev.firechat.data.ChatRepo.TAG;


public class RecentMessagesPresenter implements ChatRepoCallback {
    private ChatRepo mChatRepo;
    private UserRepo mUserRepo;
    private LifecycleOwner lifecycleOwner;
    private RecentMessagesContract.RecentMessagesView mRecentMessagesView;
    private Map<Chat, User> chatUserMap;

    public RecentMessagesPresenter(LifecycleOwner owner, RecentMessagesFragment recentMessagesFragment) {
        mChatRepo = new ChatRepo(this);
        mUserRepo = new UserRepo();
        lifecycleOwner = owner;
        mRecentMessagesView = recentMessagesFragment;
    }

    public void getRecentMessagesForCurrentUser(String fromID) {
        chatUserMap = new HashMap<>();
        mChatRepo.getRecentMessagesForCurrentUser(fromID).observe(lifecycleOwner, this::putUserForCurrentRecentMessage);
    }

    private void putUserForCurrentRecentMessage(List<Chat> chatList) {
        String uid = FirebaseAuth.getInstance().getUid();
        for (Chat chat : chatList) {
            mUserRepo.getUserWithId(chat.getFromID().equals(uid) ? chat.getToID() : chat.getFromID())
                    .observe(lifecycleOwner, user -> {
                chatUserMap.put(chat, user);
                mRecentMessagesView.populateRecentMessages(chatUserMap, chatList);
            });
        }
    }

    @Override
    public void onStartLoadingChat() {
        mRecentMessagesView.onStartLoadingChat();
    }

    @Override
    public void onFinishLoadingChat() {
        mRecentMessagesView.onFinishLoadingChat();
    }
}
