package com.prodev.firechat.users;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.prodev.firechat.R;
import com.prodev.firechat.data.User;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserItemViewHolder> {
    private List<User> userList;
    private Context mContext;
    private UserListContract.UserListView mUserListView;

    public UserListAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.mContext = context;
        mUserListView = (UserListContract.UserListView) context;

    }

    @NonNull
    @Override
    public UserItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new UserItemViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.user_item_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserItemViewHolder userItemViewHolder, int i) {
        User user = userList.get(i);
        userItemViewHolder.userEmail.setText(user.getUserMail());
        Uri uri = Uri.parse(user.getUserImagePath());
        Glide.with(mContext).load(uri).into(userItemViewHolder.userProfile);
        userItemViewHolder.itemView.setOnClickListener(view -> {
            mUserListView.onUserItemClicked(i);
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class UserItemViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView userProfile;
        private TextView userEmail;

        UserItemViewHolder(@NonNull View itemView) {
            super(itemView);
            userProfile = itemView.findViewById(R.id.image_view_user_item_profile);
            userEmail = itemView.findViewById(R.id.text_view_user_mail);
        }
    }
}
