package demo.soho.com.baogevideo.ui.activity.home;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.shuyu.gsyvideoplayer.video.base.GSYVideoPlayer;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import demo.soho.com.baogevideo.BaogeApp;
import demo.soho.com.baogevideo.R;
import demo.soho.com.baogevideo.listener.SampleListener;
import demo.soho.com.baogevideo.model.VideoDescBean;
import demo.soho.com.baogevideo.ui.fragment.home.VideoCommentFragment;
import demo.soho.com.baogevideo.ui.fragment.home.VideoInfoFragment;
import demo.soho.com.baogevideo.ui.widget.LandLayoutVideo;
import demo.soho.com.baogevideo.util.L;
import demo.soho.com.baogevideo.util.SpUtil;
import demo.soho.com.baogevideo.util.StringUtils;
import demo.soho.com.baogevideo.util.TabUtil;
import demo.soho.com.baogevideo.util.http.OkHttpUtil;
import demo.soho.com.baogevideo.util.http.Url;

/**
 * @author dell
 * @data 2018/1/20.
 */

public class VideoDesActivity extends AppCompatActivity {
    @BindView(R.id.detail_player)
    LandLayoutVideo detailPlayer;
    @BindView(R.id.toolbar_tab)
    TabLayout toolbarTab;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private SVProgressHUD progressDialog;
    private String videoId;
    private VideoDescBean videoBean;
    private SimpleDraweeView imageView;
    private boolean isPlay;
    private boolean isPause;
    private OrientationUtils orientationUtils;
    private List<Fragment> list = new ArrayList<Fragment>();
    private VideoInfoFragment videoInfoFragment = null;
    private VideoCommentFragment videoCommentFragment = null;
    private List<String> titles = new ArrayList<>();
    private String token;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_desc);

        ButterKnife.bind(this);
        progressDialog = new SVProgressHUD(this);
        token = (String) SpUtil.get(getApplicationContext(), "token", "");

        Intent intent = getIntent();
        if (intent != null) {
            videoId = intent.getStringExtra("videoId") != null ? intent.getStringExtra("videoId") : "";
            L.e("接受到：" + videoId);
            if(!StringUtils.isEmptyString(videoId)){
                iniData(videoId);
            }
        }
    }

    /**
     * 获取用户token
     */
    private void getUserData() {
        token = (String) SpUtil.get(BaogeApp.context, "token", "");
    }
    private void iniData(String videoId) {
        L.e("videoId:"+videoId);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id",videoId);
        parameters.put("token",token != null ? token : "");
        new OkHttpUtil().post(Url.VIDEO_DESC_API, parameters, new OkHttpUtil.HttpCallback() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                Toast.makeText(VideoDesActivity.this, "onError:" + msg, Toast.LENGTH_SHORT).show();
            }


            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                Toast.makeText(VideoDesActivity.this, "onFailure:" + msg, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String data) {
                L.e("response:" + data);
                videoBean = new Gson().fromJson(data,VideoDescBean.class);
                initVideo(videoBean);
                initFragment(videoBean);
            }
        });
    }

    private void initFragment(VideoDescBean videoBean) {
        if (videoInfoFragment == null) {
            videoInfoFragment = new VideoInfoFragment();
        }
        if (videoCommentFragment == null) {
            videoCommentFragment = new VideoCommentFragment();
        }
        list.add(videoInfoFragment);
        list.add(videoCommentFragment);
        Bundle bundle = new Bundle();
        bundle.putParcelable("videoInfoBean",videoBean);

        bundle.putString("videoId", videoId);
        bundle.putString("channelId",videoBean.getData().getChannel_id());

        videoInfoFragment.setArguments(bundle);
        videoCommentFragment.setArguments(bundle);
        setPagerTitle(String.valueOf(videoBean.getData().getComments()));
    }
    private void setPagerTitle(String num) {
        titles.add(getString(R.string.introduction));
        titles.add(getString(R.string.video_desc_review) + "(" + num + ")");
        initTab();
        FragmentPagerAdapter viewPageAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return titles.size();
            }

            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position % titles.size());
            }
        };
        viewPager.setAdapter(viewPageAdapter);
        viewPager.setOffscreenPageLimit(2);
        toolbarTab.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(toolbarTab));
        toolbarTab.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        setTabLayout();
    }
    /**
     * 设置TabLayout下划线宽度
     */
    private void setTabLayout() {
        TabUtil.reflex(toolbarTab);
    }
    //必须要在Tablayout渲染出来后调用,
    private void initTab() {
        toolbarTab.post(new Runnable() {
            @Override
            public void run() {
                TabUtil.setIndicator(toolbarTab, 60, 60);
            }
        });
    }


    private void initVideo(VideoDescBean videoBean) {
        /**
         * 设置右下角 显示切换到全屏(显示退出全屏)的按键资源
         * 必须在setUp之前设置
         * 不设置使用默认
         */
        detailPlayer.setEnlargeImageRes(R.drawable.video_fullscreen);
        detailPlayer.setShrinkImageRes(R.drawable.exit_video_fullscreen);
        //添加封面图
        imageView = new SimpleDraweeView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageURI(Uri.parse(videoBean.getData().getThumb_pic_url()));

        resolveNormalVideoUI();

        orientationUtils = new OrientationUtils(this, detailPlayer);//外部辅助的旋转，帮助全屏
        orientationUtils.setEnable(false);//初始化不打开外部的旋转

        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        L.e("url:"+videoBean.getData().getFile_url());
        gsyVideoOption
                .setThumbImageView(imageView)
                .setIsTouchWiget(true)
                .setNeedShowWifiTip(true)
                .setRotateViewAuto(false)
                .setShowFullAnimation(false)
                .setLockLand(false)
                .setNeedLockFull(true)
                .setSeekRatio(10)
                .setUrl(videoBean.getData().getFile_url())
                .setCacheWithPlay(false)
                .setVideoTitle(videoBean.getData().getTitle())
                .setThumbPlay(true)
                .setStandardVideoAllCallBack(new SampleListener() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        Debuger.printfError("***** onPrepared **** " + objects[0]);
                        Debuger.printfError("***** onPrepared **** " + objects[1]);
                        super.onPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        orientationUtils.setEnable(true);
                        isPlay = true;
                    }

                    @Override
                    public void onEnterFullscreen(String url, Object... objects) {
                        super.onEnterFullscreen(url, objects);
                        Debuger.printfError("***** onEnterFullscreen **** " + objects[0]);//title
                        Debuger.printfError("***** onEnterFullscreen **** " + objects[1]);//当前全屏player
                    }

                    @Override
                    public void onAutoComplete(String url, Object... objects) {
                        super.onAutoComplete(url, objects);
                        if (orientationUtils != null) {
                            orientationUtils.backToProtVideo();
                        }
                        detailPlayer.getBottomView().setVisibility(View.GONE);
                        detailPlayer.getStartImg().setVisibility(View.GONE);
                    }

                    @Override
                    public void onClickStartError(String url, Object... objects) {
                        super.onClickStartError(url, objects);
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        Debuger.printfError("***** onQuitFullscreen **** " + objects[0]);//title
                        Debuger.printfError("***** onQuitFullscreen **** " + objects[1]);//当前非全屏player
                        if (orientationUtils != null) {
                            orientationUtils.backToProtVideo();
                        }
                    }
                })
                .setLockClickListener(new LockClickListener() {
                    @Override
                    public void onClick(View view, boolean lock) {
                        if (orientationUtils != null) {
                            //配合下方的onConfigurationChanged
                            orientationUtils.setEnable(!lock);

                            detailPlayer.getCurrentPlayer().setRotateViewAuto(!lock);
                        }
                    }
                }).build(detailPlayer);
        detailPlayer.startPlayLogic();
        L.e("wifi:" + "播放");
        detailPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();
                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                detailPlayer.startWindowFullscreen(VideoDesActivity.this, true, true);
            }
        });
    }

    private void resolveNormalVideoUI() {
        detailPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        detailPlayer.getTitleTextView().setText(videoBean.getData().getTitle());
        detailPlayer.getBackButton().setVisibility(View.VISIBLE);
        detailPlayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }
        if (StandardGSYVideoPlayer.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            detailPlayer.onConfigurationChanged(this, newConfig, orientationUtils);
        }
    }


    private GSYVideoPlayer getCurPlay() {
        if (detailPlayer.getFullWindowPlayer() != null) {
            return detailPlayer.getFullWindowPlayer();
        }
        return detailPlayer;
    }
    @Override
    protected void onResume() {
        getCurPlay().onVideoResume();
        super.onResume();
        isPause = false;
        getUserData();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        getCurPlay().onVideoPause();
        super.onPause();
        isPause = true;
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(isPlay){
            getCurPlay().release();
        }
        if (orientationUtils != null) {
            orientationUtils.releaseListener();
        }
        if(progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
