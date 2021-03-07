package org.zhx.common.image.displayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
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
import org.zhx.common.image.targets.SrcTarget;
import org.zhx.common.image.utils.CLog;
import org.zhx.common.image.utils.IoUtils;

/**
 * Created by ${zhouxue} on 17/10/4 20: 55.
 * QQ:515278502
 */

public class DownLoadWorker extends AsyncTask<String, String, Target> {
    private String url;
    private ImageLoadCallBack callBack;
    private Context mContext;
    private CacheConfig cacheConfig;
    private ImageLoader imageLoader;
    private int state=-1;


    public DownLoadWorker(String url, CacheConfig cacheConfig, ImageLoader imageLoader) {
        this.url = url;
        this.cacheConfig = cacheConfig;
        this.imageLoader = imageLoader;
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

    @Override
    protected void onPreExecute() {
        state=0;
        callBack.onLoadingStarted(url);
    }

    boolean error = false;

    @Override
    protected Target doInBackground(String... params) {
        state=1;
        ReentrantLock loadFromUriLock = ImageController.getInstance().preperToLoadUrl(url);
        loadFromUriLock.lock();
        Target target = null;
        InputStream stream = null;
        try {
            CLog.e("本地无缓存文件....从网络下载图片..." + url);
            if (imageLoader == null) {
                imageLoader = new BaseImageDownloader(mContext);
            }
            stream = imageLoader.getStream(url, null);
            if (stream != null) {
                target = new BitmapTarget(BitmapFactory.decodeStream(stream));
            } else {
                error = true;
            }
        } catch (IOException e) {
            error = true;
        } finally {
            IoUtils.closeSilently(stream);
            loadFromUriLock.unlock();
        }
        if (target != null && !error) {
            cacheConfig.getImageCache().put(url, target);
        }
        return target;
    }

    @Override
    protected void onPostExecute(Target target) {
        if (target != null)
            callBack.onLoadingComplete(url, target);
        else {
            callBack.onLoadingErrorDrawable(url);
        }
        state=-1;
    }

    public int getState() {
        return state;
    }

    public DownLoadWorker() {
    }
}
