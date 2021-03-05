package org.zhx.common.image.displayer;

import android.widget.ImageView;

import org.zhx.common.image.utils.CLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
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
    private final Map<String, List<String>> links = Collections
            .synchronizedMap(new HashMap<String, List<String>>());

    public ReentrantLock preperToLoadUrl(String url) {
        ReentrantLock loadFromUriLock = uriLocks.get(url);
        if (loadFromUriLock == null) {
            loadFromUriLock = new ReentrantLock();
            uriLocks.put(url, loadFromUriLock);
        }
        return loadFromUriLock;
    }

    public void cache(String url, ImageView imageView) {
        String hascode = imageView.hashCode()+"";
        List<String> sparseArray = links.get(url);
        if (sparseArray == null) {
            sparseArray = new ArrayList<>();
            links.put(url, sparseArray);
        }
        if (!sparseArray.contains(hascode)) {
            sparseArray.add(hascode + "");
        }
        cacheKeysForImageAwares.put(imageView.hashCode(), url);
    }

    public boolean isUrlLinked(String url) {
        List<String> linksView = links.get(url);
        CLog.e("lifcycle", "linkedSize: "+ (linksView != null?linksView.size()+"@@":"0！"));
        return linksView != null && linksView.size() != 0;
    }

    public boolean isDisplay(ImageView imageView, String url) {
        String valus = cacheKeysForImageAwares.get(imageView.hashCode());
        return url.equals(valus);
    }

    public int remove(String url, String hashCode) {
        boolean islinked = isUrlLinked(url);
        CLog.e("lifcycle", "isLinked: "+islinked);
        if (islinked) {
            links.get(url).remove(hashCode);
            return links.get(url).size();
        }
        return 0;
    }
}
