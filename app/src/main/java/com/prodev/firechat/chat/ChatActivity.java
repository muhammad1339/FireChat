package com.prodev.firechat.chat;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.prodev.firechat.data.Constant;
import com.prodev.firechat.data.PrefManager;
import com.prodev.firechat.R;
import com.prodev.firechat.services.NetworkReceiver;
import com.prodev.firechat.utils.FunctionUtils;
import com.prodev.firechat.utils.ViewUtils;
import com.prodev.firechat.data.chat.Chat;
import com.prodev.firechat.recentmessages.RecentMessagesActivity;

import java.util.Date;
import java.util.List;

public class ChatActivity
        extends AppCompatActivity
        implements ChatContract.ChatView
        , NetworkReceiver.NetworkCallback {
    public static final String TAG = ChatActivity.class.getSimpleName();
    private EditText editTextChatContent;
    private ImageButton btnSendMsg;
    private ChatPresenter mPresenter;
    private RecyclerView recyclerViewChatList;
    private View chatLoadingLayout;
    private ChatAdapter chatAdapter;
    private String toID;
    private String fromID;
    private String toImageUrl;
    private String toEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        registerReceiver(new NetworkReceiver(this), new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        configureView();
        receiveIntentData();
        Toolbar toolbar = findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        if (toEmail != null && supportActionBar != null) {
            supportActionBar.setTitle(toEmail);
        }
        long timeStamp = System.currentTimeMillis();
        Date date = new Date(timeStamp);
        Log.d(TAG, "onCreate: " + date.toString());
        btnSendMsg.setOnClickListener(view -> {
            String msg = editTextChatContent.getText().toString();
            if (TextUtils.isEmpty(msg)) {
                ViewUtils.showToast(this, "Message is empty");
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intentRecentMessages = new Intent(this, RecentMessagesActivity.class);
        startActivity(intentRecentMessages);
    }

    private void configureView() {
        chatLoadingLayout = findViewById(R.id.chat_loading_layout);
        editTextChatContent = findViewById(R.id.editText_message_content);
        btnSendMsg = findViewById(R.id.imageButton_send_message);
        recyclerViewChatList = findViewById(R.id.recycler_view_chat_list);
        recyclerViewChatList.setHasFixedSize(true);
        recyclerViewChatList.setItemAnimator(new DefaultItemAnimator());
    }

    private void receiveIntentData() {
        Intent intent = getIntent();
        toID = intent.getStringExtra("toID");
        toImageUrl = intent.getStringExtra("toImageUrl");
        fromID = FirebaseAuth.getInstance().getUid();
        mPresenter = new ChatPresenter(this, this);
        mPresenter.getChatMessages(fromID, toID);
        toEmail = intent.getStringExtra("toEmail");
    }

    @Override
    public void populateListWithMessages(List<Chat> chatList) {
        chatAdapter = new ChatAdapter(this, chatList);
        chatAdapter.setToImageUrl(toImageUrl);
        chatAdapter.setFromImageUrl(PrefManager.getUserObject(this, Constant.USER_NODE).getUserImagePath());
        recyclerViewChatList.setAdapter(chatAdapter);
        recyclerViewChatList.scrollToPosition(chatAdapter.getItemCount() - 1);
    }

    @Override
    public void onStartLoadingChat() {
        chatLoadingLayout.setVisibility(View.VISIBLE);
        recyclerViewChatList.setVisibility(View.GONE);
        Log.d(TAG, "onStartLoadingChat: ");
    }

    @Override
    public void onFinishLoadingChat() {
        Log.d(TAG, "onFinishLoadingChat: ");
        recyclerViewChatList.setVisibility(View.VISIBLE);
        chatLoadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void onNetworkConnected() {
        TextView chatNoNetworkLayout = findViewById(R.id.chat_no_network_layout);
        ViewUtils.showOnlineState(this,chatNoNetworkLayout);
    }

    @Override
    public void onNetworkDisconnected() {
        TextView chatNoNetworkLayout = findViewById(R.id.chat_no_network_layout);
        ViewUtils.showOnlineState(this,chatNoNetworkLayout);
    }
}
