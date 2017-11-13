package zhx.cimage.displayer;

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
   public ReentrantLock  preperToLoadUrl(String url){
       ReentrantLock loadFromUriLock=uriLocks.get(url);
       if(loadFromUriLock==null){
           loadFromUriLock=new ReentrantLock();
           uriLocks.put(url,loadFromUriLock);
       }
      return  loadFromUriLock;
   }
}
