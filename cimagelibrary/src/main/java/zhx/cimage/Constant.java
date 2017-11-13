package zhx.cimage;

import android.os.Environment;

import java.io.File;

/**
 * Created by ${zhouxue} on 17/10/4 15: 58.
 * QQ:515278502
 */

public class Constant {
    public static int mMemorySize=(int)Runtime.getRuntime().maxMemory()/4;
    public static int mDiskMaxSize=1024*1024*2;
    public static String CocaheDir= Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"CImage";
    public static final String isSmall="small";
}
