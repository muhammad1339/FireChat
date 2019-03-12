package com.prodev.firechat.recentmessages;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.prodev.firechat.R;
import com.prodev.firechat.chat.ChatActivity;
import com.prodev.firechat.data.chat.Chat;
import com.prodev.firechat.data.user.User;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.prodev.firechat.data.chat.ChatRepo.TAG;

public class RecentMessagesAdapter extends RecyclerView.Adapter<RecentMessagesAdapter.RecentViewHolder> {

    private Context mContext;
    private Map<Chat, User> recentChat;
    private List<Chat> chatList;

    public RecentMessagesAdapter(Context mContext, Map<Chat, User> recentChat, List<Chat> chatList) {
        this.mContext = mContext;
        this.recentChat = recentChat;
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public RecentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recent_message_item, viewGroup, false);
        return new RecentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentViewHolder recentViewHolder, int position) {
        Chat chat = chatList.get(position);
        User user = recentChat.get(chat);
        Log.d(TAG, "onBindViewHolder: " + recentChat.size());
        if (user != null) {
            recentViewHolder.setUserRecentMsg(chat.getMsgContent());
            recentViewHolder.setUserRecentImage(user.getUserImagePath());
            recentViewHolder.setUserRecentName(user.getUserMail());
            recentViewHolder.itemView.setOnClickListener(view -> {
                Intent intent = new Intent(mContext, ChatActivity.class);
                // pass current clicked user Id or user to chat activity
                intent.putExtra("toID", user.getUid());
                intent.putExtra("toEmail", user.getUserMail());
                intent.putExtra("toImageUrl", user.getUserImagePath());
                mContext.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    static class RecentViewHolder extends RecyclerView.ViewHolder {
        CircleImageView userRecentImage;
        TextView userRecentName;
        TextView userRecentMsg;

        public RecentViewHolder(@NonNull View itemView) {
            super(itemView);
            userRecentImage = itemView.findViewById(R.id.recent_user_image);
            userRecentName = itemView.findViewById(R.id.recent_user_name);
            userRecentMsg = itemView.findViewById(R.id.recent_user_msg);
        }

        public void setUserRecentImage(String imageURL) {
            Glide.with(itemView).load(Uri.parse(imageURL)).into(this.userRecentImage);
        }

        public void setUserRecentName(String userRecentName) {
            this.userRecentName.setText(userRecentName);
        }

        public void setUserRecentMsg(String userRecentMsg) {
            this.userRecentMsg.setText(userRecentMsg);
        }
    }
}
