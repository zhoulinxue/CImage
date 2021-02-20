package org.zhx.common.image;

import android.content.Context;

import org.zhx.common.image.cache.CacheConfig;
import org.zhx.common.image.callback.ImageLoadCallBack;
import org.zhx.common.image.core.BaseWorker;
import org.zhx.common.image.core.Worker;
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

    /**
     * 下载界面
     *
     * @param url
     * @return
     */
    public static Worker load(String url) {
        if (mCacheConfig == null) {
            throw new IllegalStateException("call CImage.Init(CacheConfig cacheConfig) first");
        }
        Worker parser = hashMap.get(url);
        if (parser == null) {
            parser = new BaseWorker(url, mCacheConfig, imageLoader);
            hashMap.put(url, parser);
        }
        return parser;
    }

    public static void loadWithCallBack(String url, ImageLoadCallBack callBack) {
        if (mCacheConfig == null) {
            throw new IllegalStateException("call CImage.Init(CacheConfig cacheConfig) first");
        }
        Worker parser = hashMap.get(url);
        if (parser == null) {
            parser = new BaseWorker(url, mCacheConfig, imageLoader);
            hashMap.put(url, parser);
        }
        parser.setCallback(callBack);
    }

    private static Map<String, Worker> hashMap = new ConcurrentHashMap();

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
