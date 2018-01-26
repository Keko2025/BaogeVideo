package demo.soho.com.baogevideo.ui.activity.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import butterknife.OnClick;
import demo.soho.com.baogevideo.BaogeApp;
import demo.soho.com.baogevideo.R;
import demo.soho.com.baogevideo.model.CollectListBean;
import demo.soho.com.baogevideo.ui.activity.base.BaseActivity;
import demo.soho.com.baogevideo.ui.activity.home.VideoDesActivity;
import demo.soho.com.baogevideo.ui.adapter.common.HeaderViewRecyclerAdapter;
import demo.soho.com.baogevideo.ui.adapter.common.RecyclerCommonAdapter;
import demo.soho.com.baogevideo.ui.adapter.common.RecyclerViewHolder;
import demo.soho.com.baogevideo.ui.widget.CircleProgressView;
import demo.soho.com.baogevideo.util.L;
import demo.soho.com.baogevideo.util.SpUtil;
import demo.soho.com.baogevideo.util.StringUtils;
import demo.soho.com.baogevideo.util.http.OkHttpUtil;
import demo.soho.com.baogevideo.util.http.Url;

/**
 * @author dell
 * @data 2018/1/25.
 */

public class CollectActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.id_title)
    TextView idTitle;
    private String token;
    private int page = 1;
    private int pageno = 20;
    List<CollectListBean.DataBean> collectList = new ArrayList<>();
    private RecyclerCommonAdapter<CollectListBean.DataBean> collectAdapter;
    private LinearLayoutManager layoutManager;
    private HeaderViewRecyclerAdapter mAdapter;
    private View loadMoreView;
    private TextView loadingTv;
    private CircleProgressView loadPg;
    List<String> videoIdList = new ArrayList<>();
    private String videoIds;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        ButterKnife.bind(this);

        initView();
        token = (String) SpUtil.get(BaogeApp.context, "token", "");
        initData();
    }

    private void initData() {
        Map<String,Object> paramters = new HashMap<>();
        paramters.put("token",token);
        paramters.put("p",page);
        paramters.put("r",pageno);
        new OkHttpUtil().post(Url.VIDEO_MY_COLLECT_API, paramters, new OkHttpUtil.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                CollectListBean collectListBean = new Gson().fromJson(data,CollectListBean.class);
                if(collectListBean != null && collectListBean.getData().size() > 0){
                    if(collectListBean.getData().size() > 0){
                        refreshLayout.setRefreshing(false);
                        loadMoreView.setVisibility(View.GONE);
                        if(page == 1 && collectList.size() > 0){
                            collectList.clear();
                        }
                    }else {
                        loadingTv.setText(R.string.load_finish);
                        loadPg.setVisibility(View.GONE);
                    }

                    collectList.addAll(collectListBean.getData());
                    collectAdapter.notifyDataSetChanged();
                    if(collectList.size() > 0){
                        for(int i = 0; i < collectList.size(); i++){
                            videoIdList.add(collectListBean.getData().get(i).getId());

                        }
                        videoIds = videoIdList.toArray().toString();
                        L.e("videoIds" + videoIds);
                    }
                }
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                refreshLayout.setRefreshing(false);
                Toast.makeText(CollectActivity.this, msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
            }
        });
    }

    private void initView() {
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);
        refreshLayout.setRefreshing(true);

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        collectAdapter = new RecyclerCommonAdapter<CollectListBean.DataBean>(this,collectList,R.layout.item_fragment_related_video) {
            @Override
            public void convert(RecyclerViewHolder holder, CollectListBean.DataBean item, int position) {
                holder.setFrescoImg(R.id.relate_video_img, Uri.parse(item.getPic_url()));
                holder.setText(R.id.relate_vedio_title_name,item.getTitle());
            }
        };
        collectAdapter.setOnItemClickListener(new RecyclerCommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(new Intent(CollectActivity.this, VideoDesActivity.class).putExtra("videoId",collectList.get(position).getVideo_id()));
            }
        });
        mAdapter = new HeaderViewRecyclerAdapter(collectAdapter);
        createLoadMoreView();
        recyclerView.setAdapter(mAdapter);

    }

    /**
     * 加载更多
     */
    private void createLoadMoreView() {
        loadMoreView = LayoutInflater.from(this).inflate(R.layout.layout_load_more, recyclerView, false);
        loadingTv = (TextView)loadMoreView.findViewById(R.id.load_tv);
        loadPg = (CircleProgressView)loadMoreView.findViewById(R.id.load_pro);
        mAdapter.addFooterView(loadMoreView);
    }

    @OnClick({R.id.id_back, R.id.id_clear})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_back:
                finish();
                break;
            case R.id.id_clear:
                clearCollectList();
                break;
        }
    }

    /**
     * 清空所有缓存列表
     */
    private void clearCollectList() {
        Map<String,Object> paramters = new HashMap<>();
        paramters.put("token",token);
        paramters.put("video_id",videoIds);
        new OkHttpUtil().post(Url.CLEAR_COLLECT_LIST_API, paramters, new OkHttpUtil.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                L.e("清空缓存列表：" + data);
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
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
