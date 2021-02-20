package org.zhx.common.image.core;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.DownloadListener;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import org.zhx.common.image.cache.CacheConfig;
import org.zhx.common.image.callback.ImageLoadCallBack;
import org.zhx.common.image.displayer.DownLoadWorker;
import org.zhx.common.image.displayer.DisPlayer;
import org.zhx.common.image.exception.CImageException;
import org.zhx.common.image.loader.ImageLoader;
import org.zhx.common.image.utils.CLog;

/**
 * Created by ${zhouxue} on 17/10/4 21: 17.
 * QQ:515278502
 */

public class BaseWorker implements Worker, ImageLoadCallBack {
    private String url;
    private CacheConfig cacheConfig;
    private DownLoadWorker container;
    private int logdingDrawable, errorDrawable;
    private DisPlayer disPlayer;
    private ImageLoadCallBack mCallBack;

    public BaseWorker(String url, CacheConfig cacheConfig, int logdingDrawable, int errorDrawable) {
        this.url = url;
        this.cacheConfig = cacheConfig;
        this.logdingDrawable = logdingDrawable;
        this.errorDrawable = errorDrawable;
    }

    public BaseWorker(String url, CacheConfig cacheConfig, ImageLoader imageLoader) {
        this.url = url;
        this.cacheConfig = cacheConfig;
        this.container = new DownLoadWorker(url, cacheConfig, imageLoader);
        this.container.setCallBack(this);
    }

    public void setCallBack(ImageLoadCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    @Override
    public void into(@Nullable ImageView imageView) {
        if (imageView == null) {
            CLog.e("image can not be null");
            throw new NullPointerException("imageView can not be null");
        }
        container.setImageView(imageView);
        if (logdingDrawable != 0) {
            container.setLogdingDrawable(logdingDrawable);
        }
        if (errorDrawable != 0) {
            container.setErrorDrawable(errorDrawable);
        }
        container.setDisPlayer(disPlayer);
        container.start();
    }

    @Override
    public Worker error(int drawable) {
        this.errorDrawable = drawable;
        return this;
    }

    @Override
    public Worker loading(int drawable) {
        this.logdingDrawable = drawable;
        return this;
    }

    @Override
    public Worker setDisPlayer(DisPlayer disPlayer) {
        this.disPlayer = disPlayer;
        return this;
    }

    @Override
    public void onLoadingStarted(String imageUri, ImageView view) {
        if (mCallBack != null) {
            mCallBack.onLoadingStarted(imageUri, view);
        }
    }

    @Override
    public void onLoadingFailed(String imageUri, View view, CImageException failReason) {
        if (mCallBack != null) {
            mCallBack.onLoadingFailed(imageUri, view, failReason);
        }
    }

    @Override
    public void onLoadingComplete(String imageUri, ImageView view, Bitmap loadedImage) {
        if (mCallBack != null) {
            mCallBack.onLoadingComplete(imageUri, view, loadedImage);
        }
    }


    @Override
    public void onLoadingCancelled(String imageUri, View view) {
        if (mCallBack != null) {
            mCallBack.onLoadingCancelled(imageUri, view);
        }
    }
}
