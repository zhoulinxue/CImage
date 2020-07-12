package org.zhx.common.image;

import org.zhx.common.image.cache.CacheConfig;
import org.zhx.common.image.core.BaseUrlParser;
import org.zhx.common.image.core.UrlParser;
import org.zhx.common.image.loader.ImageLoader;
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
    private X509TrustManager x509TrustManager;
    private HostnameVerifier hostnameVerifier;
    private static ImageLoader imageLoader;

    public static CImage init() {
        if (mCImage == null) {
            HostnameVerifier ignoreHostnameVerifier = new CImageHostnameVerifier();
            mCImage = new CImage(CacheConfig.getInstance(), new CImageX509TrustManager(), ignoreHostnameVerifier);
        }
        return mCImage;
    }

    public CImage setImageLoader(ImageLoader loader) {
        this.imageLoader = loader;
        return mCImage;
    }

    public static void init(X509TrustManager x509TrustManager, HostnameVerifier hostnameVerifier) {
        if (mCImage == null) {
            mCImage = new CImage(CacheConfig.getInstance(), x509TrustManager, hostnameVerifier);
        }
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

    public CImage(CacheConfig cacheConfig, X509TrustManager x509TrustManager, HostnameVerifier hostnameVerifier) {
        this.cacheConfig = cacheConfig;
        this.x509TrustManager = x509TrustManager;
        this.hostnameVerifier = hostnameVerifier;
        if(imageLoader!=null){
            imageLoader.initHttps(x509TrustManager,hostnameVerifier);
        }
    }

    public CacheConfig getCacheConfig() {
        return cacheConfig;
    }

    public void setCacheConfig(CacheConfig cacheConfig) {
        this.cacheConfig = cacheConfig;
    }
}
