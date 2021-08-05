package com.kaisengao.ksglikeview.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kaisengao.ksglikeview.R;
import com.kaisengao.likeview.like.KsgLikeView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @ClassName: Viewpager2Adapter
 * @Author: KaiSenGao
 * @CreateDate: 8/5/21 10:31 PM
 * @Description: Viewpager2 列表模式 Adapter
 */
public class Viewpager2Adapter extends RecyclerView.Adapter<Viewpager2Adapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_viewpager2, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mItemVideo.setText("我是短视频（" + position + "）");
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatTextView mItemVideo;

        private final KsgLikeView mItemLike;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            this.mItemVideo = itemView.findViewById(R.id.item_video);
            this.mItemLike = itemView.findViewById(R.id.item_like);
            // 点赞
            itemView.findViewById(R.id.item_praise).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemLike.addFavor();
                }
            });
            // 刷新Item
            itemView.findViewById(R.id.item_notify).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    notifyItemChanged(getLayoutPosition());
                }
            });
            // InitLikeView
            this.initLikeView();
        }

        /**
         * InitLikeView
         */
        private void initLikeView() {
            // 添加资源文件
            this.mItemLike.addLikeImages(
                    R.drawable.heart0, R.drawable.heart1, R.drawable.heart2,
                    R.drawable.heart3, R.drawable.heart4, R.drawable.heart5,
                    R.drawable.heart6, R.drawable.heart7, R.drawable.heart8);
        }
    }
}