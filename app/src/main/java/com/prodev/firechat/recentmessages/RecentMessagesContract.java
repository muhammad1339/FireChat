package com.prodev.firechat.recentmessages;

import com.prodev.firechat.data.Chat;
import com.prodev.firechat.data.User;

import java.util.List;
import java.util.Map;

public interface RecentMessagesContract {
    interface RecentMessagesView {
        void populateRecentMessages(Map<Chat, User> chatUserMap, List<Chat> chatList);
        void onStartLoadingChat();
        void onFinishLoadingChat();
    }

    interface RecentMessagesPresenter {
    }

    interface ChangeViewCallback {
        void onStartNewMessage();
    }
}
