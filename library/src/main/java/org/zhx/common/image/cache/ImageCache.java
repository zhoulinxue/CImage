package org.zhx.common.image.cache;

import android.graphics.Bitmap;

import org.zhx.common.image.Target;

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
    public Target get(String url);

    /**
     *  缓存图片
     * @param url
     * @param target
     */
    public void put(String url, Target target);

    /**
     * 清除所有图片
     */

    public void clear();
}
