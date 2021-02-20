package org.zhx.common.image.displayer.DisplayerImpl;

import android.graphics.Bitmap;
import android.widget.ImageView;

import org.zhx.common.image.Target;
import org.zhx.common.image.displayer.DisPlayer;

/**
 * Created by ${zhouxue} on 17/10/4 20: 23.
 * QQ:515278502
 */

public class BaserDisplayer implements DisPlayer {

    @Override
    public void display(ImageView imageView, Target target) {
        target.bindView(imageView);
    }

}
