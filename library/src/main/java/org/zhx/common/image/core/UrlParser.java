package org.zhx.common.image.core;

import android.widget.ImageView;

import org.zhx.common.image.displayer.BitmapWorker;

/**
 * Created by ${zhouxue} on 17/10/4 21: 15.
 * QQ:515278502
 */

public interface UrlParser {
    public BitmapWorker into(ImageView imageView);

    public UrlParser error(int drawable);

    public UrlParser loading(int drawable);

}
