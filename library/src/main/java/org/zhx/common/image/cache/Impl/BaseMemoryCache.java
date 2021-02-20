package org.zhx.common.image.cache.Impl;

import android.graphics.Bitmap;
import android.util.LruCache;

import org.zhx.common.image.Target;
import org.zhx.common.image.cache.ImageCache;
import org.zhx.common.image.utils.CLog;

/**
 * Created by ${zhouxue} on 17/10/4 17: 59.
 * QQ:515278502
 * 内存缓存类
 */
public class BaseMemoryCache implements ImageCache {
    private int mCacheSize;
    LruCache<String,Target> bitmapLruCache;

    public BaseMemoryCache(int mCacheSize) {
        this.mCacheSize = mCacheSize;
        bitmapLruCache=new LruCache<String,Target>(mCacheSize);
    }

    @Override
    public Target get(String url) {
        synchronized (this) {
            return bitmapLruCache.get(url);
        }
    }

    @Override
    public void put(String url, Target target) {
        CLog.e("缓存到内存");
        bitmapLruCache.put(url,target);
    }

    @Override
    public void clear() {
        bitmapLruCache.evictAll();
    }

}

