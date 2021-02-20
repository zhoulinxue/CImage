package org.zhx.common.image.displayer.DisplayerImpl;

import android.graphics.Bitmap;
import android.widget.ImageView;

import org.zhx.common.image.Target;
import org.zhx.common.image.displayer.BitmapFillet;
import org.zhx.common.image.utils.CLog;

/**
 * Created by ${zhouxue} on 17/10/5 14: 45.
 * QQ:515278502
 */

public class RoundCornerDisplayer extends AnimateDisplayer {
    protected int cornerRadius;
    protected int margin;

    public RoundCornerDisplayer(int cornerRadius) {
        this(cornerRadius, 0);
    }

    public RoundCornerDisplayer(int cornerRadius, int margin) {
        this.cornerRadius = cornerRadius;
        this.margin = margin;
    }

    @Override
    public void display(ImageView imageView, Target target) {
        CLog.e("", "显示 图片....");
        animate(imageView, target, durationMillis);
    }

    @Override
    public void animate(ImageView imageView, Target target, int durationMillis) {
        super.animate(imageView, target, durationMillis);
    }
}
