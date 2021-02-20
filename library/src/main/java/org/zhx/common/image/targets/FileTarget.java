package org.zhx.common.image.targets;

import android.graphics.BitmapFactory;
import android.widget.ImageView;

import org.zhx.common.image.Target;
import org.zhx.common.image.io.DataType;

public class FileTarget implements Target<String> {
    private String path;

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
        imageView.setImageBitmap(BitmapFactory.decodeFile(path));
    }
}
