package demo.soho.com.baogevideo.ui.fragment.channel;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import demo.soho.com.baogevideo.R;
import demo.soho.com.baogevideo.model.VideoListBean;
import demo.soho.com.baogevideo.ui.activity.home.VideoDesActivity;
import demo.soho.com.baogevideo.ui.adapter.common.HeaderViewRecyclerAdapter;
import demo.soho.com.baogevideo.ui.adapter.common.RecyclerCommonAdapter;
import demo.soho.com.baogevideo.ui.adapter.common.RecyclerViewHolder;
import demo.soho.com.baogevideo.ui.fragment.base.BaseFragment;
import demo.soho.com.baogevideo.ui.widget.CircleProgressView;
import demo.soho.com.baogevideo.util.L;
import demo.soho.com.baogevideo.util.http.OkHttpUtil;
import demo.soho.com.baogevideo.util.http.Url;

/**
 * @author dell
 * @data 2018/1/24.
 */

public class TimeChannelFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;

    private Context mContext;
    private int page = 1;
    private int pageno = 10;
    List<VideoListBean.DataBean> videoList = new ArrayList<>();
    private RecyclerCommonAdapter<VideoListBean.DataBean> adapter;
    private LinearLayoutManager layoutManager;
    private HeaderViewRecyclerAdapter mAdapter;
    private View loadMoreView;
    private TextView loadingTv;
    private CircleProgressView loadPg;
    private String channelId;

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

        Bundle bundle = getArguments();
        if(bundle != null){
            channelId = bundle.getString("channel_id");
        }

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);
        refreshLayout.setRefreshing(true);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerCommonAdapter<VideoListBean.DataBean>(mContext,videoList,R.layout.item_fragment_home_video) {
            @Override
            public void convert(RecyclerViewHolder holder, VideoListBean.DataBean item, int position) {
                holder.setText(R.id.tv_video_name,item.getTitle());
                holder.setFrescoImg(R.id.video_img, Uri.parse(item.getPic_url()));
                holder.setText(R.id.tv_video_time,item.getLength());
                holder.setText(R.id.tv_publish_data,item.getPlay() + "次播放");
            }
        };
        adapter.setOnItemClickListener(new RecyclerCommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mContext.startActivity(new Intent(mContext,VideoDesActivity.class).putExtra("videoId",videoList.get(position).getId()));
                L.e("id：" + videoList.get(position).getId());
            }
        });
        mAdapter = new HeaderViewRecyclerAdapter(adapter);
        createLoadMoreView();
        recyclerView.setAdapter(mAdapter);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            public int lastVisibleItem;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter.getItemCount() && videoList.size() > 9) {
                    page++;
                    initData();
                    loadMoreView.setVisibility(View.VISIBLE);
                }

            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (layoutManager instanceof LinearLayoutManager) {
                    lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                }
            }
        });
    }

    private void createLoadMoreView() {
        if(mContext == null){
            return;
        }
        loadMoreView = LayoutInflater.from(mContext).inflate(R.layout.layout_load_more, recyclerView, false);
        loadingTv = (TextView)loadMoreView.findViewById(R.id.load_tv);
        loadPg = (CircleProgressView)loadMoreView.findViewById(R.id.load_pro);
        mAdapter.addFooterView(loadMoreView);
    }

    @Override
    protected void initData() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("p",page);
        parameters.put("r",pageno);
        parameters.put("channel_id",channelId);
        parameters.put("orderby","2");
        new OkHttpUtil().post(Url.VIDEO_CHANNEL_HOT, parameters, new OkHttpUtil.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                refreshLayout.setRefreshing(false);
                VideoListBean videoListBean = new Gson().fromJson(data,VideoListBean.class);
                if(videoListBean.getData().size() > 0){
                    if(page == 1){
                        videoList.clear();
                        loadMoreView.setVisibility(View.GONE);
                    }else if(videoListBean.getData().size() < 9){
                        loadingTv.setText(R.string.load_finish);
                        loadPg.setVisibility(View.GONE);
                    }else {
                        loadMoreView.setVisibility(View.GONE);
                    }
                }else{
                    loadingTv.setText(R.string.load_finish);
                    loadPg.setVisibility(View.GONE);
                }
                videoList.addAll(videoListBean.getData());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                refreshLayout.setRefreshing(false);
                loadMoreView.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                refreshLayout.setRefreshing(false);
                loadMoreView.setVisibility(View.GONE);
            }

            @Override
            public void onStart() {
                super.onStart();
            }
        });

    }

    @Override
    public void onRefresh() {
        refreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                page = 1;
                initData();
            }
        }, 2000);
    }
}
