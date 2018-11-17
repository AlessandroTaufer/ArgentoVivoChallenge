package com.penguins.argentovivo_social;

import android.graphics.Bitmap;

public class FeedImage {

    public FeedImage(String author, String location, Bitmap newImg, Bitmap oldImg, int likes) {
        this.author = author;
        this.location = location;
        this.newImg = newImg;
        this.oldImg = oldImg;
        this.likes = likes;
    }

    public String author;
    public String location;
    public Bitmap newImg;
    public Bitmap oldImg;
    public int likes;
}
