package demo.soho.com.baogevideo.ui.fragment.home;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import demo.soho.com.baogevideo.R;
import demo.soho.com.baogevideo.model.VideoCommentBean;
import demo.soho.com.baogevideo.model.VideoDescBean;
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
 * @data 2018/1/21.
 */

public class VideoCommentFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    private Context mContext;
    private LinearLayoutManager layoutManager;
    private VideoDescBean videoInfoBean;
    private int page = 1;
    private int pageno = 10;
    List<VideoCommentBean.DataBean> commentList = new ArrayList<>();
    private RecyclerCommonAdapter<VideoCommentBean.DataBean> adapter;
    private HeaderViewRecyclerAdapter mAdapter;
    private View loadMoreView;
    private TextView loadingTv;
    private CircleProgressView loadingPro;

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

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerCommonAdapter<VideoCommentBean.DataBean>(mContext,commentList,R.layout.item_video_comment) {
            @Override
            public void convert(RecyclerViewHolder holder, VideoCommentBean.DataBean item, int position) {
                holder.setFrescoImg(R.id.user_ava, Uri.parse(item.getUser_pic_url()));
                holder.setText(R.id.tv_username,item.getUsername());
                holder.setText(R.id.tv_level_medal,item.getLevel_medal());
                holder.setText(R.id.tv_video_comment_time,item.getAdd_time());
                holder.setText(R.id.tv_thumb_up,item.getLikes());

                holder.setOnClickListener(R.id.tv_thumb_up, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "sorry,您暂时没有权限!", Toast.LENGTH_SHORT).show();
                    }
                });
                holder.setOnClickListener(R.id.tv_comment, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "sorry,您暂时没有权限!", Toast.LENGTH_SHORT).show();
                    }
                });
                holder.setOnClickListener(R.id.img_comment_report, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(mContext, "sorry,您暂时没有权限!", Toast.LENGTH_SHORT).show();
                    }
                });
                holder.setOnClickListener(R.id.user_ava, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        startActivity(new Intent(mContext,UserZoneActivity.class));
                    }
                });
            }
        };
        mAdapter = new HeaderViewRecyclerAdapter(adapter);
        recyclerView.setAdapter(mAdapter);
        createLoadMoreView();

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
                if(layoutManager instanceof LinearLayoutManager){       //获取最后一个可见view的位置
                    lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                }
            }
        });
    }

    private void createLoadMoreView() {
        loadMoreView = LayoutInflater.from(mContext).inflate(R.layout.layout_load_more, recyclerView, false);
        loadingTv = (TextView)loadMoreView.findViewById(R.id.load_tv);
        loadingPro = (CircleProgressView)loadMoreView.findViewById(R.id.load_pro);
        mAdapter.addFooterView(loadMoreView);
        loadMoreView.setVisibility(View.GONE);
    }

    private void getData() {
        Bundle bundle = getArguments();
        if(bundle != null){
            videoInfoBean = (VideoDescBean)bundle.getParcelable("videoInfoBean");
        }
    }
    @Override
    protected void initData() {
        getData();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("p",page);
        parameters.put("r",pageno);
        parameters.put("video_id",videoInfoBean.getData().getId());
        parameters.put("token","ffbafc11f95f30402c814b89727af15e");
        new OkHttpUtil().post(Url.VIDEO_COMMONT, parameters, new OkHttpUtil.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                L.e("评论数据:" + data);
                refreshLayout.setRefreshing(false);
                VideoCommentBean videoCommentBean = new Gson().fromJson(data,VideoCommentBean.class);
                if(videoCommentBean != null && videoCommentBean.getData().size() > 0){
                    loadMoreView.setVisibility(View.GONE);
                    if(page == 1 && commentList.size() > 0){
                        commentList.clear();
                    }
                }else {
                    loadingPro.setVisibility(View.GONE);
                    loadingTv.setText(R.string.load_finish);
                }
                commentList.addAll(videoCommentBean.getData());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
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
        }, 2000);
    }
}
