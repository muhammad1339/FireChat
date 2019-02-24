package com.prodev.firechat.chat;

import com.prodev.firechat.data.Chat;

import java.util.List;

public interface ChatContract {
    interface ChatView {
        void populateListWithMessages(List<Chat> chatList);
        void onStartLoadingChat();
        void onFinishLoadingChat();
    }

    interface ChatPresenter {
        void saveChatMessage(Chat chat);
    }
}
