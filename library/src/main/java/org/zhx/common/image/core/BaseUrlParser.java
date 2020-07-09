package org.zhx.common.image.core;

import android.widget.ImageView;

import androidx.annotation.Nullable;
import org.zhx.common.image.cache.CacheConfig;
import org.zhx.common.image.displayer.BitmapContainer;
import org.zhx.common.image.utils.Log;

/**
 * Created by ${zhouxue} on 17/10/4 21: 17.
 * QQ:515278502
 */

public class BaseUrlParser implements UrlParser {
    private String url;
    private CacheConfig cacheConfig;
    private BitmapContainer container;
    private int logdingDrawable,errorDrawable;

    public BaseUrlParser(String url, CacheConfig cacheConfig, int logdingDrawable, int errorDrawable) {
        this.url = url;
        this.cacheConfig = cacheConfig;
        this.logdingDrawable = logdingDrawable;
        this.errorDrawable = errorDrawable;
    }

    public BaseUrlParser(String url, CacheConfig cacheConfig) {
        this.url = url;
        this.cacheConfig = cacheConfig;
        this.container=new BitmapContainer(url,cacheConfig);
    }

    @Override
    public BitmapContainer into(@Nullable ImageView imageView) {
        if(imageView==null){
            Log.e("image can not be null");
            throw new NullPointerException("imageView can not be null");
        }
        container.setImageView(imageView);
        if(logdingDrawable!=0){
            container.setLogdingDrawable(logdingDrawable);
        }
        if(errorDrawable!=0){
            container.setErrorDrawable(errorDrawable);
        }
        return container;
    }

    @Override
    public UrlParser error(int drawable) {
        this.errorDrawable=drawable;
        return this;
    }

    @Override
    public UrlParser loading(int drawable) {
        this.logdingDrawable=drawable;
        return this;
    }
}
