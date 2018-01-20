package demo.soho.com.baogevideo.ui.adapter.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/2/26.
 */

public abstract class RecyclerCommonAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> data;

    private OnItemLongClickListener mOnItemLongClickListener = null;
    private OnItemClickListener mOnItemClickListener = null;
    private int mPosition = 0;

    public void setOnItemClickListener(RecyclerCommonAdapter.OnItemClickListener mOnItemClickListener){this.mOnItemClickListener = mOnItemClickListener;}
    public void setOnItemLongClickListener(RecyclerCommonAdapter.OnItemLongClickListener mOnItemLongClickListener) {this.mOnItemLongClickListener = mOnItemLongClickListener;}

    public void setPosition(int position) {
        mPosition = position;
    }

    public interface OnItemClickListener{void onItemClick(View view, int position);}
    public interface OnItemLongClickListener{void onItemLongClick(View view, int position);}

    public RecyclerCommonAdapter(Context context, List<T> data, int layoutId) {
        mContext = context;
        mLayoutId = layoutId;
        if(data == null){
            this.data = new ArrayList<>();
        }else if(data instanceof List){
            this.data = (List<T>) data;
        }else{
            this.data = new ArrayList<>(data);
        }
    }
    public void clear() {
        data.clear();
        notifyDataSetChanged();
    }
    /**
     * Recycler 填充方法
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        RecyclerViewHolder viewHolder = RecyclerViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        //判断是否设置了监听器
        if(mOnItemClickListener != null){
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView,id); // 2
                }
            });
        }
        if(mOnItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int id = holder.getLayoutPosition();
                    mOnItemLongClickListener.onItemLongClick(holder.itemView, id);
                    return true;
                }
            });
        }
        convert(holder, data.get(position),position);
    }

    /**
     * 资源回收
     * @param holder
     */
    @Override
    public void onViewDetachedFromWindow(RecyclerViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        //释放资源
        int position = holder.getAdapterPosition();
        //数组越界检查
        if(position < 0 || position >= data.size()){
            return;
        }
    }
    /**
     * RecyclerView适配器填充方法
     * @param holder  viewholder
     * @param t       javabean
     * @param position 位置
     */
    public abstract void convert(RecyclerViewHolder holder, T t, int position);
    @Override
    public int getItemCount()
    {
        return data.size();
    }
}
