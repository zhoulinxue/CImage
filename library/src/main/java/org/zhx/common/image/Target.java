package org.zhx.common.image;

import android.widget.ImageView;

import org.zhx.common.image.io.DataType;

public interface Target<T> {
    T getTarget();

    DataType getType();

    void bindView(ImageView imageView);

    void destory();
}
