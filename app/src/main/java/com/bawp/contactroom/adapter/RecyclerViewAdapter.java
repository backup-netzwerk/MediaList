package com.bawp.contactroom.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bawp.contactroom.R;
import com.bawp.contactroom.model.Contact;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final List<Contact> contactList;
    private final Context context;
    private final OnContactClickListener contactClickListener;

    public RecyclerViewAdapter(List<Contact> contactList, Context context, OnContactClickListener onContactClickListener) {
        this.contactList = contactList;
        this.context = context;
        this.contactClickListener = onContactClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_row, parent, false);

        return new ViewHolder(view, contactClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Contact item = Objects.requireNonNull(contactList.get(position));

        String mediaName = item.getName();

        // set icon
        String mediaTypeBr = "texto";
        if(mediaName.equals("image")) mediaTypeBr = "imagem";
        if(mediaName.equals("video")) mediaTypeBr = "vídeo";

        holder.name.setText(mediaTypeBr);
        holder.occupation.setText(item.getOccupation());
        holder.deliveryDate.setText(item.getDeliveryDate());

        if(mediaName.equals("text")) holder.mediaIconImgBtn.setBackgroundResource(R.drawable.ic_baseline_text_snippet_44);
        if(mediaName.equals("video")) holder.mediaIconImgBtn.setBackgroundResource(R.drawable.ic_baseline_videocam_44);
        if(mediaName.equals("image")) holder.mediaIconImgBtn.setBackgroundResource(R.drawable.ic_baseline_photo_44);
    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(contactList.size());
    }

    public interface OnContactClickListener {
        void onContactClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView occupation;
        public TextView deliveryDate;
        public ImageButton mediaIconImgBtn;

        OnContactClickListener onContactClickListener;

        public ViewHolder(@NonNull View itemView, OnContactClickListener onContactClickListener) {
            super(itemView);
            name = itemView.findViewById(R.id.row_media_type_textview);
            occupation = itemView.findViewById(R.id.row_occupation_textview);
            deliveryDate = itemView.findViewById(R.id.row_delivery_date_textview);
            // Change icon dynamically
            mediaIconImgBtn = itemView.findViewById(R.id.media_icon_img_btn);

            this.onContactClickListener = onContactClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onContactClickListener.onContactClick(getAdapterPosition());
        }
    }
}
