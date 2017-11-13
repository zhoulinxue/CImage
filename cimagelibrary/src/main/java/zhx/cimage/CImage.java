package zhx.cimage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import zhx.cimage.cache.CacheConfig;
import zhx.cimage.core.BaseUrlParser;
import zhx.cimage.core.UrlParser;
import zhx.cimage.displayer.DisPlayer;

/**
 * Created by zhouxue on 2017/4/20.
 * QQ 515278502
 * 工具对象类 自己写一个轻量级的图片缓存和处理 项目。
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
      return new BaseUrlParser(url,cacheConfig);
    }

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
