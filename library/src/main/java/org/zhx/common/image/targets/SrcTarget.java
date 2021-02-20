package org.zhx.common.image.targets;

import android.widget.ImageView;

import org.zhx.common.image.Target;
import org.zhx.common.image.io.DataType;

public class SrcTarget implements Target<Integer> {
    private int src;

    public SrcTarget(int src) {
        this.src = src;
    }

    @Override
    public Integer getTarget() {
        return src;
    }

    @Override
    public DataType getType() {
        return DataType.SRC;
    }

    @Override
    public void bindView(ImageView imageView) {
        imageView.setImageResource(src);
    }
}
