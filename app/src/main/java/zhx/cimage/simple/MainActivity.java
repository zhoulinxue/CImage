package zhx.cimage.simple;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import zhx.cimage.CImage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CImage.in(this).load("www.baidu.com").into(new ImageView(this)).withScalType(ImageView.ScaleType.CENTER_CROP).onloadLisenter();
    }
}
