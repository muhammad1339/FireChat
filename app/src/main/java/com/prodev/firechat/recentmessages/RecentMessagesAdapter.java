package com.prodev.firechat.recentmessages;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.prodev.firechat.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecentMessagesAdapter extends RecyclerView.Adapter<RecentMessagesAdapter.RecentViewHolder> {

    @NonNull
    @Override
    public RecentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecentViewHolder recentViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
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

        public void setUserRecentImage(String userRecentImage) {
            Glide.with(itemView).load(Uri.parse(userRecentImage)).into(this.userRecentImage);
        }

        public void setUserRecentName(String userRecentName) {
            this.userRecentName.setText(userRecentName);
        }

        public void setUserRecentMsg(String userRecentMsg) {
            this.userRecentMsg.setText(userRecentMsg);
        }
    }
}
