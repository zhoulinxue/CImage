package zhx.cimage.displayer;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * Created by ${zhouxue} on 17/10/4 20: 17.
 * QQ:515278502
 */

public interface DisPlayer {
    /**
     * 显示图片
     * @param imageView
     * @param bitmap
     */
     public void display(ImageView imageView,Bitmap bitmap);

}
