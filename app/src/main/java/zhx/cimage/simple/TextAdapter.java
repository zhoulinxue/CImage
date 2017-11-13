package zhx.cimage.simple;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import zhx.cimage.CImage;
import zhx.cimage.displayer.DisplayerImpl.AnimateDisplayer;
import zhx.cimage.displayer.DisplayerImpl.RoundCornerDisplayer;

/**
 * Created by ${zhouxue} on 17/10/5 16: 32.
 * QQ:515278502
 */

public class TextAdapter extends BaseAdapter {
    private String[] mList;
    private Context context;

    public TextAdapter(String[] mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mList==null?0:mList.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            view= LayoutInflater.from(context).inflate(R.layout.listview_item_layout,null);
        }
        ImageView tximg= (ImageView) view.findViewById(R.id.text_img);
        CImage.load(mList[i]).into(tximg).setDisPlayer(new AnimateDisplayer(300)).smallend();
        return view;
    }
}
