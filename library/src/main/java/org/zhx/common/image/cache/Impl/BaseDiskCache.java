package org.zhx.common.image.cache.Impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;

import org.zhx.common.image.cache.DiskCache;
import org.zhx.common.image.cache.FileMaster;
import org.zhx.common.image.utils.IoUtils;
import org.zhx.common.image.utils.MD5Util;

/**
 * Created by ${zhouxue} on 17/10/4 14: 13.
 * QQ:515278502
 */

public class BaseDiskCache   implements DiskCache, FileMaster{

    private int mCacheSize;

    public BaseDiskCache(int mCacheSize, String mCacheDir) {
        this.mCacheSize = mCacheSize;
        this.mCacheDir = mCacheDir;
    }

    @Override
    public Bitmap get(String url) {
        if(isexist(url)) {
            String name = encodefileName(url);
            String path = mCacheDir + File.separator + name;
            Log.e("CImage_缓存", "本地文件名:   " + name + " 本地路径:   " + path);
            return BitmapFactory.decodeFile(path);
        }else {
            return null;
        }
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        Log.e("CImage_缓存","网络地址"+url);
        if(isexist(url)){
            Log.e("CImage_缓存","缓存文件已存在"+encodefileName(url));
            return;
        }
        File panrent=null;
        if(!TextUtils.isEmpty(mCacheDir)){
            panrent=new File(mCacheDir);
            if(!panrent.exists()){
                panrent.mkdirs();
            }
        }
        FileOutputStream stream=null;
        try {
            File file=new File(panrent+File.separator+encodefileName(url));
             stream=new FileOutputStream(file.getAbsolutePath());
             Log.e("CImage_缓存",panrent+File.separator+encodefileName(url)+"缓存地址");
             bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            org.zhx.common.image.utils.Log.e("CImage_缓存","缓存到本地文件");
            IoUtils.closeSilently(stream);
        }
    }

    @Override
    public void clear() {
        File file=new File(mCacheDir);
        if(file.exists()&&file.isDirectory()){
            for (File child:file.listFiles()){
                child.delete();
            }
        }
    }
    protected String mCacheDir;
    @Override
    public boolean isexist(String url) {
        String path=encodefileName(url);
        if(TextUtils.isEmpty(path)){
            return false;
        }
        File file=new File(mCacheDir,path);
        return file.exists();
    }

    @Override
    public long fileSize(String path) {
        if(TextUtils.isEmpty(path)){
            return 0;
        }
        File file=new File(path);
        if(file.exists()) {
            return file.length();
        }
        return 0;
    }

    @Override
    public String encodefileName(String url) {
        return  MD5Util.encrypt(url);
    }

    public String getCacheDir() {
        return mCacheDir;
    }

    public void setCacheDir(String mCacheDir) {
        this.mCacheDir = mCacheDir;
    }
}
