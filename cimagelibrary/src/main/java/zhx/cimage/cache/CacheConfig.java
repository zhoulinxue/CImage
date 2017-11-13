package zhx.cimage.cache;

import zhx.cimage.Constant;
import zhx.cimage.cache.Impl.BaseDiskCache;
import zhx.cimage.cache.Impl.BaseImageCache;
import zhx.cimage.cache.Impl.BaseMemoryCache;

/**
 * Created by ${zhouxue} on 17/10/4 13: 57.
 * QQ:515278502
 */

public class CacheConfig {
    private static CacheConfig config;
    /**
     * 当前已️缓存大小
     */
    private int mCurrentSize;
    /**
     * 是否缓存到本地
     */
    private boolean isCacheInDisk;
    /**
     * 是否缓存到 内存
     */
    private boolean isCacheInMemory;

    /**
     * 缓存策略
     */
    private ImageCache imageCache;



    public static CacheConfig getInstance() {
        if(config==null){
            config=new CacheConfig();
            config.initdefault();
        }
        return config;
    }

    private CacheConfig initdefault(){
        setCacheInMemory(true);
        setCacheInDisk(true);
        setImageCache(new BaseImageCache(Constant.mMemorySize,true,true));
        return this;
    }

    public int getmCurrentSize() {
        return mCurrentSize;
    }

    public void setmCurrentSize(int mCurrentSize) {
        this.mCurrentSize = mCurrentSize;
    }

    public boolean isCacheInDisk() {
        return isCacheInDisk;
    }

    public void setCacheInDisk(boolean cacheInDisk) {
        isCacheInDisk = cacheInDisk;
    }

    public boolean isCacheInMemory() {
        return isCacheInMemory;
    }

    public void setCacheInMemory(boolean cacheInMemory) {
        isCacheInMemory = cacheInMemory;
    }

    public ImageCache getImageCache() {
        return imageCache;
    }

    public void setImageCache(ImageCache imageCache) {
        this.imageCache = imageCache;
    }
}
