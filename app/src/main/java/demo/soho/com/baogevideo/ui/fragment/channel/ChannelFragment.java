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
import demo.soho.com.baogevideo.model.VideoChannelBean;
import demo.soho.com.baogevideo.ui.activity.channel.ChannelDescActivity;
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
 * @data 2018/1/19.
 * desc:精选频道
 */

public class ChannelFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    private Context mContext;
    private LinearLayoutManager layoutManager;
    private int page = 0;
    private int pageno = 10;
    List<VideoChannelBean.DataBean> channelList = new ArrayList<>();
    private RecyclerCommonAdapter<VideoChannelBean.DataBean> adapter;
    private HeaderViewRecyclerAdapter mAdapter;
    private View loadMoreView;
    private TextView loadingTv;
    private CircleProgressView loadPg;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_channel;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

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

        adapter = new RecyclerCommonAdapter<VideoChannelBean.DataBean>(mContext, channelList, R.layout.item_fragment_video_channel) {
            @Override
            public void convert(RecyclerViewHolder holder, VideoChannelBean.DataBean item, int position) {
                holder.setFrescoImg(R.id.channel_author_ava, Uri.parse(item.getPic_url()));
                holder.setText(R.id.tv_channel_name,item.getName());
                holder.setText(R.id.tv_updata_time,item.getUpdate_time());
                holder.setText(R.id.tv_num,item.getCount() + "个视频");
                holder.setText(R.id.tv_channel_desc,item.getIntro());
            }
        };
        mAdapter = new HeaderViewRecyclerAdapter(adapter);
        recyclerView.setAdapter(mAdapter);
        createLoadMoreView();
        adapter.setOnItemClickListener(new RecyclerCommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(mContext,ChannelDescActivity.class).putExtra("channelId",channelList.get(position).getId()));
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public int lastVisibleItem;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter.getItemCount()){
                    page++;
                    initData();
                    loadMoreView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(layoutManager instanceof LinearLayoutManager){
                    lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                }
            }
        });
    }

    /**
     * 加载更多
     */
    private void createLoadMoreView() {
        loadMoreView = LayoutInflater.from(mContext).inflate(R.layout.layout_load_more, recyclerView, false);
        loadingTv = (TextView)loadMoreView.findViewById(R.id.load_tv);
        loadPg = (CircleProgressView)loadMoreView.findViewById(R.id.load_pro);
        mAdapter.addFooterView(loadMoreView);
        loadMoreView.setVisibility(View.GONE);
    }

    @Override
    protected void initData() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("p",page);
        parameters.put("r",pageno);
        new OkHttpUtil().post(Url.VIDEO_CHANNEL_API, parameters, new OkHttpUtil.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                refreshLayout.setRefreshing(false);
                VideoChannelBean videoChannelBean = new Gson().fromJson(data,VideoChannelBean.class);
                if(videoChannelBean != null && videoChannelBean.getData().size() > 0){
                    loadMoreView.setVisibility(View.GONE);
                    if(page == 1 && videoChannelBean.getData().size() > 0){
                        channelList.clear();
                    }
                }else {
                    loadMoreView.setVisibility(View.VISIBLE);
                    loadingTv.setText(R.string.load_finish);
                    loadPg.setVisibility(View.GONE);
                }
                channelList.addAll(videoChannelBean.getData());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                refreshLayout.setRefreshing(false);
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
        }, 1000);
    }
}
