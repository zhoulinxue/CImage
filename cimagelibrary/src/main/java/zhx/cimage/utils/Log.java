package zhx.cimage.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import zhx.cimage.CImage;

public class Log {
	public static void error(String error) {
		System.out.println(new Date().toString() + "|" + error);
	}

	
	public static void e(String msg){
		if(CImage.isDebug)
		android.util.Log.e("CImage",msg);
	}

	public static void e(String TAG,String msg){
		if(CImage.isDebug)
			android.util.Log.e("CImage",msg);
	}
	
	
}
