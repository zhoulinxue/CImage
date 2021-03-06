package org.zhx.common.image.callback;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import org.zhx.common.image.Target;
import org.zhx.common.image.exception.CImageException;

/**
 * Created by zhouxue on 2017/4/20.
 * QQ 515278502
 */


public interface ImageLoadCallBack {
    /**
     * 开始下载/called when image loading task was started
     *
     * @param imageUri Loading image URI
     */
    void onLoadingStarted(String imageUri);

    /**
     * 下载失败/called when an error was occurred during image loading
     *
     * @param imageUri   Loading image URI
     * @param failReason {@linkplain org.zhx.common.image.exception.CImageException The reason} why image
     *                   loading was failed
     */
    void onLoadingFailed(String imageUri, CImageException failReason);

    /**
     * 下载完成/Is called when image is loaded successfully (and displayed in View if one was specified)
     *
     * @param imageUri    Loaded image URI
     * @param loadedImage Bitmap of loaded and decoded image
     */
    void onLoadingComplete(String imageUri, Target loadedImage);

    /**
     * 下载取消/Is called when image loading task was cancelled because View for image was reused in newer task
     *
     * @param imageUri Loading image URI
     */
    void onLoadingCancelled(String imageUri);

    void onLoadingErrorDrawable(String imageUri);
}
