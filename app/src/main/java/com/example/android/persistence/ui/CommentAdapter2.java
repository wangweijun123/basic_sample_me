package com.example.android.persistence.ui;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.persistence.R;
import com.example.android.persistence.databinding.CommentItemBinding;
import com.example.android.persistence.db.entity.CommentEntity;

public class CommentAdapter2 extends ListAdapter<CommentEntity,CommentAdapter2.CommentViewHolder > {

    private final CommentClickCallback commentClickCallback;
    protected CommentAdapter2(CommentClickCallback commentClickCallback) {
        super(new AsyncDifferConfig.Builder<>(new DiffUtil.ItemCallback<CommentEntity>(){

            @Override
            public boolean areItemsTheSame(@NonNull CommentEntity old,
                                           @NonNull CommentEntity comment) {
                return old.getId() == comment.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull CommentEntity old,
                                              @NonNull CommentEntity comment) {
                return old.getId() == comment.getId()
                        && old.getPostedAt().equals(comment.getPostedAt())
                        && old.getProductId() == comment.getProductId()
                        && TextUtils.equals(old.getText(), comment.getText());
            }
        }).build());
        this.commentClickCallback = commentClickCallback;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CommentItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.comment_item,parent, false);
        return new CommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.binding.setComment(getItem(position));
        holder.binding.setCallback(commentClickCallback);
        holder.binding.executePendingBindings();
    }


    static final class CommentViewHolder extends RecyclerView.ViewHolder {
        CommentItemBinding binding;
        public CommentViewHolder(@NonNull CommentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
