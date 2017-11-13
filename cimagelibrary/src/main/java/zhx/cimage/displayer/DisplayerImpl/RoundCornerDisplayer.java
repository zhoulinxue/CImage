package zhx.cimage.displayer.DisplayerImpl;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import zhx.cimage.displayer.BitmapFillet;
import zhx.cimage.utils.Log;

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
