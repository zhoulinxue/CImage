package org.zhx.common.image;

import android.os.Environment;

import java.io.File;

/**
 * Name: Constant
 * Author: zhouxue
 * Email: 194093798@qq.com
 * Comment: //TODO
 * Date: 2020-07-09 17:03
 */
public class Constant {
    public static int mMemorySize=(int)Runtime.getRuntime().maxMemory()/4;
    public static int mDiskMaxSize=1024*1024*2;
    public static String CocaheDir= Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"CImage";
    public static final String isSmall="small";
}
