package zhx.cimage.cache.Impl;

import android.graphics.Bitmap;
import android.text.TextUtils;

import zhx.cimage.Constant;
import zhx.cimage.cache.DiskCache;
import zhx.cimage.exception.CImageException;
import zhx.cimage.cache.CacheConfig;
import zhx.cimage.cache.ImageCache;
import zhx.cimage.displayer.DisPlayer;
import zhx.cimage.utils.BitmapUtils;
import zhx.cimage.utils.Log;

/**
 * Created by ${zhouxue} on 17/10/4 13: 55.
 * QQ:515278502
 *
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
    private boolean isCacheInMemory=true;
    /**
     * 是否存在 硬盘
     */
    private boolean isCacheOnDisk=true;

    public BaseImageCache(int mCacheSize, boolean isCacheInMemory, boolean isCacheOnDisk) {
        this.mCacheSize = mCacheSize;
        this.isCacheInMemory = isCacheInMemory;
        this.isCacheOnDisk = isCacheOnDisk;
        init();
    }
    private void init(){
        if(isCacheInMemory()){
            mMemoryCache=new BaseMemoryCache(mCacheSize);
        }
        if(isCacheOnDisk()){
            mDiskCache=new BaseDiskCache(Constant.mDiskMaxSize,Constant.CocaheDir);
        }
    }

    @Override
    public final Bitmap get(String url) {
        Bitmap bitmap=null;
        if(TextUtils.isEmpty(url)){
            CImageException.throwNullKey();
        }
        bitmap=mMemoryCache.get(url);
        if(bitmap==null){
            Log.e("CImage", "内存中没有图片.....从缓存文件中取");
            bitmap=mDiskCache.get(url);
        }
        return bitmap;
    }


    @Override
    public final void put(String url, Bitmap bitmap) {
        if(TextUtils.isEmpty(url)){
            CImageException.throwNullKey();
            return;
        }
        if(bitmap==null){
            CImageException.throwNullValus();
            return;
        }
        // 缓存到内存
        if(isCacheInMemory){
            mMemoryCache.put(url, bitmap);
        }
        // 缓存到 磁盘
        if(isCacheOnDisk){
            mDiskCache.put(url,bitmap);
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
