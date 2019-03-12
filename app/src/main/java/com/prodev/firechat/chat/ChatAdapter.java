package com.prodev.firechat.chat;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.prodev.firechat.R;
import com.prodev.firechat.data.chat.Chat;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.BaseViewHolder> {
    public static final String TAG = ChatAdapter.class.getSimpleName();
    private List<Chat> chatList;
    private Context mContext;
    private String uid;
    public static final int FROM_TYPE = 0;
    public static final int TO_TYPE = 1;
    private String toImageUrl;
    private String fromImageUrl;

    public ChatAdapter(Context context, List<Chat> chatList) {
        this.chatList = chatList;
        mContext = context;
        uid = FirebaseAuth.getInstance().getUid();
    }

    public void setToImageUrl(String toImageUrl) {
        this.toImageUrl = toImageUrl;
    }

    public void setFromImageUrl(String fromImageUrl) {
        this.fromImageUrl = fromImageUrl;
    }

    @Override
    public int getItemViewType(int position) {
        Chat chat = chatList.get(position);
        String userID = chat.getFromID();
        return uid.equals(userID) ? FROM_TYPE : TO_TYPE;
    }

    /**
     * the error was cause because misunderstand to the param of this method
     * i was thinking its a position but it is a view type
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case FROM_TYPE:
                View fromView = inflater.inflate(R.layout.chat_from_item_layout
                        , parent, false);
                return new FromUserView(fromView);

            case TO_TYPE:
                View toView = inflater.inflate(R.layout.chat_to_item_layout
                        , parent, false);
                return new ToUserView(toView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        Chat chat = chatList.get(position);
        int type = holder.getItemViewType();
        switch (type) {
            case FROM_TYPE:
                if (holder instanceof ToUserView) {
                    Log.d(TAG, "\nfromVH ClassCastException\n"
                            + holder.toString());
                } else {
                    FromUserView fromUserView = (FromUserView) holder;
                    fromUserView.setTextViewFromMessage(chat.getMsgContent());
                    fromUserView.setImageViewToProfile(fromImageUrl);
                }
                break;
            case TO_TYPE:
                if (holder instanceof FromUserView) {
                    Log.d(TAG, "\ntoVH ClassCastException\n"
                            + holder.toString());
                } else {
                    ToUserView toUserView = (ToUserView) holder;
                    toUserView.setTextViewToMessage(chat.getMsgContent());
                    toUserView.setImageViewToProfile(toImageUrl);
                }
                break;
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    //this is not necessary it can be done without it
    static class BaseViewHolder extends RecyclerView.ViewHolder {

        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    static class FromUserView extends BaseViewHolder {
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
            else
                Log.d(TAG, "setTextViewFromMessage: NULL");
        }

        void setImageViewToProfile(String imgUrl) {
            Uri imgUri = Uri.parse(imgUrl);
            Glide.with(itemView).load(imgUri).into(imageViewFromProfile);
        }
    }

    static class ToUserView extends BaseViewHolder {
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
            else
                Log.e(TAG, "setTextViewToMessage: NULL");
        }

        public void setImageViewToProfile(String imgUrl) {
            if (imgUrl != null) {
                Uri imgUri = Uri.parse(imgUrl);
                Glide.with(itemView).load(imgUri).into(imageViewToProfile);
            }
        }
    }
}
