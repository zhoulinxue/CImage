package org.zhx.common.image.core;

import android.graphics.Bitmap;
import android.view.View;
import android.webkit.DownloadListener;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import org.zhx.common.image.Target;
import org.zhx.common.image.cache.CacheConfig;
import org.zhx.common.image.callback.ImageLoadCallBack;
import org.zhx.common.image.displayer.DisplayerImpl.AnimateDisplayer;
import org.zhx.common.image.displayer.DownLoadWorker;
import org.zhx.common.image.displayer.DisPlayer;
import org.zhx.common.image.displayer.ImageController;
import org.zhx.common.image.exception.CImageException;
import org.zhx.common.image.loader.ImageLoader;
import org.zhx.common.image.utils.CLog;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ${zhouxue} on 17/10/4 21: 17.
 * QQ:515278502
 */

public class BaseWorker implements Worker, ImageLoadCallBack {
    private String url;
    private CacheConfig cacheConfig;
    private int logdingDrawable, errorDrawable;
    private DisPlayer disPlayer;
    private ImageLoadCallBack mCallBack;
    private ImageLoader imageLoader;
    private ImageView imageView;

    public BaseWorker(String url, CacheConfig cacheConfig, int logdingDrawable, int errorDrawable) {
        this.url = url;
        this.cacheConfig = cacheConfig;
        this.logdingDrawable = logdingDrawable;
        this.errorDrawable = errorDrawable;
    }

    public BaseWorker(String url, CacheConfig cacheConfig, ImageLoader imageLoader) {
        this.url = url;
        this.cacheConfig = cacheConfig;
        this.imageLoader = imageLoader;
    }

    public void setCallBack(ImageLoadCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    @Override
    public void into(@Nullable ImageView imageView) {
        this.imageView = imageView;
        ImageController.getInstance().cache(url, imageView);
        if (imageView == null) {
            CLog.e("image can not be null");
            throw new NullPointerException("imageView can not be null");
        }
        DownLoadWorker worker = new DownLoadWorker(url, cacheConfig, imageLoader);
        worker.setCallBack(this);
        if (!hasLoaded(imageView, url))
            worker.start();
    }

    private static Map<String, String> hashMap = new ConcurrentHashMap();

    private boolean hasLoaded(ImageView imageView, String url) {
        if (imageView == null) {
            return true;
        }
        String code = imageView.hashCode() + url;
        if (hashMap.get(imageView.hashCode() + url) != null) {
            return true;
        }
        hashMap.put(code, url);
        return false;
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
    public void setCallback(ImageLoadCallBack callback) {
        this.mCallBack = callback;
        DownLoadWorker worker = new DownLoadWorker(url, cacheConfig, imageLoader);
        worker.setCallBack(mCallBack);
        worker.start();
    }

    @Override
    public void onLoadingStarted(String imageUri) {
        if (imageView != null && logdingDrawable != 0) {
            imageView.setImageResource(logdingDrawable);
        }
        if (mCallBack != null) {
            mCallBack.onLoadingStarted(imageUri);
        }
    }

    @Override
    public void onLoadingFailed(String imageUri, CImageException failReason) {
        if (mCallBack != null) {
            mCallBack.onLoadingFailed(imageUri, failReason);
        }
    }

    @Override
    public void onLoadingComplete(String imageUri, Target target) {
        if (mCallBack != null) {
            mCallBack.onLoadingComplete(imageUri, target);
        } else {
            if (disPlayer == null) {
                CLog.e("创建显示器....");
                disPlayer = new AnimateDisplayer();
            }
            CLog.e("显示", "显示图片 bitmap " + url + "  " + (target == null ? "空" : "不为空"));
            if (ImageController.getInstance().isDisplay(imageView, url)) {
                disPlayer.display(imageView, target);
            } else {
                imageView.setImageBitmap(null);
            }
        }
    }


    @Override
    public void onLoadingCancelled(String imageUri) {
        if (mCallBack != null) {
            mCallBack.onLoadingCancelled(imageUri);
        }
    }

    @Override
    public void onLoadingErrorDrawable() {
        if (imageView != null && errorDrawable != 0) {
            imageView.setImageResource(errorDrawable);
        }
        if (mCallBack != null) {
            mCallBack.onLoadingErrorDrawable();
        }
    }
}
