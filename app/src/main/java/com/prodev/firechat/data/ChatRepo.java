package com.prodev.firechat.data;

import com.google.firebase.database.FirebaseDatabase;

public class ChatRepo {
   private FirebaseDatabase mFirebaseDatabase;

    public ChatRepo() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void storeUserChat(Chat chat) {

    }
}
