package demo.soho.com.baogevideo.ui.activity.user.adv;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import abc.abc.abc.nm.cm.ErrorCode;
import abc.abc.abc.nm.sp.SpotListener;
import abc.abc.abc.nm.sp.SpotManager;
import demo.soho.com.baogevideo.R;
import demo.soho.com.baogevideo.ui.activity.base.BaseActivity;
import demo.soho.com.baogevideo.util.L;

import static com.umeng.commonsdk.stateless.UMSLEnvelopeBuild.mContext;

/**
 * @author dell
 * @data 2018/1/25.
 */

public class NativeSpotAdActivity extends BaseActivity {
    private RelativeLayout mNativeSpotAdLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_spot_ad);
        // 设置原生插屏广告
        setupNativeSpotAd();
    }

    /**
     * 设置原生插屏广告
     */
    public void setupNativeSpotAd() {
        mNativeSpotAdLayout = (RelativeLayout) findViewById(R.id.rl_native_spot_ad);

        //	设置插屏图片类型，默认竖图
        //		//	横图
        //		SpotManager.getInstance(mContext).setImageType(SpotManager
        // .IMAGE_TYPE_HORIZONTAL);
        // 竖图
        SpotManager.getInstance(mContext).setImageType(SpotManager.IMAGE_TYPE_VERTICAL);

        // 设置动画类型，默认高级动画
        //		// 无动画
        //		SpotManager.getInstance(mContext).setAnimationType(SpotManager
        //				.ANIMATION_TYPE_NONE);
        //		// 简单动画
        //		SpotManager.getInstance(mContext)
        //		                    .setAnimationType(SpotManager.ANIMATION_TYPE_SIMPLE);
        // 高级动画
        SpotManager.getInstance(mContext)
                .setAnimationType(SpotManager.ANIMATION_TYPE_ADVANCED);

        Button btnShowNativeSpotAd = (Button) findViewById(R.id.btn_show_native_spot_ad);
        btnShowNativeSpotAd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 获取原生插屏控件
                View nativeSpotView = SpotManager.getInstance(mContext)
                        .getNativeSpot(mContext, new SpotListener() {

                            @Override
                            public void onShowSuccess() {
                                L.e("原生插屏展示成功");
                            }

                            @Override
                            public void onShowFailed(int errorCode) {
                                L.e("原生插屏展示失败");
                                switch (errorCode) {
                                    case ErrorCode.NON_NETWORK:
                                        Toast.makeText(NativeSpotAdActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                                        break;
                                    case ErrorCode.NON_AD:
                                        Toast.makeText(NativeSpotAdActivity.this, "暂无原生插屏广告", Toast.LENGTH_SHORT).show();
                                        break;
                                    case ErrorCode.RESOURCE_NOT_READY:
                                        Toast.makeText(NativeSpotAdActivity.this, "原生插屏资源还没准备好", Toast.LENGTH_SHORT).show();
                                        break;
                                    case ErrorCode.SHOW_INTERVAL_LIMITED:
                                        Toast.makeText(NativeSpotAdActivity.this, "请勿频繁展示", Toast.LENGTH_SHORT).show();
                                        break;
                                    case ErrorCode.WIDGET_NOT_IN_VISIBILITY_STATE:
                                        Toast.makeText(NativeSpotAdActivity.this, "请设置插屏为可见状态", Toast.LENGTH_SHORT).show();
                                        break;
                                    default:
                                        Toast.makeText(NativeSpotAdActivity.this, "请稍后再试", Toast.LENGTH_SHORT).show();
                                        break;
                                }
                            }

                            @Override
                            public void onSpotClosed() {
                                L.e("原生插屏被关闭");
                            }

                            @Override
                            public void onSpotClicked(boolean isWebPage) {
                                L.e("原生插屏被点击");
                                L.e("是否是网页广告？%s", isWebPage ? "是" : "不是");
                            }
                        });
                if (nativeSpotView != null) {
                    RelativeLayout.LayoutParams layoutParams =
                            new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                            );
                    layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
                    if (mNativeSpotAdLayout != null) {
                        mNativeSpotAdLayout.removeAllViews();
                        // 添加原生插屏控件到容器中
                        mNativeSpotAdLayout.addView(nativeSpotView, layoutParams);
                        if (mNativeSpotAdLayout.getVisibility() != View.VISIBLE) {
                            mNativeSpotAdLayout.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        //原生控件点击后退关闭
        if (mNativeSpotAdLayout != null && mNativeSpotAdLayout.getVisibility() != View.GONE) {
            mNativeSpotAdLayout.removeAllViews();
            mNativeSpotAdLayout.setVisibility(View.GONE);
            return;
        }
        super.onBackPressed();
    }
}
