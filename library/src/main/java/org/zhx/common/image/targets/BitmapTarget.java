package org.zhx.common.image.targets;

import android.graphics.Bitmap;
import android.widget.ImageView;

import org.zhx.common.image.Target;
import org.zhx.common.image.io.DataType;
import org.zhx.common.image.utils.CLog;

public class BitmapTarget implements Target<Bitmap> {
    private Bitmap bitmap;

    public BitmapTarget(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    public Bitmap getTarget() {
        return bitmap;
    }

    @Override
    public DataType getType() {
        return DataType.BITMAP;
    }

    @Override
    public void bindView(ImageView imageView) {
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void destory() {
        if (bitmap != null&& !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap=null;
            CLog.e("lifcycle","bitmap recycled...");
        }
    }
}
