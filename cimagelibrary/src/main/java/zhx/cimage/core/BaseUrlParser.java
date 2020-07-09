package zhx.cimage.core;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ImageView;

import java.util.concurrent.ConcurrentHashMap;

import zhx.cimage.cache.CacheConfig;
import zhx.cimage.displayer.BitmapContainer;
import zhx.cimage.utils.Log;

/**
 * Created by ${zhouxue} on 17/10/4 21: 17.
 * QQ:515278502
 */

public class BaseUrlParser implements UrlParser {
    private String url;
    private CacheConfig cacheConfig;
    private BitmapContainer container;

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
        return container;
    }
}
