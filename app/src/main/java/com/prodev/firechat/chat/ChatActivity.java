package com.prodev.firechat.chat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.prodev.firechat.Constant;
import com.prodev.firechat.PrefManager;
import com.prodev.firechat.R;
import com.prodev.firechat.Utils;
import com.prodev.firechat.data.Chat;

import java.util.Date;
import java.util.List;

public class ChatActivity
        extends AppCompatActivity
        implements ChatContract.ChatView {
    public static final String TAG = ChatActivity.class.getSimpleName();
    private EditText editTextChatContent;
    private ImageButton btnSendMsg;
    private ChatPresenter mPresenter;
    private RecyclerView recyclerViewChatList;
    private ChatAdapter chatAdapter;
    private String toID;
    private String fromID;
    private String toImageUrl;
    private String toEmail;

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        toID = intent.getStringExtra("toID");
        toImageUrl = intent.getStringExtra("toImageUrl");
        fromID = FirebaseAuth.getInstance().getUid();
        mPresenter = new ChatPresenter(this, this);
        mPresenter.getChatMessages(fromID, toID);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        configureView();
        Intent intent = getIntent();
        toEmail = intent.getStringExtra("toEmail");
        Toolbar toolbar = findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(toEmail);
        long timeStamp = System.currentTimeMillis();
        Date date = new Date(timeStamp);
        Log.d(TAG, "onCreate: " + date.toString());
        btnSendMsg.setOnClickListener(view -> {
            String msg = editTextChatContent.getText().toString();
            if (TextUtils.isEmpty(msg)) {
                Utils.showToast(this, "Message is empty");
            } else {
                Chat chat = new Chat("", fromID, toID, msg, timeStamp);
                editTextChatContent.setText("");
                mPresenter.saveChatMessage(chat);
                if (chatAdapter != null) {
                    Log.d(TAG, "onCreate: " + chatAdapter.getItemCount());
                    recyclerViewChatList.scrollToPosition(chatAdapter.getItemCount());
                }
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
        chatAdapter = new ChatAdapter(this, chatList);
        chatAdapter.setToImageUrl(toImageUrl);
        chatAdapter.setFromImageUrl(PrefManager.getUserObject(this, Constant.USER_NODE).getUserImagePath());
        recyclerViewChatList.setAdapter(chatAdapter);
        recyclerViewChatList.scrollToPosition(chatAdapter.getItemCount() - 1);
    }
}
