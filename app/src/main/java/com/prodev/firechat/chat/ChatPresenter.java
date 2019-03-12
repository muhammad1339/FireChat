package com.prodev.firechat.chat;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Context;

import com.prodev.firechat.data.chat.Chat;
import com.prodev.firechat.data.chat.ChatRepo;
import com.prodev.firechat.data.chat.ChatRepoCallback;

public class ChatPresenter implements ChatContract.ChatPresenter, ChatRepoCallback {
    private ChatRepo mRepo;
    private ChatContract.ChatView mChatView;
    private LifecycleOwner lifecycleOwner;

    public ChatPresenter(LifecycleOwner owner, Context context) {
        mRepo = new ChatRepo(this);
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

    @Override
    public void onStartLoadingChat() {
        mChatView.onStartLoadingChat();
    }

    @Override
    public void onFinishLoadingChat() {
        mChatView.onFinishLoadingChat();
    }
}
