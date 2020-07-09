package org.zhx.common.image.displayer;

import android.widget.ImageView;

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
    public void animate(ImageView imageView, int durationMillis);

}
