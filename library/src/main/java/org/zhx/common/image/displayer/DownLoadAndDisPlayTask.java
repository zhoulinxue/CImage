package org.zhx.common.image.displayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.locks.ReentrantLock;

import org.zhx.common.image.cache.CacheConfig;
import org.zhx.common.image.callback.ImageLoadCallBack;
import org.zhx.common.image.exception.CImageException;
import org.zhx.common.image.loader.http.BaseImageDownloader;
import org.zhx.common.image.utils.Log;

/**
 * Created by Administrator on 2017/11/14.
 */

public class DownLoadAndDisPlayTask implements Runnable {
    private DisPlayer disPlayer;
    private CacheConfig cacheConfig;
    private String url;
    private Context mContext;
    private ImageView imageView;
    private ImageLoadCallBack callBack;
    private  Handler handler;


    public DownLoadAndDisPlayTask(Context mContext, String url, CacheConfig cacheConfig,ImageView imageView,ImageLoadCallBack callBack,Handler handler) {
        this.imageView = imageView;
        this.url = url;
        this.cacheConfig = cacheConfig;
        this.mContext=mContext;
        this.callBack=callBack;
        this.handler=handler;
    }

    @Override
    public void run() {
        ReentrantLock loadFromUriLock=ImageController.getInstance().preperToLoadUrl(url,imageView);
        loadFromUriLock.lock();
        Bitmap bitmap=null;
        try {
                Log.e("CImage","本地无缓存文件....从网络下载图片..."+url);
                InputStream stream=new BaseImageDownloader(mContext).getStream(url,null);
                if(stream!=null) {
                    bitmap = BitmapFactory.decodeStream(stream);
                    cacheConfig.getImageCache().put(url,bitmap);
                    disPlayer.display(imageView,bitmap);
                }
        } catch (IOException e) {
            e.printStackTrace();
            if(callBack!=null)
                callBack.onLoadingFailed(url,imageView,new CImageException(CImageException.FailType.IO_ERROR,e));
        }finally {
            loadFromUriLock.unlock();
        }
    }
}
