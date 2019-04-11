package com.fss.mobiletrading.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.tcscuat.mobiletrading.R;

/**
 * Created by giangcoibg on 2/20/2017.
 */

public class Alert extends AlertDialog
{

    protected Alert(Context context) {
        super(context);
    }
    public static void showQuestion(Context context, String title, String message, OnClickListener onPositiveClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.confirm,onPositiveClickListener);
        builder.setNegativeButton(R.string.no, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
    public static void showAlert(Context context, String title, String message, OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.confirm,onClickListener);
        builder.create().show();
    }
    public static void showAlert(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(R.string.confirm, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    /**
     * Created by Mr.Incredible on 11/24/2015.
     */
    public static class LoadingImage extends ImageView {
        public AnimationDrawable loadingImageView = null;

        public LoadingImage(Context context, AttributeSet attrs) {
            super(context,attrs);
            setBackgroundResource (R.drawable.animation_loading);
            loadingImageView = (AnimationDrawable) getBackground();
        }

        public void start() {
            setVisibility(VISIBLE);
            loadingImageView.start();
        }

        public void stop() {
            setVisibility(GONE);
            loadingImageView.stop();
        }

    }
}
