package zhx.cimage.utils;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2017/11/14.
 */

public class BitmapUtils {


    public static int getSize(Bitmap bitmap) {
        return bitmap.getRowBytes() * bitmap.getHeight();
    }
}
