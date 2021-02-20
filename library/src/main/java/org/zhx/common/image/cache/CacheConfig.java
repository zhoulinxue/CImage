package org.zhx.common.image.cache;

import org.zhx.common.image.Constant;
import org.zhx.common.image.cache.Impl.BaseImageCache;

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
    /**
     * 缓存的路径
     */
    private String cacheDir;

    public CacheConfig(String cacheDir) {
        this.cacheDir = cacheDir;
        initdefault();
    }

    private CacheConfig initdefault() {
        setCacheInMemory(true);
        setCacheInDisk(true);
        setImageCache(new BaseImageCache(Constant.mMemorySize, true, true, cacheDir));
        return this;
    }

    public int getCurrentSize() {
        return mCurrentSize;
    }

    public void setCurrentSize(int mCurrentSize) {
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

    public String getCacheDir() {
        return cacheDir;
    }

    public void setCacheDir(String cacheDir) {
        this.cacheDir = cacheDir;
    }
}
