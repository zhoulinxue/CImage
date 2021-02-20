package zhx.cimage.simple;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import org.zhx.common.image.CImage;
import org.zhx.common.image.Target;
import org.zhx.common.image.callback.BitmapCallback;
import org.zhx.common.image.displayer.DisplayerImpl.AnimateDisplayer;

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
        return mList == null ? 0 : mList.length;
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
        ViewHolder holder = null;
        if (view != null && view.getTag() != null) {
            holder = (ViewHolder) view.getTag();
        } else {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.listview_item_layout, null);
            view.setTag(holder);
            holder.imageView = view.findViewById(R.id.text_img);
        }
        //带回调 加载
        final ViewHolder finalHolder = holder;
//        CImage.loadWithCallBack(mList[i], new BitmapCallback(holder.imageView.getContext()) {
//            @Override
//            protected void onComplete(boolean isSuc, String imageUri, Bitmap bitmap) {
//                finalHolder.imageView.setImageBitmap(bitmap);
//            }
//        });
        CImage.load(mList[i]).error(R.mipmap.ic_error).loading(R.mipmap.ic_stub).setDisPlayer(new AnimateDisplayer(300)).into(holder.imageView);
        return view;
    }

    public class ViewHolder {
        public ImageView imageView;
    }
}
