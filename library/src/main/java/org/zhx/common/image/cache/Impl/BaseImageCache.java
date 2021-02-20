package org.zhx.common.image.cache.Impl;

import android.graphics.Bitmap;
import android.text.TextUtils;

import org.zhx.common.image.Constant;
import org.zhx.common.image.Target;
import org.zhx.common.image.cache.DiskCache;
import org.zhx.common.image.cache.ImageCache;
import org.zhx.common.image.displayer.DisPlayer;
import org.zhx.common.image.exception.CImageException;
import org.zhx.common.image.utils.CLog;

/**
 * Created by ${zhouxue} on 17/10/4 13: 55.
 * QQ:515278502
 * <p>
 * 图片缓存 策略类
 */

public class BaseImageCache implements ImageCache {
    /**
     * 缓存大小
     */
    private int mCacheSize;
    /**
     * 磁盘储存
     */
    private DiskCache mDiskCache;
    /**
     * 内存缓存
     */
    private BaseMemoryCache mMemoryCache;
    /**
     * 图片显示对象
     */

    private DisPlayer mdisplayer;
    /**
     * 是否存在 内存
     */
    private boolean isCacheInMemory = true;
    /**
     * 是否存在 硬盘
     */
    private boolean isCacheOnDisk = true;
    /**
     * 缓存的路径
     */
    private String cacheDir;

    public BaseImageCache(int mCacheSize, boolean isCacheInMemory, boolean isCacheOnDisk, String cacheDir) {
        this.mCacheSize = mCacheSize;
        this.isCacheInMemory = isCacheInMemory;
        this.isCacheOnDisk = isCacheOnDisk;
        this.cacheDir = cacheDir;
        init();
    }

    private void init() {
        if (isCacheInMemory()) {
            mMemoryCache = new BaseMemoryCache(mCacheSize);
        }
        if (isCacheOnDisk()) {
            mDiskCache = new BaseDiskCache(Constant.mDiskMaxSize, cacheDir);
        }
    }

    @Override
    public final Target get(String url) {
        Target target = null;
        if (TextUtils.isEmpty(url)) {
            CImageException.throwNullKey();
        }
        CLog.e("正在内存获取..." + url);
        target = mMemoryCache.get(url);
        if (target == null) {
            CLog.e("内存获取失败..." + url);
            target = mDiskCache.get(url);
        } else {
            CLog.e("内存获取成功" + url);
        }
        return target;
    }


    @Override
    public final void put(String url, Target target) {
        if (TextUtils.isEmpty(url)) {
            CImageException.throwNullKey();
            return;
        }
        if (target == null) {
            CLog.e("CImage", "缓存失败 bitmap==null");
            return;
        }
        // 缓存到内存
        if (isCacheInMemory) {
            mMemoryCache.put(url, target);
        }
        // 缓存到 磁盘
        if (isCacheOnDisk) {
            mDiskCache.put(url, target);
        }
    }

    @Override
    public void clear() {
        mMemoryCache.clear();
        mDiskCache.clear();
    }

    public DisPlayer getDisplayer() {
        return mdisplayer;
    }

    public void setDisplayer(DisPlayer mdisplayer) {
        this.mdisplayer = mdisplayer;
    }

    public DiskCache getmDiskCache() {
        return mDiskCache;
    }

    public void setmDiskCache(DiskCache mDiskCache) {
        this.mDiskCache = mDiskCache;
    }

    public BaseMemoryCache getmMemoryCache() {
        return mMemoryCache;
    }

    public void setmMemoryCache(BaseMemoryCache mMemoryCache) {
        this.mMemoryCache = mMemoryCache;
    }

    public DisPlayer getMdisplayer() {
        return mdisplayer;
    }

    public void setMdisplayer(DisPlayer mdisplayer) {
        this.mdisplayer = mdisplayer;
    }

    public boolean isCacheInMemory() {
        return isCacheInMemory;
    }

    public void setCacheInMemory(boolean cacheInMemory) {
        isCacheInMemory = cacheInMemory;
    }

    public boolean isCacheOnDisk() {
        return isCacheOnDisk;
    }

    public void setCacheOnDisk(boolean cacheOnDisk) {
        isCacheOnDisk = cacheOnDisk;
    }

    public int getmCacheSize() {
        return mCacheSize;
    }

    public void setmCacheSize(int mCacheSize) {
        this.mCacheSize = mCacheSize;
    }
}
