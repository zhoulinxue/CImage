package org.zhx.common.image.displayer;

import android.util.Log;
import android.widget.ImageView;

import org.zhx.common.image.CImage;
import org.zhx.common.image.core.Worker;
import org.zhx.common.image.utils.CLog;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by ${zhouxue} on 17/10/4 20: 17.
 * QQ:515278502
 */

public class ImageController {
    private static ImageController minstance;

    public static ImageController getInstance() {
        if (minstance == null) {
            minstance = new ImageController();
        }
        return minstance;
    }

    private final Map<String, ReentrantLock> uriLocks = new WeakHashMap<String, ReentrantLock>();
    private final Map<Integer, String> cacheKeysForImageAwares = Collections
            .synchronizedMap(new HashMap<Integer, String>());
    private static Map<String, DownLoadWorker> hashMap = new ConcurrentHashMap();

    public ReentrantLock preperToLoadUrl(String url) {
        ReentrantLock loadFromUriLock = uriLocks.get(url);
        if (loadFromUriLock == null) {
            loadFromUriLock = new ReentrantLock();
            uriLocks.put(url, loadFromUriLock);
        }
        return loadFromUriLock;
    }

    public void cache(String url, ImageView imageView) {
        CLog.e("匹配緩存" + imageView.hashCode() + "!!!!" + url);
        cacheKeysForImageAwares.put(imageView.hashCode(), url);
        imageView.setTag(url);
    }

    public boolean isDisplay(ImageView imageView, String url) {
        CLog.e("匹配" + imageView.hashCode() + "!!!!" + url);
        String valus = cacheKeysForImageAwares.get(imageView.hashCode());
        return url.equals(valus);
    }

    public DownLoadWorker getDownLoadWorker(String url) {
        return hashMap.get(url);
    }

    public void putDownLoadWorker(String url, DownLoadWorker worker) {
        hashMap.put(url, worker);
    }
}
