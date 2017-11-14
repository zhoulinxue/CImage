package zhx.cimage.core;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import zhx.cimage.cache.CacheConfig;
import zhx.cimage.displayer.BitmapContainer;
import zhx.cimage.displayer.DisPlayer;
import zhx.cimage.utils.Log;

/**
 * Created by ${zhouxue} on 17/10/4 21: 17.
 * QQ:515278502
 */

public class BaseUrlParser implements UrlParser {
    private String url;
    private CacheConfig cacheConfig;

    public BaseUrlParser(String url, CacheConfig cacheConfig) {
        this.url = url;
        this.cacheConfig = cacheConfig;
    }

    @Override
    public BitmapContainer into(@Nullable ImageView imageView) {
        if(imageView==null){
            Log.e("image can not be null");
            throw new NullPointerException("imageView can not be null");
        }
        return new BitmapContainer(imageView,url,cacheConfig);
    }
}
