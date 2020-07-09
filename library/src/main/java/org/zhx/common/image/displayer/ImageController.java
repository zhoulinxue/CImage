package org.zhx.common.image.displayer;

import android.widget.ImageView;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ${zhouxue} on 17/10/4 20: 17.
 * QQ:515278502
 */

public class ImageController {
    private static ImageController minstance;
    public static  ImageController getInstance(){
        if(minstance==null){
            minstance=new ImageController();
        }
        return minstance;
    }
    private final Map<String, ReentrantLock> uriLocks = new WeakHashMap<String, ReentrantLock>();
    private final Map<Integer, String> cacheKeysForImageAwares = Collections
            .synchronizedMap(new HashMap<Integer, String>());
   public ReentrantLock  preperToLoadUrl(String url, ImageView imageView){
       ReentrantLock loadFromUriLock=uriLocks.get(url);
       if(loadFromUriLock==null){
           loadFromUriLock=new ReentrantLock();
           uriLocks.put(url,loadFromUriLock);
       }
       cacheKeysForImageAwares.put(imageView.hashCode(),url);
       imageView.setTag(url);
      return  loadFromUriLock;
   }

    public boolean isDisplay(ImageView imageView, String url) {
       String valus=cacheKeysForImageAwares.get(imageView.hashCode());
       return url.equals(valus);
    }
}
