package com.prodev.firechat.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.prodev.firechat.R;
import com.prodev.firechat.data.Chat;
import com.prodev.firechat.data.ChatRepo;

import java.util.List;

public class ChatActivity
        extends AppCompatActivity
        implements ChatContract.ChatView {
    private EditText editTextChatContent;
    private ImageButton btnSendMsg;
    private ChatPresenter mPresenter;
    private RecyclerView recyclerViewChatList;
    private ChatAdapter chatAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        String toID = intent.getStringExtra("toID");
        String fromID = FirebaseAuth.getInstance().getUid();
        mPresenter = new ChatPresenter(this,this);
        mPresenter.getChatMessages(fromID,toID);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        configureView();
        Intent intent = getIntent();
        String toID = intent.getStringExtra("toID");
        String toEmail = intent.getStringExtra("toEmail");
        String fromID = FirebaseAuth.getInstance().getUid();
        long timeStamp = System.currentTimeMillis();
        btnSendMsg.setOnClickListener(view -> {
            String msg = editTextChatContent.getText().toString();
            Chat chat = new Chat("", fromID, toID, msg, timeStamp);
            editTextChatContent.setText("");
            mPresenter.saveChatMessage(chat);
            if (chatAdapter!=null){
                recyclerViewChatList.scrollToPosition(chatAdapter.getItemCount());
            }
        });

    }

    private void configureView() {
        editTextChatContent = findViewById(R.id.editText_message_content);
        btnSendMsg = findViewById(R.id.imageButton_send_message);
        recyclerViewChatList = findViewById(R.id.recycler_view_chat_list);
        recyclerViewChatList.setHasFixedSize(true);
        recyclerViewChatList.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void populateListWithMessages(List<Chat> chatList) {
        chatAdapter = new ChatAdapter(this,chatList);
        recyclerViewChatList.setAdapter(chatAdapter);
    }
}
