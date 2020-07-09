package org.zhx.common.image.cache.Impl;

import android.graphics.Bitmap;
import android.util.LruCache;

import org.zhx.common.image.cache.ImageCache;
import org.zhx.common.image.utils.Log;

/**
 * Created by ${zhouxue} on 17/10/4 17: 59.
 * QQ:515278502
 * 内存缓存类
 */
public class BaseMemoryCache implements ImageCache {
    private int mCacheSize;
    LruCache<String,Bitmap> bitmapLruCache;

    public BaseMemoryCache(int mCacheSize) {
        this.mCacheSize = mCacheSize;
        bitmapLruCache=new LruCache<String,Bitmap>(mCacheSize);
    }

    @Override
    public Bitmap get(String url) {
        synchronized (this) {
            return bitmapLruCache.get(url);
        }
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        Log.e("CImage_缓存","缓存到内存");
        bitmapLruCache.put(url,bitmap);
    }

    @Override
    public void clear() {
        bitmapLruCache.evictAll();
    }

}

