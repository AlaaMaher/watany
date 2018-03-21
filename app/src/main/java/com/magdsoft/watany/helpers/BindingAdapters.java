package com.magdsoft.watany.helpers;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.content.res.AppCompatResources;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A class for all binding adapters in the application
 */
public class BindingAdapters {
    private static final String TAG = BindingAdapters.class.getSimpleName();
    @BindingAdapter("image")
    public static void loadImage(ImageView view, String url) {
        if (null != url && !url.isEmpty()) {
            Log.d(TAG, "Loading " + url);
            //here we are
            Glide.with(view.getContext()).load(url).centerCrop().into(view);
        }
    }


    @BindingAdapter("thumbnail")
    public static void loadImage(ImageView view, Bitmap url) {
        if (null != url) {
            view.setImageBitmap(url);
        }
    }

    @BindingAdapter("imageRes")
    public static void loadImage(ImageView view, @DrawableRes int id) {
        Log.d(TAG, "Loading image resource " + id);
        Glide.with(view.getContext()).load(id).into(view);
    }

    @BindingAdapter("circleImageRes")
    public static void loadImage(CircleImageView view, @DrawableRes int id) {
        view.setImageDrawable(loadDrawable(view.getContext(), id));
    }

    private static Drawable loadDrawable(Context context, @DrawableRes int id) {
        return AppCompatResources.getDrawable(context, id);
    }

    @BindingAdapter("drawable")
    public static void setDrawable(TextView view, @DrawableRes int id) {
        TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(
                view, loadDrawable(view.getContext(), id), null, null, null);
    }

    @BindingAdapter("drawableStart")
    public static void setDrawableStart(TextView view, @DrawableRes int id) {
        TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(
                view, loadDrawable(view.getContext(), id), null, null, null);
    }

    @BindingAdapter("drawableTop")
    public static void setDrawableTop(TextView view, @DrawableRes int id) {
        TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(
                view, null, loadDrawable(view.getContext(), id), null, null);
    }

    @BindingAdapter("drawableEnd")
    public static void setDrawableEnd(TextView view, @DrawableRes int id) {
        TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(
                view, null, null, loadDrawable(view.getContext(), id), null);
    }

    @BindingAdapter("drawableBottom")
    public static void setDrawableBottom(TextView view, @DrawableRes int id) {
        TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(
                view, null, null, null, loadDrawable(view.getContext(), id));
    }

    @BindingAdapter("background")
    public static void setBackground(View view, @DrawableRes int id) {
        ViewCompat.setBackground(view, loadDrawable(view.getContext(), id));
    }

    @BindingAdapter("backgroundTint")
    public static void setBackgroundTint(ImageView view, int id) {
        Drawable drawable = view.getDrawable();
        DrawableCompat.setTint(drawable, id);
    }

    @BindingAdapter("peekHeight")
    public static void setPeekHeight(View view, int oldPeekHeight, int newPeekHeight) {
        BottomSheetBehavior b = BottomSheetBehavior.from(view);
        b.setPeekHeight(newPeekHeight);
    }

    @BindingAdapter("anchorGravity")
    public static void setAnchorGravity(View view, int oldGravity, int newGravity) {
        CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
        p.anchorGravity = newGravity;
        view.setLayoutParams(p);
    }


    @BindingAdapter({"imageUrl", "progressView"})
    public static void loadImageWithLoading(ImageView view, String imageUrl, final ProgressBar progressBar) {
        Glide.with(view.getContext()).load(imageUrl)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(view);
    }




}
