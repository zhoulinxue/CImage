package org.zhx.common.image.targets;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import org.zhx.common.image.Target;
import org.zhx.common.image.io.DataType;

public class SourceTarget implements Target<Drawable> {
    private Drawable drawable;

    public SourceTarget(Drawable drawable) {
        this.drawable = drawable;
    }

    @Override
    public Drawable getTarget() {
        return drawable;
    }

    @Override
    public DataType getType() {
        return DataType.DRAWABLE;
    }

    @Override
    public void bindView(ImageView imageView) {
        imageView.setImageDrawable(drawable);
    }
}
