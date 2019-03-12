package com.prodev.firechat.data.chat;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prodev.firechat.chat.ChatPresenter;
import com.prodev.firechat.data.Constant;
import com.prodev.firechat.recentmessages.RecentMessagesPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatRepo {
    private FirebaseDatabase mFirebaseDatabase;
    public static final String TAG = ChatRepo.class.getSimpleName();
    private ChatRepoCallback chatRepoCallback;

    public ChatRepo() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
    }

    public ChatRepo(ChatPresenter chatPresenter) {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        chatRepoCallback = chatPresenter;
    }

    public ChatRepo(RecentMessagesPresenter recentMessagesPresenter) {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        chatRepoCallback = recentMessagesPresenter;
    }


    public void storeUserChat(Chat chat) {
        final DatabaseReference fromRef = mFirebaseDatabase.getReference(Constant.CHAT_NODE)
                .child(chat.getFromID())
                .child(chat.getToID()).push();
        final DatabaseReference toRef = mFirebaseDatabase.getReference(Constant.CHAT_NODE)
                .child(chat.getToID())
                .child(chat.getFromID()).push();
        chat.setMsgID(fromRef.getKey());
        fromRef.setValue(chat).addOnSuccessListener(aVoid -> {
            Log.d(TAG, "storeUserChat: " + chat.toString());
        }).addOnFailureListener(Throwable::printStackTrace);
        toRef.setValue(chat).addOnSuccessListener(aVoid -> {
            Log.d(TAG, "storeUserChat: " + chat.toString());
        }).addOnFailureListener(Throwable::printStackTrace);
        storeRecentUserChat(chat);
    }

    private void storeRecentUserChat(Chat chat) {
        final DatabaseReference fromRef = mFirebaseDatabase.getReference(Constant.RECENT_MESSAGE_NODE)
                .child(chat.getFromID())
                .child(chat.getToID());
        final DatabaseReference toRef = mFirebaseDatabase.getReference(Constant.RECENT_MESSAGE_NODE)
                .child(chat.getToID())
                .child(chat.getFromID());
        chat.setMsgID(fromRef.getKey());
        fromRef.setValue(chat).addOnSuccessListener(aVoid -> {
            Log.d(TAG, "storeRecentUserChat: ");
        }).addOnFailureListener(Throwable::printStackTrace);
        toRef.setValue(chat).addOnSuccessListener(aVoid -> {
            Log.d(TAG, "storeRecentUserChat: ");
        }).addOnFailureListener(Throwable::printStackTrace);
    }

    public LiveData<List<Chat>> getChatMessages(String fromId, String toId) {
        chatRepoCallback.onStartLoadingChat();
        final MutableLiveData<List<Chat>> listMutableLiveData = new MutableLiveData<>();
        final DatabaseReference reference = mFirebaseDatabase.getReference(Constant.CHAT_NODE);
        DatabaseReference child = reference.child(fromId).child(toId);
        child.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Chat> chatList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    chatList.add(snapshot.getValue(Chat.class));
                }
                listMutableLiveData.postValue(chatList);
                chatRepoCallback.onFinishLoadingChat();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                chatRepoCallback.onFinishLoadingChat();
                Log.d(TAG, "onCancelled: " + databaseError.getMessage());
            }
        });
        return listMutableLiveData;
    }

    public LiveData<List<Chat>> getRecentMessagesForCurrentUser(String fromId) {
        chatRepoCallback.onStartLoadingChat();
        final MutableLiveData<List<Chat>> listMutableLiveData = new MutableLiveData<>();
        DatabaseReference reference = mFirebaseDatabase.getReference(Constant.RECENT_MESSAGE_NODE)
                .child(fromId);
        reference.orderByChild("timeStamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Chat> chatList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    chatList.add(snapshot.getValue(Chat.class));
                }
                Collections.reverse(chatList);
                listMutableLiveData.postValue(chatList);
                chatRepoCallback.onFinishLoadingChat();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                chatRepoCallback.onFinishLoadingChat();
                Log.d(TAG, "onCancelled: " + databaseError.getMessage());
            }
        });
        return listMutableLiveData;
    }
}
