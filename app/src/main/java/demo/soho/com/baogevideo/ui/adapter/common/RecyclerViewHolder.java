package demo.soho.com.baogevideo.ui.adapter.common;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import demo.soho.com.baogevideo.util.DisplayUtil;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;

    public RecyclerViewHolder(Context context, View view, ViewGroup parent){
        super(view);
        mContext = context;
        mViews = new SparseArray<>();
        mConvertView = view;
    }

    public static RecyclerViewHolder get(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        RecyclerViewHolder holder = new RecyclerViewHolder(context, itemView, parent);
        return holder;
    }
    /**
     * 通过viewId获取控件
     */
    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
    /**
     * 显示文本
     * @param viewId
     * @param text
     * @return
     */
    public RecyclerViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }
    /**
     *给img赋值
     * @param viewId
     * @param resId
     * @return
     */
    public RecyclerViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    /**
     * 控件点击事件
     * @param viewId
     * @param listener
     * @return
     */
    public RecyclerViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    /**
     * SimpleDraweeView图片()
     * @param viewId
     * @param uri
     * @return
     */
    public RecyclerViewHolder setFrescoImg(int viewId, Uri uri){
        SimpleDraweeView simpleDraweeView = getView(viewId);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(DisplayUtil.dp2px(mContext.getApplicationContext(),360),DisplayUtil.dp2px(mContext.getApplicationContext(),202)))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(simpleDraweeView.getController())
                .build();
        simpleDraweeView.setController(controller);
        return this;
    }
}
