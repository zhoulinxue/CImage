package zhx.cimage.displayer.DisplayerImpl;

import android.graphics.Bitmap;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

import zhx.cimage.displayer.CImageAnimate;
import zhx.cimage.displayer.DisPlayer;

/**
 * Created by ${zhouxue} on 17/10/5 15: 03.
 * QQ:515278502
 */

public class AnimateDisplayer implements DisPlayer,CImageAnimate{
    /**
     * 动画持续时间
     */
    protected   int durationMillis=1000;

    public AnimateDisplayer() {
    }

    public AnimateDisplayer(int durationMillis) {
        this.durationMillis = durationMillis;
    }
    @Override
    public void display(ImageView imageView, Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
        animate(imageView,durationMillis);
    }

    @Override
    public void animate(ImageView imageView, int durationMillis) {
        if (imageView != null) {
            AlphaAnimation fadeImage = new AlphaAnimation(0, 1);
            fadeImage.setDuration(durationMillis);
            fadeImage.setInterpolator(new DecelerateInterpolator());
            imageView.startAnimation(fadeImage);
        }
    }

    public int getDurationMillis() {
        return durationMillis;
    }

    public void setDurationMillis(int durationMillis) {
        this.durationMillis = durationMillis;
    }
}
