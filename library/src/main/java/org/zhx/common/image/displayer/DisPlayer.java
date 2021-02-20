package org.zhx.common.image.displayer;

import android.graphics.Bitmap;
import android.widget.ImageView;

import org.zhx.common.image.Target;

/**
 * Created by ${zhouxue} on 17/10/4 20: 17.
 * QQ:515278502
 */

public interface DisPlayer<T> {
    /**
     * 显示图片
     *
     * @param imageView
     * @param target
     */
    public void display(ImageView imageView, Target<T> target);

}
