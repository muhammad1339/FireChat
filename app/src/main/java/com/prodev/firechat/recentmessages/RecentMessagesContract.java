package com.prodev.firechat.recentmessages;

public interface RecentMessagesContract {
    public interface RecentMessagesView {
    }

    public interface RecentMessagesPresenter {
        void fetchRecentMessages();
    }
    public interface ChangeViewCallback{
        void onStartNewMessage();
    }
}
