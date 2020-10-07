package com.blueeyescloud.baselib.mvvm;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

public class ImageUtils {

    @BindingAdapter("placeholder")
    public static void loadImage(ImageView imageView, int placeHolderId){
        Glide.with(imageView.getContext())
                .load("")
                .placeholder(placeHolderId)
                .into(imageView);
    }

    @BindingAdapter(value = {"imageUrl", "placeholder", "error"}, requireAll = false)
    public static void loadImage(ImageView imageView, String url, int placeHolderId, int errorId){
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(placeHolderId)
                .error(errorId)
                .into(imageView);
    }

    @BindingAdapter({"imageUrl", "placeholder"})
    public static void loadImage(ImageView imageView, String url, int placeHolderId){
        Glide.with(imageView.getContext())
                .load(url)
                .placeholder(placeHolderId)
//                .error(errorId)
                .into(imageView);
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String url) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
//                .placeholder(R.mipmap.ic_launcher_round)
//                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);

        Glide.with(view.getContext()).applyDefaultRequestOptions(options)
                .load(url)
                .transition(new DrawableTransitionOptions()
                        .crossFade(1000))
                .into(view);
    }
}
