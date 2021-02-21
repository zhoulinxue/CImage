package org.zhx.common.image.core;

import android.widget.ImageView;

import org.zhx.common.image.callback.ImageLoadCallBack;
import org.zhx.common.image.displayer.DisPlayer;

/**
 * Created by ${zhouxue} on 17/10/4 21: 15.
 * QQ:515278502
 */

public interface Worker {
    public void from(String url);

    public Worker error(int drawable);

    public Worker loading(int drawable);

    public Worker setDisPlayer(DisPlayer disPlayer);

    public void setCallback(ImageLoadCallBack callback);

}
