package org.zhx.common.image.displayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.locks.ReentrantLock;

import org.zhx.common.image.Constant;
import org.zhx.common.image.Target;
import org.zhx.common.image.targets.BitmapTarget;
import org.zhx.common.image.targets.SourceTarget;
import org.zhx.common.image.cache.CacheConfig;
import org.zhx.common.image.callback.ImageLoadCallBack;
import org.zhx.common.image.displayer.DisplayerImpl.AnimateDisplayer;
import org.zhx.common.image.exception.CImageException;
import org.zhx.common.image.loader.ImageLoader;
import org.zhx.common.image.loader.http.BaseImageDownloader;
import org.zhx.common.image.utils.CLog;
import org.zhx.common.image.utils.IoUtils;

/**
 * Created by ${zhouxue} on 17/10/4 20: 55.
 * QQ:515278502
 */

public class DownLoadWorker extends AsyncTask<String, String, Target> {
    private ImageView imageView;
    private String url;
    private ImageLoadCallBack callBack;
    private Context mContext;
    private DisPlayer disPlayer;
    private CacheConfig cacheConfig;
    private int logdingDrawable, errorDrawable;

    private ImageLoader imageLoader;

    public DownLoadWorker(String url, CacheConfig cacheConfig, ImageLoader imageLoader) {
        this.url = url;
        this.cacheConfig = cacheConfig;
        this.imageLoader = imageLoader;
    }

    public DownLoadWorker(String url, CacheConfig cacheConfig) {
        this.url = url;
        this.cacheConfig = cacheConfig;
    }

    public DownLoadWorker setCallBack(ImageLoadCallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    /**
     * 开始加载...
     */
    public void start() {
        execute(url);
    }

    /**
     * 开始加载...
     */
    public void start(String... param) {
        ReentrantLock loadFromUriLock = ImageController.getInstance().preperToLoadUrl(url, imageView);
        if (loadFromUriLock.isLocked()) {
            CLog.e("loading...wait");
        }
        execute(url, param[0]);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (logdingDrawable != 0) {
            imageView.setImageResource(logdingDrawable);
        }
        if (callBack != null)
            callBack.onLoadingStarted(url, imageView);
    }

    @Override
    protected Target doInBackground(String... params) {
        ReentrantLock loadFromUriLock = ImageController.getInstance().preperToLoadUrl(url, imageView);
        loadFromUriLock.lock();
        Target target = null;
        boolean error = false;
        InputStream stream=null;
        try {
            target = cacheConfig.getImageCache().get(url);
            if (target != null) {
                return target;
            } else {
                CLog.e("本地无缓存文件....从网络下载图片..." + url);
                if (imageLoader == null) {
                    imageLoader = new BaseImageDownloader(mContext);
                }
                 stream= imageLoader.getStream(url, null);
                if (stream != null) {
                    target = new BitmapTarget(BitmapFactory.decodeStream(stream));
                } else {
                    error = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return error(e);
        } finally {
            IoUtils.closeSilently(stream);
            loadFromUriLock.unlock();
        }
        if (error) {
            return error(new Exception("Loading error"));
        } else {
            if (target != null) {
                cacheConfig.getImageCache().put(url, target);
            }
        }
        return target;
    }

    private Target error(Exception e) {
        if (errorDrawable != 0) {
            return new SourceTarget(mContext.getResources().getDrawable(errorDrawable));
        } else {
            if (callBack != null)
                callBack.onLoadingFailed(url, imageView, new CImageException(CImageException.FailType.IO_ERROR, e));
        }
        return null;
    }

    @Override
    protected void onPostExecute(Target target) {
        if (callBack != null)
            callBack.onLoadingComplete(url, imageView, (Bitmap) target.getTarget());
        else {
            if (disPlayer == null) {
                CLog.e("创建显示器....");
                disPlayer = new AnimateDisplayer();
            }
            if (ImageController.getInstance().isDisplay(imageView, url)) {
                CLog.e("显示", "显示图片 bitmap " + url + "  " + (target == null ? "空" : "不为空"));
                disPlayer.display(imageView, target);
            } else {
                imageView.setImageBitmap(null);
            }
        }
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
        mContext = imageView.getContext();
    }

    public DownLoadWorker setDisPlayer(DisPlayer disPlayer) {
        this.disPlayer = disPlayer;
        return this;
    }

    public void smallstart() {
        start(Constant.isSmall);
    }

    public void setErrorDrawable(int errorDrawable) {
        this.errorDrawable = errorDrawable;
    }

    public void setLogdingDrawable(int logdingDrawable) {
        this.logdingDrawable = logdingDrawable;
    }

    public DownLoadWorker() {
    }
}
