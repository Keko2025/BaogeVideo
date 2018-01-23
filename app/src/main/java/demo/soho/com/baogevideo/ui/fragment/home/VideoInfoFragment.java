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
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import demo.soho.com.baogevideo.R;
import demo.soho.com.baogevideo.model.VideoDescBean;
import demo.soho.com.baogevideo.model.VideoListBean;
import demo.soho.com.baogevideo.ui.adapter.common.HeaderViewRecyclerAdapter;
import demo.soho.com.baogevideo.ui.adapter.common.RecyclerCommonAdapter;
import demo.soho.com.baogevideo.ui.adapter.common.RecyclerViewHolder;
import demo.soho.com.baogevideo.ui.fragment.base.BaseFragment;
import demo.soho.com.baogevideo.util.L;
import demo.soho.com.baogevideo.util.http.OkHttpUtil;
import demo.soho.com.baogevideo.util.http.Url;

/**
 * @author dell
 * @data 2018/1/21.
 */

public class VideoInfoFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,View.OnClickListener{
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    List<VideoListBean.DataBean> videoList = new ArrayList<>();
    private RecyclerCommonAdapter<VideoListBean.DataBean> adapter;
    private Context mContext;
    private HeaderViewRecyclerAdapter mAdapter;
    private View headView;
    private LinearLayoutManager layoutManager;
    private String channelId;
    private TextView videoName;
    private TextView playCount;
    private TextView videoInfo;
    private VideoListBean videoListBean;
    private VideoDescBean videoInfoBean;
    private TextView tvDownload;
    private TextView tvCollect;
    private TextView tvShare;
    private TagFlowLayout videoTag;

    private static final int VIDEO_CONTENT_DESC_MAX_LINE = 2;// 默认展示最大行数3行
    private static final int SHOW_CONTENT_NONE_STATE = 0;// 扩充
    private static final int SHRINK_UP_STATE = 1;// 收起状态
    private static final int SPREAD_STATE = 2;// 展开状态
    private static int mState = SHRINK_UP_STATE;//默认收起状态
    private String isCollect;

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
        adapter = new RecyclerCommonAdapter<VideoListBean.DataBean>(mContext,videoList,R.layout.fragment_related_video) {
            @Override
            public void convert(RecyclerViewHolder holder, VideoListBean.DataBean item, int position) {
                holder.setFrescoImg(R.id.relate_video_img, Uri.parse(item.getThumb_pic_url()));
                holder.setText(R.id.relate_vedio_title_name,item.getTitle());
                holder.setText(R.id.relate_vedio_time,item.getPlay() + "次播放" + " | " + item.getUpdate_time());
            }
        };
        mAdapter = new HeaderViewRecyclerAdapter(adapter);
        recyclerView.setAdapter(mAdapter);
        createHeadView();
    }

    private void createHeadView() {
        headView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_video_details_introduce, recyclerView, false);
        videoName = (TextView) headView.findViewById(R.id.video_title);
        playCount = (TextView) headView.findViewById(R.id.tv_play);
        videoInfo = (TextView) headView.findViewById(R.id.tv_video_info);
        tvCollect = (TextView) headView.findViewById(R.id.tv_collect);
        tvShare = (TextView) headView.findViewById(R.id.tv_video_share_num);
        videoTag = (TagFlowLayout) headView.findViewById(R.id.tags_layout);

        videoName.setOnClickListener(this);
        tvCollect.setOnClickListener(this);
        tvShare.setOnClickListener(this);


        mAdapter.addHeaderView(headView);
    }

    /**
     * 给头部videoinfo控件赋值
     * @param videoInfoBean
     */
    private void setVideoInfoData(VideoDescBean videoInfoBean) {
        videoName.setText(videoInfoBean.getData().getTitle());
        playCount.setText(videoInfoBean.getData().getPlay() + "次播放");
        videoInfo.setText(videoInfoBean.getData().getIntro());
        tvCollect.setText(videoInfoBean.getData().getCollects());

        isCollect = videoInfoBean.getData().getIs_collect();
    }

    private void getData() {
        Bundle bundle = getArguments();
        if(bundle != null){
            videoInfoBean = (VideoDescBean)bundle.getParcelable("videoInfoBean");
            setVideoInfoData(videoInfoBean);
        }
    }
    @Override
    protected void initData() {
        getData();
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("r",10);
        parameters.put("p",0);
        parameters.put("channel_id",videoInfoBean.getData().getChannel_id());
        new OkHttpUtil().post(Url.VIDEO_RELATED, parameters, new OkHttpUtil.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                L.e("data:" + data);
                refreshLayout.setRefreshing(false);
                videoListBean = new Gson().fromJson(data,VideoListBean.class);
                if(videoListBean != null && videoListBean.getData().size() > 0){
                    videoList.addAll(videoListBean.getData());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                refreshLayout.setRefreshing(false);
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

    @Override
    public void onRefresh() {}


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_video_name:
                if(mState == SPREAD_STATE){
                    videoInfo.setMaxLines(VIDEO_CONTENT_DESC_MAX_LINE);
                    videoInfo.requestLayout();
                    mState = SHRINK_UP_STATE;
                }else if(mState == SHRINK_UP_STATE){
                    videoInfo.setMaxLines(Integer.MAX_VALUE);
                    videoInfo.requestLayout();
                    mState = SPREAD_STATE;
                }
                break;

            case R.id.tv_collect:
                Toast.makeText(mContext, "点击收藏按钮", Toast.LENGTH_SHORT).show();
                break;
            
            case R.id.tv_video_download:
                Toast.makeText(mContext, "暂时没有缓存权限", Toast.LENGTH_SHORT).show();
                break;
            
            case R.id.video_share_icon:
                Toast.makeText(mContext, "点击分享", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
