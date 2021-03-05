package org.zhx.common.image.targets;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import org.zhx.common.image.Target;
import org.zhx.common.image.io.DataType;
import org.zhx.common.image.utils.CLog;

public class FileTarget implements Target<String> {
    private String path;
    private Bitmap bitmap;

    public FileTarget(String path) {
        this.path = path;
    }

    @Override
    public String getTarget() {
        return path;
    }

    @Override
    public DataType getType() {
        return DataType.FILE;
    }

    @Override
    public void bindView(ImageView imageView) {
        bitmap=BitmapFactory.decodeFile(path);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void destory() {
       if(bitmap!=null){
           bitmap.recycle();
           CLog.e("lifcycle","bitmap recycled...");
       }
    }
}
