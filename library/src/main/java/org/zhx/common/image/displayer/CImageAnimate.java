package org.zhx.common.image.displayer;

import android.graphics.Bitmap;
import android.widget.ImageView;

import org.zhx.common.image.Target;

/**
 * Created by ${zhouxue} on 17/10/5 14: 49.
 * QQ:515278502
 */

public interface CImageAnimate {

    /**
     * 回调动画
     * @param imageView
     * @param durationMillis
     */
    public void animate(ImageView imageView, Target target, int durationMillis);

}
