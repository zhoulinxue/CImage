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
import org.zhx.common.image.cache.CacheConfig;
import org.zhx.common.image.callback.ImageLoadCallBack;
import org.zhx.common.image.displayer.DisplayerImpl.AnimateDisplayer;
import org.zhx.common.image.exception.CImageException;
import org.zhx.common.image.loader.ImageLoader;
import org.zhx.common.image.loader.http.BaseImageDownloader;
import org.zhx.common.image.utils.CLog;

/**
 * Created by ${zhouxue} on 17/10/4 20: 55.
 * QQ:515278502
 */

public class BitmapContainer extends AsyncTask<String, String, Bitmap> {
    private ImageView imageView;
    private String url;
    private ImageLoadCallBack callBack;
    private Context mContext;
    private DisPlayer disPlayer;
    private CacheConfig cacheConfig;
    private int logdingDrawable, errorDrawable;

    private ImageLoader imageLoader;

    public BitmapContainer(String url, CacheConfig cacheConfig, ImageLoader imageLoader) {
        this.url = url;
        this.cacheConfig = cacheConfig;
        this.imageLoader = imageLoader;
    }

    public BitmapContainer(String url, CacheConfig cacheConfig) {
        this.url = url;
        this.cacheConfig = cacheConfig;
    }

    public BitmapContainer setCallBack(ImageLoadCallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    /**
     * 开始加载...
     */
    public void end() {
        execute(url);
    }

    /**
     * 开始加载...
     */
    public void end(String... param) {
        ReentrantLock loadFromUriLock = ImageController.getInstance().preperToLoadUrl(url, imageView);
        if (loadFromUriLock.isLocked()) {
            CLog.e("loading...wait");
        }
        execute(url, param[0]);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (logdingDrawable == 0) {
            imageView.setImageBitmap(null);
        } else {
            imageView.setImageResource(logdingDrawable);
        }
        if (callBack != null)
            callBack.onLoadingStarted(url, imageView);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        ReentrantLock loadFromUriLock = ImageController.getInstance().preperToLoadUrl(url, imageView);
        loadFromUriLock.lock();
        Bitmap bitmap = null;
        boolean error = false;
        try {
            bitmap = cacheConfig.getImageCache().get(url);
            if (bitmap != null) {
                CLog.e( "从本地缓存文件获取图片成功...." + url);
                return bitmap;
            } else {
                CLog.e( "本地无缓存文件....从网络下载图片..." + url);
                if (imageLoader == null) {
                    imageLoader = new BaseImageDownloader(mContext);
                }
                InputStream stream = imageLoader.getStream(url, null);
                if (stream != null) {
                    bitmap = BitmapFactory.decodeStream(stream);
                } else {
                    error = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return error(e);
        } finally {
            loadFromUriLock.unlock();
        }
        if (error) {
            return error(new Exception("Loading error"));
        } else {
            if (bitmap != null) {
                cacheConfig.getImageCache().put(url, bitmap);
            }
        }
        return bitmap;
    }

    private Bitmap error(Exception e) {
        if (errorDrawable != 0) {
            return BitmapFactory.decodeResource(mContext.getResources(), errorDrawable);
        } else {
            if (callBack != null)
                callBack.onLoadingFailed(url, imageView, new CImageException(CImageException.FailType.IO_ERROR, e));
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (callBack != null)
            callBack.onLoadingComplete(url, imageView, bitmap);
        else {
            if (disPlayer == null) {
                CLog.e("创建显示器....");
                disPlayer = new AnimateDisplayer();
            }
            if (ImageController.getInstance().isDisplay(imageView, url)) {
                CLog.e("显示", "显示图片 bitmap " + url + "  " + (bitmap == null ? "空" : "不为空"));
                disPlayer.display(imageView, bitmap);
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

    public BitmapContainer setDisPlayer(DisPlayer disPlayer) {
        this.disPlayer = disPlayer;
        return this;
    }

    public void smallend() {
        end(Constant.isSmall);
    }

    public void setErrorDrawable(int errorDrawable) {
        this.errorDrawable = errorDrawable;
    }

    public void setLogdingDrawable(int logdingDrawable) {
        this.logdingDrawable = logdingDrawable;
    }

    public BitmapContainer() {
    }
}
