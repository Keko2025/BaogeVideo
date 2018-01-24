package demo.soho.com.baogevideo.ui.activity.channel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import demo.soho.com.baogevideo.model.ChannelDescBean;
import demo.soho.com.baogevideo.ui.fragment.channel.HotChannelFragment;
import demo.soho.com.baogevideo.ui.fragment.channel.TimeChannelFragment;
import demo.soho.com.baogevideo.util.TabUtil;
import demo.soho.com.baogevideo.util.http.OkHttpUtil;
import demo.soho.com.baogevideo.util.http.Url;

/**
 * @author dell
 * @data 2018/1/24.
 */
public class ChannelDescActivity extends AppCompatActivity {
    @BindView(R.id.channel_bg)
    SimpleDraweeView channelBg;
    @BindView(R.id.channel_author_ava)
    SimpleDraweeView channelAuthorAva;
    @BindView(R.id.tv_channel_name)
    TextView tvChannelName;
    @BindView(R.id.tv_channel_data)
    TextView tvChannelData;
    @BindView(R.id.tv_channel_info)
    TextView tvChannelInfo;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar_tab)
    TabLayout tablayout;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.rl_channel_info)
    RelativeLayout channelTopBg;
    private Intent intent;
    private String channelId;
    private HotChannelFragment hotChannelFragment;
    private TimeChannelFragment timeChannelFragment;
    private List<Fragment> list = new ArrayList<Fragment>();
    private FragmentPagerAdapter viewPageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_desc);
        ButterKnife.bind(this);

        intent = getIntent();
        if(intent != null){
            channelId = intent.getStringExtra("channelId");
            queryChannelDescData(channelId);
        }
        initTab();
    }
    private void initTab() {
        tablayout.post(new Runnable() {
            @Override
            public void run() {
                TabUtil.setIndicator(tablayout,60,60);
            }
        });
    }

    /**
     * 获取频道详情
     * @param channelId
     */
    private void queryChannelDescData(String channelId) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id",channelId);
        new OkHttpUtil().post(Url.VIDEO_CHANNEL_DESC_API, parameters, new OkHttpUtil.HttpCallback() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(String data) {
                ChannelDescBean channelDescBean = new Gson().fromJson(data,ChannelDescBean.class);
                if(channelDescBean != null){
                    setData(channelDescBean);
                }
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

    /**
     * 控件赋值
     * @param channelDescBean
     */
    private void setData(ChannelDescBean channelDescBean) {
        channelBg.setImageURI(Uri.parse(channelDescBean.getData().getCover_url()));
        channelAuthorAva.setImageURI(Uri.parse(channelDescBean.getData().getPic_url()));
        tvChannelName.setText(channelDescBean.getData().getName());
        tvChannelData.setText(channelDescBean.getData().getCount() + "个视频 | "
                + channelDescBean.getData().getCollects() + "个收藏 | "
                + channelDescBean.getData().getShares() + "次分享");
        tvChannelInfo.setText(channelDescBean.getData().getIntro());

        initToolBar(channelDescBean.getData().getName());
    }

    private void initToolBar(final String name) {
        toolbar.setNavigationIcon(R.drawable.title_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -channelTopBg.getHeight() / 2) {
                    toolbar.setNavigationIcon(R.drawable.back_white);
                    toolbar.setVisibility(View.VISIBLE);
                    tvTitle.setVisibility(View.VISIBLE);
                    tvTitle.setText(name);
                    tvTitle.setTextColor(getResources().getColor(R.color.white));
                } else {
                    toolbar.setNavigationIcon(R.drawable.title_back);
                    toolbar.setVisibility(View.VISIBLE);
                    tvTitle.setVisibility(View.GONE);
                    tvTitle.setTextColor(getResources().getColor(R.color.black));
                    tvTitle.setText(name);
                }
            }
        });
        initFragment();
    }

    private void initFragment() {
        list.clear();
        if(hotChannelFragment == null){
            hotChannelFragment = new HotChannelFragment();
        }
        if(timeChannelFragment == null){
            timeChannelFragment = new TimeChannelFragment();
        }
        list.add(hotChannelFragment);
        list.add(timeChannelFragment);

        Bundle bundle = new Bundle();
        bundle.putString("channel_id",channelId);

        hotChannelFragment.setArguments(bundle);
        timeChannelFragment.setArguments(bundle);

        viewPageAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        };

        viewpager.setAdapter(viewPageAdapter);
        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
        tablayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewpager));
    }
}
