package demo.soho.com.baogevideo.ui.activity.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;

import abc.abc.abc.nm.cm.ErrorCode;
import abc.abc.abc.nm.sp.SpotListener;
import abc.abc.abc.nm.sp.SpotManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import demo.soho.com.baogevideo.R;
import demo.soho.com.baogevideo.ui.activity.base.BaseActivity;

/**
 * @author dell
 * @data 2018/1/25.
 */

public class VideoCacheActivity extends BaseActivity {
    @BindView(R.id.id_title)
    TextView idTitle;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache);
        ButterKnife.bind(this);

        //设置广告条
        setupBannerAd();
    }

    private void setupBannerAd() {

        SpotManager.getInstance(this).setImageType(SpotManager.IMAGE_TYPE_VERTICAL);
        SpotManager.getInstance(this)
                .setAnimationType(SpotManager.ANIMATION_TYPE_ADVANCED);

        // 展示轮播插屏广告
        SpotManager.getInstance(this)
                .showSlideableSpot(this, new SpotListener() {

                    @Override
                    public void onShowSuccess() {
                    }

                    @Override
                    public void onShowFailed(int errorCode) {
                        switch (errorCode) {
                            case ErrorCode.NON_NETWORK:
                                break;
                            case ErrorCode.NON_AD:
                                break;
                            case ErrorCode.RESOURCE_NOT_READY:
                                break;
                            case ErrorCode.SHOW_INTERVAL_LIMITED:
                                break;
                            case ErrorCode.WIDGET_NOT_IN_VISIBILITY_STATE:
                                break;
                            default:
                                break;
                        }
                    }

                    @Override
                    public void onSpotClosed() {}

                    @Override
                    public void onSpotClicked(boolean isWebPage) {
                    }
                });
    }

    @OnClick(R.id.id_back)
    public void onClick() {
        finish();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onBackPressed() {
        // 点击后退关闭轮播插屏广告
        if (SpotManager.getInstance(this).isSlideableSpotShowing()) {
            SpotManager.getInstance(this).hideSlideableSpot();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 轮播插屏广告
        SpotManager.getInstance(this).onPause();
        MobclickAgent.onPause(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        // 轮播插屏广告
        SpotManager.getInstance(this).onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 轮播插屏广告
        SpotManager.getInstance(this).onDestroy();
    }
}
