package com.bawp.contactroom.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawp.contactroom.R;
import com.bawp.contactroom.model.MediaModel;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private final List<MediaModel> mediaModelList;
    private final Context context;
    private final OnContactClickListener contactClickListener;

    public RecyclerViewAdapter(List<MediaModel> mediaModelList, Context context, OnContactClickListener onContactClickListener) {
        this.mediaModelList = mediaModelList;
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
        MediaModel mediaModel = Objects.requireNonNull(mediaModelList.get(position));
        holder.name.setText(mediaModel.getName());
        holder.occupation.setText(mediaModel.getOccupation());


    }

    @Override
    public int getItemCount() {
        return Objects.requireNonNull(mediaModelList.size());
    }

    public interface OnContactClickListener {
        void onContactClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView occupation;
        OnContactClickListener onContactClickListener;

        public ViewHolder(@NonNull View itemView, OnContactClickListener onContactClickListener) {
            super(itemView);
            name = itemView.findViewById(R.id.row_media_type_textview);
            occupation = itemView.findViewById(R.id.row_occupation_textview);
            this.onContactClickListener = onContactClickListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onContactClickListener.onContactClick(getAdapterPosition());
        }
    }
}
