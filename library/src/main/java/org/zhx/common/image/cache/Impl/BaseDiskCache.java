package org.zhx.common.image.cache.Impl;

import android.graphics.Bitmap;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;

import org.zhx.common.image.Target;
import org.zhx.common.image.targets.FileTarget;
import org.zhx.common.image.cache.DiskCache;
import org.zhx.common.image.cache.FileMaster;
import org.zhx.common.image.utils.CLog;
import org.zhx.common.image.utils.IoUtils;
import org.zhx.common.image.utils.MD5Util;

/**
 * Created by ${zhouxue} on 17/10/4 14: 13.
 * QQ:515278502
 */

public class BaseDiskCache implements DiskCache, FileMaster {

    private int mCacheSize;

    public BaseDiskCache(int mCacheSize, String mCacheDir) {
        this.mCacheSize = mCacheSize;
        this.mCacheDir = mCacheDir;
    }

    @Override
    public Target get(String url) {
        CLog.e("正在磁盘获取...");
        if (isexist(url)) {
            String name = encodefileName(url);
            String path = mCacheDir + File.separator + name;
            CLog.e("磁盘获取成功");
            return new FileTarget(path);
        } else {
            CLog.e("磁盘获取失败...");
            return null;
        }
    }

    @Override
    public void put(String url, Target target) {
        CLog.e("网络地址" + url);
        if (isexist(url)) {
            CLog.e("缓存文件已存在" + encodefileName(url));
            return;
        }
        File parents = null;
        if (!TextUtils.isEmpty(mCacheDir)) {
            parents = new File(mCacheDir);
            if (!parents.exists()) {
                parents.mkdirs();
            }
        }
        if (target != null)
            saveTarget(parents, target, url);
    }

    private void saveTarget(File parents, Target target, String url) {
        switch (target.getType()) {
            case BITMAP:
                Bitmap bitmap = (Bitmap) target.getTarget();
                FileOutputStream stream = null;
                try {
                    File file = new File(parents + File.separator + encodefileName(url));
                    stream = new FileOutputStream(file.getAbsolutePath());
                    CLog.e("缓存地址" + parents + File.separator + encodefileName(url));
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    CLog.e("缓存到本地文件");
                    IoUtils.closeSilently(stream);
                }
                break;
        }
    }

    @Override
    public void clear() {
        File file = new File(mCacheDir);
        if (file.exists() && file.isDirectory()) {
            for (File child : file.listFiles()) {
                child.delete();
            }
        }
    }


    protected String mCacheDir;

    @Override
    public boolean isexist(String url) {
        String path = encodefileName(url);
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        File file = new File(mCacheDir, path);
        return file.exists();
    }

    @Override
    public long fileSize(String path) {
        if (TextUtils.isEmpty(path)) {
            return 0;
        }
        File file = new File(path);
        if (file.exists()) {
            return file.length();
        }
        return 0;
    }

    @Override
    public String encodefileName(String url) {
        return MD5Util.encrypt(url);
    }

    public String getCacheDir() {
        return mCacheDir;
    }

    public void setCacheDir(String mCacheDir) {
        this.mCacheDir = mCacheDir;
    }
}
