package com.penguins.argentovivo_social;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private List<FeedImage> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView feedUsernameTxt;
        public ImageView mImageView;
        Button likeBtn;
        Button dislikeBtn;
        TextView likeNumberTxt;
        TextView locationTxt;

        Bitmap bMap;

        int likedDisliked = 0;

        boolean displayingNewImage = true;

        private List<FeedImage> mDataset;

        public MyViewHolder(final View v, List<FeedImage> dataset) {
            super(v);
            feedUsernameTxt = v.findViewById(R.id.list_item_text);
            mImageView = v.findViewById(R.id.list_item_img);

            bMap = BitmapFactory.decodeFile("/sdcard/img_app.jpg");

            likeBtn = v.findViewById(R.id.feed_like_btn);
            dislikeBtn = v.findViewById(R.id.feed_dislike_btn);
            likeNumberTxt = v.findViewById(R.id.feed_like_counter);
            locationTxt = v.findViewById(R.id.feed_location_text);

            this.mDataset = dataset;

            likeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (likedDisliked != 1) {
                        dislikeBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(v.getContext(), R.color.colorPrimary)));
                        likeBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(v.getContext(), R.color.colorBlack)));
                        changeLikes(1);
                        if (likedDisliked == -1) changeLikes(1);
                        likedDisliked = 1;
                    }
                }
            });
            dislikeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (likedDisliked != -1) {
                        likeBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(v.getContext(), R.color.colorPrimary)));
                        dislikeBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(v.getContext(), R.color.colorBlack)));
                        changeLikes(-1);
                        if (likedDisliked == 1) changeLikes(-1);
                        likedDisliked = -1;
                    }
                }
            });
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (displayingNewImage) {
                        mImageView.setImageBitmap(mDataset.get(getAdapterPosition()).oldImg);
                    }
                    else {
                        mImageView.setImageBitmap(mDataset.get(getAdapterPosition()).newImg);
                    }
                    displayingNewImage = !displayingNewImage;
                }
            });
        }

        private void changeLikes(int change) {

            Integer likes = Integer.parseInt(likeNumberTxt.getText().toString()) + change;
            likeNumberTxt.setText(likes.toString());
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<FeedImage> feedImageList) {
        mDataset = feedImageList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_layout, parent, false);


        MyViewHolder vh = new MyViewHolder(v, this.mDataset);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        FeedImage img = mDataset.get(position);
        holder.feedUsernameTxt.setText(img.author);
        holder.likeNumberTxt.setText((Integer.toString(img.likes)));
        holder.locationTxt.setText(img.location);
        holder.mImageView.setImageBitmap(img.newImg);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
