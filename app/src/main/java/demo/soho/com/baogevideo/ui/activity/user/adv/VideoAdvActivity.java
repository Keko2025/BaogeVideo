package demo.soho.com.baogevideo.ui.activity.user.adv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import abc.abc.abc.nm.cm.ErrorCode;
import abc.abc.abc.nm.vdo.VideoAdListener;
import abc.abc.abc.nm.vdo.VideoAdManager;
import abc.abc.abc.nm.vdo.VideoAdSettings;
import butterknife.ButterKnife;
import demo.soho.com.baogevideo.R;
import demo.soho.com.baogevideo.ui.activity.base.BaseActivity;
import demo.soho.com.baogevideo.util.L;

/**
 * @author dell
 * @data 2018/3/7.
 */

public class VideoAdvActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adv_video);
        ButterKnife.bind(this);

        initAdv();
    }

    private void initAdv() {
        // 设置视频广告
        final VideoAdSettings videoAdSettings = new VideoAdSettings();
        videoAdSettings.setInterruptTips("退出视频播放将无法获得奖励" + "\n确定要退出吗？");
        VideoAdManager.getInstance(this).setUserId("1");
        //		// 只需要调用一次，由于在主页窗口中已经调用了一次，所以此处无需调用
//        VideoAdManager.getInstance(this).requestVideoAd(this,);

        Button btnShowVideoAd = (Button) findViewById(R.id.btn_show_video_ad);
        btnShowVideoAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 展示视频广告
                VideoAdManager.getInstance(VideoAdvActivity.this)
                        .showVideoAd(VideoAdvActivity.this, videoAdSettings, new VideoAdListener() {
                            @Override
                            public void onPlayStarted() {
                                L.e("开始播放视频");
                            }
                            @Override
                            public void onPlayInterrupted() {
                                Toast.makeText(VideoAdvActivity.this, "播放视频被中断", Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onPlayFailed(int errorCode) {
                                Toast.makeText(VideoAdvActivity.this, "视频播放失败", Toast.LENGTH_SHORT).show();
                                switch (errorCode) {
                                    case ErrorCode.NON_NETWORK:
                                        Toast.makeText(VideoAdvActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                                        break;
                                    case ErrorCode.NON_AD:
                                        Toast.makeText(VideoAdvActivity.this, "视频暂无广告", Toast.LENGTH_SHORT).show();
                                        break;
                                    case ErrorCode.RESOURCE_NOT_READY:
                                        Toast.makeText(VideoAdvActivity.this, "视频资源还没准备好", Toast.LENGTH_SHORT).show();
                                        break;
                                    case ErrorCode.SHOW_INTERVAL_LIMITED:
                                        Toast.makeText(VideoAdvActivity.this, "视频展示间隔限制", Toast.LENGTH_SHORT).show();
                                        break;
                                    case ErrorCode.WIDGET_NOT_IN_VISIBILITY_STATE:
                                        Toast.makeText(VideoAdvActivity.this, "视频控件处在不可见状态", Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        Toast.makeText(VideoAdvActivity.this, "请稍后再试", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }

                            @Override
                            public void onPlayCompleted() {
                                Toast.makeText(VideoAdvActivity.this, "视频播放成功", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

}
