package org.zhx.common.image.callback;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;

import org.zhx.common.image.Target;
import org.zhx.common.image.exception.CImageException;

import java.io.InputStream;

public abstract class BitmapCallback implements ImageLoadCallBack {
    private Context mContext;

    public BitmapCallback(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onLoadingStarted(String imageUri) {

    }

    @Override
    public void onLoadingFailed(String imageUri, CImageException failReason) {

    }

    @Override
    public final void onLoadingComplete(String imageUri, Target loadedImage) {
        Bitmap bitmap = null;
        switch (loadedImage.getType()) {
            case FILE:
                bitmap = BitmapFactory.decodeFile(loadedImage.getTarget() + "");
                break;
            case BITMAP:
                bitmap = (Bitmap) loadedImage.getTarget();
                break;
            case DRAWABLE:
                bitmap = ((BitmapDrawable) loadedImage.getTarget()).getBitmap();
                break;
            case SRC:
                bitmap = BitmapFactory.decodeResource(mContext.getResources(), (int) loadedImage.getTarget());
                break;
            case STREAM:
                bitmap = BitmapFactory.decodeStream((InputStream) loadedImage.getTarget());
                break;
        }
        onComplete(true, imageUri, bitmap);
    }

    protected abstract void onComplete(boolean isSuc, String imageUri, Bitmap bitmap);

    @Override
    public void onLoadingCancelled(String imageUri) {

    }

    @Override
    public void onLoadingErrorDrawable() {

    }
}
