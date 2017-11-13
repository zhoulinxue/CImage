package zhx.cimage.cache;

import android.graphics.Bitmap;

/**
 * Created by ${zhouxue} on 17/10/4 13: 50.
 * QQ:515278502
 */

public interface ImageCache {
    /**
     *  获取缓存图片
     * @param url
     * @return
     */
    public Bitmap get(String url);

    /**
     *  缓存图片
     * @param url
     * @param bitmap
     */
    public void put(String url,Bitmap bitmap);

    /**
     * 清除所有图片
     */

    public void clear();
}
