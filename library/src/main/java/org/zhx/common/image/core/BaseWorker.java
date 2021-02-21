package org.zhx.common.image.core;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.webkit.DownloadListener;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.zhx.common.image.Target;
import org.zhx.common.image.cache.CacheConfig;
import org.zhx.common.image.callback.ImageLoadCallBack;
import org.zhx.common.image.displayer.DisplayerImpl.AnimateDisplayer;
import org.zhx.common.image.displayer.DownLoadWorker;
import org.zhx.common.image.displayer.DisPlayer;
import org.zhx.common.image.displayer.ImageController;
import org.zhx.common.image.exception.CImageException;
import org.zhx.common.image.io.DataType;
import org.zhx.common.image.loader.ImageLoader;
import org.zhx.common.image.utils.CLog;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ${zhouxue} on 17/10/4 21: 17.
 * QQ:515278502
 */

public class BaseWorker implements Worker, ImageLoadCallBack {
    private CacheConfig cacheConfig;
    private int logdingDrawable, errorDrawable;
    private DisPlayer disPlayer;
    private ImageLoadCallBack mCallBack;
    private ImageLoader imageLoader;
    private ImageView imageView;
    private Map<String, Long> times = new ConcurrentHashMap<>();
    DownLoadWorker worker;

    public BaseWorker(ImageView imageView, CacheConfig cacheConfig, ImageLoader imageLoader) {
        this.imageView = imageView;
        this.cacheConfig = cacheConfig;
        this.imageLoader = imageLoader;
    }

    public void setCallBack(ImageLoadCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    @Override
    public void from(String url) {
        ImageController.getInstance().cache(url, imageView);
        Target target = cacheConfig.getImageCache().get(url);
        if (target == null) {
            if (!isLoading(url)) {
                worker = new DownLoadWorker(url, cacheConfig, imageLoader);
                worker.setCallBack(this);
                worker.start();
                times.put(url, System.currentTimeMillis());
            }
        } else {
            onLoadingComplete(url, target);
        }
    }

    private boolean isLoading(String url) {
        if (times.get(url) == null) {
            return false;
        }
        return System.currentTimeMillis() - times.get(url) < 2000;
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

    }

    @Override
    public void cancel() {
        if (worker != null) {
            worker.cancel(true);
        }
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
            if (ImageController.getInstance().isDisplay(imageView, imageUri)) {
                CLog.e("显示图片 bitmap " + imageUri + "  " + (target.getType() + "显示成功"));
                disPlayer.display(imageView, target);
            } else {
                CLog.e("显示失败");
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
    public void onLoadingErrorDrawable(String url) {
        CLog.e("onLoadingErrorDrawable: " + url + "  @@" + (imageView != null) + "  !!!" + (errorDrawable != 0) + "!@#" + (mCallBack != null));
        if (mCallBack != null) {
            mCallBack.onLoadingErrorDrawable(url);
        }
        if (imageView != null && errorDrawable != 0) {
            if (ImageController.getInstance().isDisplay(imageView, url))
                imageView.setImageResource(errorDrawable);
        }
    }
}
