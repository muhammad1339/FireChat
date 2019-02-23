package com.prodev.firechat.data;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.prodev.firechat.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatRepo {
    private FirebaseDatabase mFirebaseDatabase;
    public static final String TAG = ChatRepo.class.getSimpleName();

    public ChatRepo() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
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
            Log.d(TAG, "storeUserChat: ");
        }).addOnFailureListener(Throwable::printStackTrace);
        toRef.setValue(chat).addOnSuccessListener(aVoid -> {
            Log.d(TAG, "storeUserChat: ");
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
        final MutableLiveData<List<Chat>> listMutableLiveData = new MutableLiveData<>();
        final DatabaseReference reference = mFirebaseDatabase.getReference(Constant.CHAT_NODE);
        reference.child(fromId).child(toId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Chat> chatList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "\n" + snapshot.getValue(Chat.class).toString());
                    Log.d(TAG, "\n" + snapshot.getValue().toString());
                    chatList.add(snapshot.getValue(Chat.class));
                }
                listMutableLiveData.postValue(chatList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return listMutableLiveData;
    }

    public LiveData<List<Chat>> getRecentMessagesForCurrentUser(String fromId) {
        final MutableLiveData<List<Chat>> listMutableLiveData = new MutableLiveData<>();
        DatabaseReference reference = mFirebaseDatabase.getReference(Constant.RECENT_MESSAGE_NODE)
                .child(fromId);
        Log.d(TAG, "getRecentMessagesForCurrentUser: "+fromId);
        reference.orderByChild("timeStamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Chat> chatList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "\n" + snapshot.getValue().toString());
                    chatList.add(snapshot.getValue(Chat.class));
                }
                Collections.reverse(chatList);
                listMutableLiveData.postValue(chatList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return listMutableLiveData;
    }
}
