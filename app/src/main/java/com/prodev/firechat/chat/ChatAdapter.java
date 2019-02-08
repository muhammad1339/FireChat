package com.prodev.firechat.chat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.prodev.firechat.R;
import com.prodev.firechat.data.Chat;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final String TAG = ChatAdapter.class.getSimpleName();
    private List<Chat> chatList;
    private Context mContext;
    private String uid;
    public static final int FROM_TYPE = 0;
    public static final int TO_TYPE = 1;

    public ChatAdapter(Context context, List<Chat> chatList) {
        this.chatList = chatList;
        mContext = context;
        uid = FirebaseAuth.getInstance().getUid();
    }

    @Override
    public int getItemViewType(int position) {
        return uid.equals(chatList.get(position).getFromID()) ? FROM_TYPE : TO_TYPE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        int type = getItemViewType(i);
        switch (type) {
            case FROM_TYPE:
                View fromView = inflater.inflate(R.layout.chat_from_item_layout
                        , parent, false);
                viewHolder = new FromUserView(fromView);
                break;
            case TO_TYPE:
                View toView = inflater.inflate(R.layout.chat_to_item_layout
                        , parent, false);
                viewHolder = new ToUserView(toView);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Chat chat = chatList.get(position);
        int type = holder.getItemViewType();
        switch (type) {
            case FROM_TYPE:
                if (holder instanceof ToUserView) {
                    Log.d(TAG, "\nfromVH ClassCastException\n"
                            + holder.toString());
                } else {
                    FromUserView fromUserView = FromUserView.class.cast(holder);
                    fromUserView.setTextViewFromMessage(chat.getMsgContent());
                }
                break;
            case TO_TYPE:
                if (holder instanceof FromUserView) {
                    Log.d(TAG, "\ntoVH ClassCastException\n"
                            + holder.toString());
                } else {
                    ToUserView toUserView = ToUserView.class.cast(holder);
                    toUserView.setTextViewToMessage(chat.getMsgContent());
                }
        }

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public static class FromUserView extends RecyclerView.ViewHolder {
        private CircleImageView imageViewFromProfile;
        private TextView textViewFromMessage;

        public FromUserView(@NonNull View itemView) {
            super(itemView);
            imageViewFromProfile = itemView.findViewById(R.id.from_image_view_user_item_profile);
            textViewFromMessage = itemView.findViewById(R.id.from_text_view_msg_content);
        }

        void setTextViewFromMessage(String msg) {
            if (textViewFromMessage != null)
                textViewFromMessage.setText(msg);
        }
    }

    public static class ToUserView extends RecyclerView.ViewHolder {
        private CircleImageView imageViewToProfile;
        private TextView textViewToMessage;

        public ToUserView(@NonNull View itemView) {
            super(itemView);
            imageViewToProfile = itemView.findViewById(R.id.to_image_view_user_item_profile);
            textViewToMessage = itemView.findViewById(R.id.to_text_view_msg_content);
        }

        void setTextViewToMessage(String msg) {
            if (textViewToMessage != null)
                textViewToMessage.setText(msg);
        }

    }
}
