package org.zhx.common.image.displayer.DisplayerImpl;

import android.graphics.Bitmap;
import android.widget.ImageView;

import org.zhx.common.image.displayer.BitmapFillet;
import org.zhx.common.image.utils.Log;

/**
 * Created by ${zhouxue} on 17/10/5 14: 45.
 * QQ:515278502
 */

public  class RoundCornerDisplayer extends AnimateDisplayer{
    protected  int cornerRadius;
    protected  int margin;

    public RoundCornerDisplayer(int cornerRadius) {
        this(cornerRadius,0);
    }

    public RoundCornerDisplayer(int cornerRadius, int margin) {
        this.cornerRadius = cornerRadius;
        this.margin = margin;
    }

    @Override
    public void display(ImageView imageView, Bitmap bitmap) {
        Log.e("","显示 图片....");
         imageView.setImageBitmap(BitmapFillet.fillet(BitmapFillet.CornerType.ALL,bitmap,cornerRadius));
         animate(imageView,durationMillis);
    }

}
