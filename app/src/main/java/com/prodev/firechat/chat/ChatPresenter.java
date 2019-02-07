package com.prodev.firechat.chat;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;

import com.prodev.firechat.data.Chat;
import com.prodev.firechat.data.ChatRepo;

import java.util.List;

public class ChatPresenter implements ChatContract.ChatPresenter {
    private ChatRepo mRepo;
    private ChatContract.ChatView mChatView;
    private LifecycleOwner lifecycleOwner;

    public ChatPresenter(LifecycleOwner owner, Context context) {
        mRepo = new ChatRepo();
        mChatView = (ChatContract.ChatView) context;
        lifecycleOwner = owner;
    }

    @Override
    public void saveChatMessage(Chat chat) {
        mRepo.storeUserChat(chat);
    }

    public void getChatMessages(String fromID, String toID) {
        mRepo.getChatMessages(fromID, toID).observe(lifecycleOwner, chats -> {
            mChatView.populateListWithMessages(chats);
        });
    }
}
