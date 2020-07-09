package org.zhx.common.image;

import org.zhx.common.image.cache.CacheConfig;
import org.zhx.common.image.core.BaseUrlParser;
import org.zhx.common.image.core.UrlParser;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Name: CImage
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2020-07-09 17:02
 */
public class CImage {
    private static  String TAG=CImage.class.getSimpleName();
    private static  CImage mCImage;
    public static boolean isDebug=true;
    private static CacheConfig cacheConfig;

    public static void init(CacheConfig cacheConfig){
        if(mCImage==null){
            mCImage=new CImage(cacheConfig);
        }
    }

    /**
     * 下载界面
     * @param url
     * @return
     */
    public static UrlParser load(String url){
        if(cacheConfig==null){
            throw new IllegalStateException("call CImage.Init(CacheConfig cacheConfig) first");
        }
        UrlParser parser=hashMap.get(url);
        if(parser==null){
            parser=new BaseUrlParser(url,cacheConfig);
        }
        return parser;
    }
    private  static Map<String,UrlParser> hashMap=new ConcurrentHashMap();

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
