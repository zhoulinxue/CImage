package org.zhx.common.image;

import android.app.Application;
import android.content.Context;

import org.zhx.common.image.cache.CacheConfig;
import org.zhx.common.image.core.BaseUrlParser;
import org.zhx.common.image.core.UrlParser;
import org.zhx.common.image.loader.ImageLoader;
import org.zhx.common.image.loader.http.BaseImageDownloader;
import org.zhx.common.image.loader.https.CImageHostnameVerifier;
import org.zhx.common.image.loader.https.CImageX509TrustManager;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
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
    private static CacheConfig cacheConfig;
    private static ImageLoader imageLoader;

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
                    CacheConfig cacheConfig = new CacheConfig(context.getCacheDir().getAbsolutePath());
                    mCImage = new CImage(cacheConfig);
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
    public static UrlParser load(String url) {
        if (cacheConfig == null) {
            throw new IllegalStateException("call CImage.Init(CacheConfig cacheConfig) first");
        }
        UrlParser parser = hashMap.get(url);
        if (parser == null) {
            parser = new BaseUrlParser(url, cacheConfig, imageLoader);
        }
        return parser;
    }

    private static Map<String, UrlParser> hashMap = new ConcurrentHashMap();

    public CImage(CacheConfig cacheConfig) {
        this.cacheConfig = cacheConfig;
    }

    public CacheConfig getCacheConfig() {
        return cacheConfig;
    }

    public void setCacheConfig(CacheConfig cacheConfig) {
        this.cacheConfig = cacheConfig;
    }
}
