package com.example.android.qrcodereaver;

import android.content.Context;
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by dmidma on 12/3/17.
 */

public class ImageViewAdapter extends RecyclerView.Adapter<ImageViewAdapter.ImageViewHolder> {



    public ImageViewAdapter() {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.qr_image_item;

        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        ImageViewHolder viewHolder = new ImageViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {

    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private FloatingActionButton fabDeleteImage;
        private FloatingActionButton fabShareImage;
        private ImageView ivImage;


        public ImageViewHolder(View itemView) {
            super(itemView);

            fabDeleteImage = (FloatingActionButton) itemView.findViewById(R.id.fab_clear_image);
            fabDeleteImage.setOnClickListener(this);
            fabShareImage = (FloatingActionButton) itemView.findViewById(R.id.fab_share_image);
            fabShareImage.setOnClickListener(this);
            ivImage = (ImageView) itemView.findViewById(R.id.iv_image);

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.fab_clear_image:
                    return;
                case R.id.fab_share_image:
                    return;
            }
        }
    }
}
