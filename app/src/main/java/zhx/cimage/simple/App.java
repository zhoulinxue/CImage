package zhx.cimage.simple;

import android.app.Application;

import org.zhx.common.image.CImage;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CImage.init(this);
    }
}
