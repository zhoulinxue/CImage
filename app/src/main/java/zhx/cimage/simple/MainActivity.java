package zhx.cimage.simple;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import zhx.cimage.CImage;
import zhx.cimage.cache.CacheConfig;
import zhx.cimage.displayer.DisplayerImpl.AnimateDisplayer;
import zhx.cimage.displayer.DisplayerImpl.RoundCornerDisplayer;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CImage.init(CacheConfig.getInstance());
        mListView= (ListView) findViewById(R.id.listView);
        TextAdapter mAdapter=new TextAdapter(IMAGES,this);
        mListView.setAdapter(mAdapter);
    }

    public static final String[] IMAGES = new String[] {
            // Heavy images
            "http://scimg.jb51.net/allimg/150624/14-150624143232592.jpg",
            "http://www.wmgm.org/uploads/allimg/130114/6-130114140P5Y0.jpg",
            "http://dynamic-image.yesky.com/1080x-/uploadImages/2014/218/42/XZ29Z22K800G.jpg",
            "http://www.wmgm.org/uploads/allimg/130114/6-130114140P5Y0.jpg",
            "http://mpic.tiankong.com/786/db8/786db8df99d52a7aabcc7be97728e6c9/640.jpg",
            "http://mpic.tiankong.com/05a/fda/05afdae70cf4dcf046ac9ddae51db8e4/640.jpg",
            "https://www.fuhaodq.com/d/file/201704/07/iyyvnzwblid116756.jpg",
            "http://img3.xiazaizhijia.com/walls/20160222/mid_e89255b53a54e10.jpg",
            "http://dynamic-image.yesky.com/1080x-/uploadImages/2014/218/39/2HNKZIHTU2B3.jpg",
            "http://dynamic-image.yesky.com/1080x-/uploadImages/2014/218/37/E2W54W0QWP54.jpg",
            "http://dynamic-image.yesky.com/1080x-/uploadImages/2014/218/08/N7N10C1879DS.jpg",
            "http://old.bz55.com/uploads/allimg/120906/1-120Z6112945.jpg",
            "http://old.bz55.com/uploads/allimg/120906/1-120Z6112945-50.jpg",
            "http://old.bz55.com/uploads/allimg/120906/1-120Z6112946.jpg",
            "http://old.bz55.com/uploads/allimg/120906/1-120Z6112947.jpg",
            "http://old.bz55.com/uploads/allimg/120906/1-120Z6112951.jpg",
            "http://old.bz55.com/uploads/allimg/120906/1-120Z6112943.jpg",
            "http://old.bz55.com/uploads/allimg/120906/1-120Z6112944.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141218/3-14121Q04153.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141217/3-14121G11050.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141216/3-141216111621.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141216/3-141216101A7.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141215/4-141215145939.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141215/3-141215113955.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141215/3-141215101601.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141213/3-141213110013.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141213/3-141213102019.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141213/3-141213101637.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141212/4-141212163240.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141212/4-141212161012.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141212/3-141212101105.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141212/3-141212100548.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141211/4-141211150I9.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141211/4-141211150542.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141211/4-141211150300.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141211/4-141211144F3.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141211/3-141211105Z4.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141211/3-141211103S2.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141211/3-141211103108.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141210/4-141210141R6.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141210/4-141210141342.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141209/3-141209110215.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141208/1-14120Q04511.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141206/3-141206100234.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141205/3-141205101344.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141204/4-141204141616.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141204/4-141204141311.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141204/4-141204134252.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141204/3-141204105040.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141204/3-141204101947.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141203/4-141203154006.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141203/4-141203153302.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141203/4-141203152Z6.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141203/4-141203151T7.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141203/4-141203151540.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141203/4-141203151216.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141203/4-141203150K5.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141203/4-141203150528.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141203/4-141203150344.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141203/4-141203101503.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141202/3-141202111R6.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141202/3-141202111452.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141202/3-141202111215.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141202/3-141202105F8.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141202/3-141202104G0.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141202/1-141202102521.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141202/1-141202102118.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141201/4-141201161T6.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141201/4-141201161649.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141201/1-141201112524.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141201/1-141201105551.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141201/1-141201104121.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141129/4-141129161033.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141128/4-14112Q64635.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141127/4-14112G63I9.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141127/4-14112G63202.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141127/4-14112G11Z7.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141125/4-141125154942.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141125/4-141125154608.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141125/4-141125105418.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141125/4-141125105130.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141125/4-141125104949.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141125/4-141125104544.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141125/4-141125104105.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141125/4-141125103S8.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141125/4-141125103A6.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141125/4-141125103338.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141125/4-141125100K8.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141125/4-141125095432.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141124/4-141124150I0.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141124/4-141124145R7.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141124/4-141124145028.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141122/3-141122102239.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141121/4-141121164349.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141121/4-141121164215.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141121/4-141121164027.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141121/4-141121160358.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141120/4-1411201F506.jpg",
            "http://4493bz.1985t.com/uploads/allimg/141120/4-1411201F308.jpg",
            // Light images
            "http://img1.ph.126.net/nfDDYU3FWu6mp85DtNgzjA==/6608848834724682034.png",
            "http://img1.ph.126.net/UiNtd3QROB4RmEr_9EEEqA==/6608568459259601203.png",
            "http://img2.ph.126.net/uZQgzN5ey80HBG9dulpInw==/6608912606399096096.png",
            "http://img2.ph.126.net/_ng63OX-j6v0oumEcraMeQ==/6608570658282856712.png",
            "http://img1.ph.126.net/2avc4jVg-Rsu8dZCi9l43w==/6608917004445607153.png",
            "http://img2.ph.126.net/f_wnJkSZdXPSXdsb1CT47A==/6608881820073516830.png",
            "http://img2.ph.126.net/RcSviuikvLBBtRqyS7gmiQ==/6608884019096772371.png",
            "http://img1.ph.126.net/DQm0XeVyHDWjHYPvBV4R5A==/6608571757794484492.png",
            "http://img2.ph.126.net/vhFPl1MFTeag70-h_tsKMQ==/6608505787096819127.png",
            "http://img2.ph.126.net/ZqhuxUntgPnpU0YQCAPtDA==/6608822446445617067.png",
            "http://img0.ph.126.net/EEe9fP19jlDQ1uSr91hLYA==/6608901611282816979.png",
            "http://img1.ph.126.net/PXxajfnW7G_G0tLx809kSQ==/6608902710794444740.png",
            "http://img2.ph.126.net/1VmrxZB3NNXU2IZb1XjIlg==/6608689405538656073.png",
            "http://img1.ph.126.net/tPK5AEBla7JN645mGYw7lw==/6608901611282816974.png",
            "http://img2.ph.126.net/3PNDu33VXaOXdOi9hFXDbQ==/6608884019096772377.png",
            "http://img0.ph.126.net/9Agtqu4WG4Lc8z97bW3gew==/6608569558771228980.png",
            "http://img0.ph.126.net/Vw8_CIvWR4TA160u1Pq3hQ==/6608189127749153229.png",
            "http://img1.ph.126.net/N6xjLizafv6686eat9r0Ug==/6608849934236309782.png",
            "http://img0.ph.126.net/0L3yf_xv7i6ow7s1ZXngVQ==/6608475000771240393.png",
            "http://img2.ph.126.net/-nCKbweYGGK69v9pgouEuw==/6608475000771240392.png",
            "http://img1.ph.126.net/zCtsjtrdP47L5HpnjOqIUg==/6608412328609588333.png",
            "http://img1.ph.126.net/DSw-E9t9j0prlvQbTNcjJw==/6608914805422351636.png",
            "http://img2.ph.126.net/Gm6QHfSj-jOQStiEbC-qfA==/6608466204678218150.png",
            "http://www.flashyl.com/upFiles/tpsc/pngsc/123.xdssmn/01.png",
            // Special cases
            "file:///sdcard/Universal Image Loader @#&=+-_.,!()~'%20.png", // Image from SD card with encoded symbols
            "assets://Living Things @#&=+-_.,!()~'%20.jpg", // Image from assets
            "drawable://" + R.mipmap.ic_launcher, // Image from drawables
            "http://upload.wikimedia.org/wikipedia/ru/b/b6/Как_кот_с_мышами_воевал.png", // Link with UTF-8
            "https://www.eff.org/sites/default/files/chrome150_0.jpg", // Image from HTTPS
            "http://bit.ly/soBiXr", // Redirect link
            "http://img001.us.expono.com/100001/100001-1bc30-2d736f_m.jpg", // EXIF
            "", // Empty link
            "http://wrong.site.com/corruptedLink", // Wrong link
    };
}
