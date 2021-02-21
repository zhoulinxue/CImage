package org.zhx.common.image;

import android.content.Context;
import android.widget.ImageView;

import org.zhx.common.image.cache.CacheConfig;
import org.zhx.common.image.callback.ImageLoadCallBack;
import org.zhx.common.image.core.BaseWorker;
import org.zhx.common.image.core.Worker;
import org.zhx.common.image.displayer.DownLoadWorker;
import org.zhx.common.image.loader.ImageLoader;
import org.zhx.common.image.loader.http.BaseImageDownloader;
import org.zhx.common.image.loader.https.CImageHostnameVerifier;
import org.zhx.common.image.loader.https.CImageX509TrustManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.X509TrustManager;

/**
 * Name: CImage
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2020-07-09 17:02
 */
public class CImage {
    private static String TAG = CImage.class.getSimpleName();
    private static CImage mCImage;
    public static boolean isDebug = true;
    private static ImageLoader imageLoader;
    private static CacheConfig mCacheConfig;

    public static CImage init(Context context) {
        CImageX509TrustManager manager = new CImageX509TrustManager();
        CImageHostnameVerifier hostnameVerifier = new CImageHostnameVerifier();
        return init(context, manager, hostnameVerifier);
    }

    public CImage setImageLoader(ImageLoader loader) {
        this.imageLoader = loader;
        return mCImage;
    }

    public static CImage init(Context context, X509TrustManager x509TrustManager, HostnameVerifier hostnameVerifier) {
        if (mCImage == null) {
            synchronized (CImage.class) {
                if (mCImage == null) {
                    mCacheConfig = new CacheConfig(context.getCacheDir().getAbsolutePath());
                    mCImage = new CImage(mCacheConfig);
                    imageLoader = new BaseImageDownloader(context);
                    imageLoader.initHttps(x509TrustManager, hostnameVerifier);
                }
            }
        }
        return mCImage;
    }

    public static Worker with(ImageView imageView) {
        if (mCacheConfig == null) {
            throw new IllegalStateException("call CImage.Init(CacheConfig cacheConfig) first");
        }
        Worker worker = null;
        if (imageView != null) {
            worker = workerMap.get(imageView.hashCode());
            if (worker != null) {
                worker.cancel();
            }
            worker = new BaseWorker(imageView, mCacheConfig, imageLoader);
            workerMap.put(imageView.hashCode(), worker);
        } else {
            throw new NullPointerException("ImageView can not be null");
        }
        return worker;
    }

    public static void loadWithCallBack(String url, ImageLoadCallBack callBack) {
        Target target = mCacheConfig.getImageCache().get(url);
        if (target != null) {
            if (callBack != null) {
                callBack.onLoadingComplete(url, target);
                return;
            }
        }
        DownLoadWorker worker = loaderMap.get(url);
        if (worker != null) {
            worker.cancel(true);
        }
        worker = new DownLoadWorker(url, mCacheConfig, imageLoader);
        loaderMap.put(url, worker);
        worker.setCallBack(callBack);
        worker.start();
    }

    private static Map<Integer, Worker> workerMap = new ConcurrentHashMap();
    private static Map<String, DownLoadWorker> loaderMap = new ConcurrentHashMap();

    public CImage(CacheConfig cacheConfig) {
        this.mCacheConfig = cacheConfig;
    }

    public CacheConfig getCacheConfig() {
        return mCacheConfig;
    }

    public void setCacheConfig(CacheConfig cacheConfig) {
        this.mCacheConfig = cacheConfig;
    }
}
