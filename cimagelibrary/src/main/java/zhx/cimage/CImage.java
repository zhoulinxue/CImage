package zhx.cimage;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.widget.ImageView;

/**
 * Created by zhouxue on 2017/4/20.
 * QQ 515278502
 * 工具对象类 自己写一个轻量级的图片缓存和处理 项目。
 */


public class CImage {
    private static  String TAG=CImage.class.getSimpleName();
    private static  CImage mCImage;
    private static CImage getInstance(){
        if(mCImage==null){
            mCImage=new CImage();
        }
        return mCImage;
    }

    /**
     *
     * @param context 上下文
     * @return CImage
     */
    public static CImage in(Context context){

        return getInstance();
    }

    /**
     *
     * @param activity   in activity/在activity 中使用
     * @return CImage
     */
    public static CImage in(Activity activity){

        return getInstance();
    }

    /**
     *
     * @param fragment in fragment/在fragemnt 中使用
     * @return CImage
     */

    public static CImage in(Fragment fragment){

        return getInstance();
    }

    /**
     *
     * @param fragment in  android.support.v4.app.Fragment / 在 v4 包中使用
     * @return CImage
     */
    public static CImage in(android.support.v4.app.Fragment fragment){

        return getInstance();
    }

    /**
     *
     * @param url  load url /下载地址(http:  file:  data/data)
     * @return
     */
    public static CImage load(String url){

        return  getInstance();
    }

    /**
     *
     * @param src  local src /本地资源 (R.drawable.xx  ; R.mipmap.xx)
     * @return
     */
    public static CImage load(int  src){

        return  getInstance();
    }

    /**
     *
     * @param imageView  imagView
     * @return
     */

    public static CImage into(ImageView imageView){
        return getInstance();
    }

    public static  CImage withScalType(ImageView.ScaleType scaleType){

        return getInstance();
    }

    public static  CImage onloadLisenter(){
        return getInstance();
    }


}
