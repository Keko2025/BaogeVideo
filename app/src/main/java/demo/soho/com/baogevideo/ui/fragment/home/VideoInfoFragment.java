package demo.soho.com.baogevideo.ui.fragment.home;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import demo.soho.com.baogevideo.R;
import demo.soho.com.baogevideo.model.VideoListBean;
import demo.soho.com.baogevideo.ui.adapter.common.HeaderViewRecyclerAdapter;
import demo.soho.com.baogevideo.ui.adapter.common.RecyclerCommonAdapter;
import demo.soho.com.baogevideo.ui.adapter.common.RecyclerViewHolder;
import demo.soho.com.baogevideo.ui.fragment.base.BaseFragment;
import demo.soho.com.baogevideo.util.http.OkHttpUtil;
import demo.soho.com.baogevideo.util.http.Url;

/**
 * @author dell
 * @data 2018/1/21.
 */

public class VideoInfoFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    List<VideoListBean.DataBean> videoList = new ArrayList<>();
    private RecyclerCommonAdapter<VideoListBean.DataBean> adapter;
    private Context mContext;
    private HeaderViewRecyclerAdapter mAdapter;
    private View headView;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video_list;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this,view);

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);
        refreshLayout.setRefreshing(true);
        adapter = new RecyclerCommonAdapter<VideoListBean.DataBean>(mContext,videoList,R.layout.fragment_related_video) {
            @Override
            public void convert(RecyclerViewHolder holder, VideoListBean.DataBean item, int position) {
                holder.setFrescoImg(R.id.relate_video_img, Uri.parse(item.getThumb_pic_url()));
                holder.setText(R.id.tv_video_name,item.getTitle());
            }
        };
        mAdapter = new HeaderViewRecyclerAdapter(adapter);
        recyclerView.setAdapter(adapter);
        createHeadView();

    }

    private void createHeadView() {
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_video_details_introduce, recyclerView, false);

    }

    @Override
    protected void initData() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("r",10);
        parameters.put("p",0);
        new OkHttpUtil().post(Url.VIDEO_RELATED, parameters, new OkHttpUtil.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                refreshLayout.setRefreshing(false);
                VideoListBean videoListBean = new Gson().fromJson(data,VideoListBean.class);
                if(videoListBean != null && videoListBean.getData().size() > 0){
                    videoList.addAll(videoListBean.getData());
                    adapter.notifyDataSetChanged();
                    setData();
                }
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
            }

            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
            }
        });
    }

    private void setData() {
    }

    @Override
    public void onRefresh() {

    }
}
