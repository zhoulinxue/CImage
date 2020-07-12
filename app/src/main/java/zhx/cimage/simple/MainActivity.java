package zhx.cimage.simple;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.zhx.common.image.CImage;
import org.zhx.common.image.loader.http.BaseImageDownloader;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CImage.init().setImageLoader(new BaseImageDownloader(this));
        mListView= (ListView) findViewById(R.id.listView);
        TextAdapter mAdapter=new TextAdapter(IMAGES,this);
        mListView.setAdapter(mAdapter);
    }

    public static final String[] IMAGES = new String[] {
            // Heavy images
            "http://mpic.tiankong.com/786/db8/786db8df99d52a7aabcc7be97728e6c9/640.jpg",
            "http://5b0988e595225.cdn.sohucs.com/images/20181209/38467a58f9264ca68eefa37719b4b739.jpeg",
            // Special cases
            "file:///sdcard/Universal Image Loader @#&=+-_.,!()~'%20.png",
            "assets://Living Things @#&=+-_.,!()~'%20.jpg",
            "http://mpic.tiankong.com/05a/fda/05afdae70cf4dcf046ac9ddae51db8e4/640.jpg",
            "drawable://" + R.mipmap.ic_launcher,
            "https://pic.feizl.com/upload/allimg/170810/1008kfibb5kpmpp.jpg",
            "https://pic.feizl.com/upload/allimg/170810/1007bfxpfhg3nqz.jpg",

            "http://5b0988e595225.cdn.sohucs.com/images/20181209/bc3b96877862459098839e3c9b6ebc23.jpeg", // Redirect link

            "http://mpic.tiankong.com/786/db8/786db8df99d52a7aabcc7be97728e6c9/640.jpg",

            "http://5b0988e595225.cdn.sohucs.com/images/20181209/0acff72ebfbc4e0aba7b6f33dbe1df29.jpeg", // EXIF
            "http://wrong.site.com/corruptedLink", // Wrong link
            "http://mpic.tiankong.com/05a/fda/05afdae70cf4dcf046ac9ddae51db8e4/640.jpg",
            "http://5b0988e595225.cdn.sohucs.com/images/20181209/b3ef180a8e2b452bab60a198dd354617.jpeg",
    };
}
