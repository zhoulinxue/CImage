package zhx.cimage.cache.Impl;

import android.graphics.Bitmap;
import android.util.LruCache;

import zhx.cimage.cache.ImageCache;

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
        bitmapLruCache.put(url,bitmap);
    }

    @Override
    public void clear() {
        bitmapLruCache.evictAll();
    }

}

