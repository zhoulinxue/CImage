package zhx.cimage.simple;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import zhx.cimage.CImage;
import zhx.cimage.exception.CImageException;
import zhx.cimage.callback.ImageLoadCallBack;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CImage.in(this).load("www.baidu.com").into(new ImageView(this)).withScalType(ImageView.ScaleType.CENTER_CROP).onloadLisenter(new ImageLoadCallBack() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, CImageException failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }
}
