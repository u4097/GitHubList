package com.bsobat.github.ui.util;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import com.bumptech.glide.Glide;

public final class DataBinder {
    private DataBinder() {
        //NO-OP
    }

    @BindingAdapter("imageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        if(url == null) return;
        Context context = imageView.getContext();
        Glide.with(context).load(url).into(imageView);
    }
}
