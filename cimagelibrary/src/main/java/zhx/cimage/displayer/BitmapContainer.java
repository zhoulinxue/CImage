package zhx.cimage.displayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.locks.ReentrantLock;

import zhx.cimage.CImage;
import zhx.cimage.Constant;
import zhx.cimage.cache.CacheConfig;
import zhx.cimage.callback.ImageLoadCallBack;
import zhx.cimage.displayer.DisplayerImpl.AnimateDisplayer;
import zhx.cimage.exception.CImageException;
import zhx.cimage.loader.http.BaseImageDownloader;
import zhx.cimage.utils.Log;

/**
 * Created by ${zhouxue} on 17/10/4 20: 55.
 * QQ:515278502
 */

public class BitmapContainer extends AsyncTask<String,String,Bitmap> {
    private ImageView imageView;
    private String url;
    private ImageLoadCallBack callBack;
    private Context mContext;
    private DisPlayer disPlayer;
    private CacheConfig cacheConfig;

    public BitmapContainer() {
    }

    public BitmapContainer(String url, CacheConfig cacheConfig) {
        this.url = url;
        this.cacheConfig = cacheConfig;
    }

    public BitmapContainer setCallBack(ImageLoadCallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    /**
     * 开始加载...
     */
    public void end(){
       execute(url);
    }

    /**
     * 开始加载...
     */
    public void end(String...param){
        ReentrantLock loadFromUriLock=ImageController.getInstance().preperToLoadUrl(url,imageView);
        if(loadFromUriLock.isLocked()) {
            Log.e("loading...wait");
        }
         execute(url,param[0]);
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        imageView.setImageBitmap(null);
        if(callBack!=null)
            callBack.onLoadingStarted(url,imageView);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        ReentrantLock loadFromUriLock=ImageController.getInstance().preperToLoadUrl(url,imageView);
        loadFromUriLock.lock();
        Bitmap   bitmap=null;
        try {
            bitmap = cacheConfig.getImageCache().get(url);
        if(bitmap!=null) {
            Log.e("CImage","从本地缓存文件获取图片成功...."+url);
            return bitmap;
        } else {
            Log.e("CImage","本地无缓存文件....从网络下载图片"+url);
            InputStream stream=new BaseImageDownloader(mContext).getStream(url,null);
            if(stream!=null) {
                bitmap = BitmapFactory.decodeStream(stream);
            }
         }
        } catch (IOException e) {
            if(callBack!=null)
                callBack.onLoadingFailed(url,imageView,new CImageException(CImageException.FailType.IO_ERROR,e));
        }finally {
               loadFromUriLock.unlock();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        cacheConfig.getImageCache().put(url,bitmap);
        if(callBack!=null)
            callBack.onLoadingComplete(url,imageView,bitmap);
        else {
            if(disPlayer==null) {
                Log.e("CImage","创建显示器....");
                disPlayer = new AnimateDisplayer();
            }
            if(ImageController.getInstance().isDisplay(imageView,url)) {
                disPlayer.display(imageView, bitmap);
            }else {
                imageView.setImageBitmap(null);
            }
        }
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
        mContext=imageView.getContext();
    }

    public BitmapContainer setDisPlayer(DisPlayer disPlayer) {
        this.disPlayer = disPlayer;
        return this;
    }

    public void smallend() {
        end(Constant.isSmall);
    }
}
