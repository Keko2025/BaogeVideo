package demo.soho.com.baogevideo.ui.activity.home;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.ExoPlaybackException;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import chuangyuan.ycj.videolibrary.listener.VideoInfoListener;
import chuangyuan.ycj.videolibrary.listener.VideoWindowListener;
import chuangyuan.ycj.videolibrary.video.GestureVideoPlayer;
import chuangyuan.ycj.videolibrary.widget.VideoPlayerView;
import demo.soho.com.baogevideo.R;
import demo.soho.com.baogevideo.data.DataSource;

/**
 * @author dell
 * @data 2018/2/5.
 */

public class VideoDetailActivity extends AppCompatActivity {
    @BindView(R.id.exo_play_context_id)
    VideoPlayerView videoPlayerView;
    @BindView(R.id.toolbar_tab)
    TabLayout toolbarTab;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private GestureVideoPlayer exoPlayerManager;
    private String[] test;
    private static final String TAG = "VideoDetailActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        exoPlayerManager = new GestureVideoPlayer(this, videoPlayerView, new DataSource(this));
        //设置视频标题
        exoPlayerManager.setTitle("视频标题");
        //设置水印图
        exoPlayerManager.setExoPlayWatermarkImg(R.mipmap.watermark_big);
        exoPlayerManager.setOnWindowListener(new VideoWindowListener() {
            @Override
            public void onCurrentIndex(int currentIndex, int windowCount) {
                Toast.makeText(getApplication(), currentIndex + "windowCount:" + windowCount, Toast.LENGTH_SHORT).show();
            }
        });
        test = new String[]{getString(R.string.uri_test_9), getString(R.string.uri_test_7), getString(R.string.uri_test_8)};
        String[] name = {"超清", "高清", "标清"};

        //开启线路设置
        exoPlayerManager.setShowVideoSwitch(true);
        exoPlayerManager.setPlaySwitchUri(0,0,getString(R.string.uri_test_11), Arrays.asList(test),Arrays.asList(name));

        exoPlayerManager.startPlayer();
        Glide.with(this)
                .load(getString(R.string.uri_test_image))
                .fitCenter()
                .placeholder(R.mipmap.test)
                .into(videoPlayerView.getPreviewImage());
        exoPlayerManager.setVideoInfoListener(new VideoInfoListener() {
            @Override
            public void onPlayStart() {}

            @Override
            public void onLoadingChanged() {}

            @Override
            public void onPlayerError(@Nullable ExoPlaybackException e) {}

            @Override
            public void onPlayEnd() {
                Toast.makeText(VideoDetailActivity.this, "播放完毕...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void isPlaying(boolean playWhenReady) {}
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        exoPlayerManager.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        exoPlayerManager.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        //  exoPlayerManager.onConfigurationChanged(newConfig);//横竖屏切换
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onBackPressed() {
        if (exoPlayerManager.onBackPressed()) {
            ActivityCompat.finishAfterTransition(this);
            exoPlayerManager.onDestroy();
        }
    }
}
