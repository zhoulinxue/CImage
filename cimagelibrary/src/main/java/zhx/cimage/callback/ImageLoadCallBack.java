package zhx.cimage.callback;

import android.graphics.Bitmap;
import android.view.View;

import zhx.cimage.Exception.CImageException;

/**
 * Created by zhouxue on 2017/4/20.
 * QQ 515278502
 */


public interface ImageLoadCallBack {
    /**
     * called when image loading task was started
     *
     * @param imageUri Loading image URI
     * @param view     View for image
     */
    void onLoadingStarted(String imageUri, View view);

    /**
     * called when an error was occurred during image loading
     *
     * @param imageUri   Loading image URI
     * @param view       View for image. Can be <b>null</b>.
     * @param failReason {@linkplain zhx.cimage.Exception.CImageException The reason} why image
     *                   loading was failed
     */
    void onLoadingFailed(String imageUri, View view, CImageException failReason);

    /**
     * Is called when image is loaded successfully (and displayed in View if one was specified)
     *
     * @param imageUri    Loaded image URI
     * @param view        View for image. Can be <b>null</b>.
     * @param loadedImage Bitmap of loaded and decoded image
     */
    void onLoadingComplete(String imageUri, View view, Bitmap loadedImage);

    /**
     * Is called when image loading task was cancelled because View for image was reused in newer task
     *
     * @param imageUri Loading image URI
     * @param view     View for image. Can be <b>null</b>.
     */
    void onLoadingCancelled(String imageUri, View view);
}