package demo.soho.com.baogevideo.ui.activity.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

import abc.abc.abc.nm.bn.BannerManager;
import abc.abc.abc.nm.bn.BannerViewListener;
import abc.abc.abc.nm.cm.ErrorCode;
import abc.abc.abc.nm.sp.SpotListener;
import abc.abc.abc.nm.sp.SpotManager;
import abc.abc.abc.nm.vdo.VideoAdManager;
import abc.abc.abc.nm.vdo.VideoAdRequestListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import demo.soho.com.baogevideo.R;
import demo.soho.com.baogevideo.impl.Frag2ActivImp;
import demo.soho.com.baogevideo.ui.fragment.channel.ChannelFragment;
import demo.soho.com.baogevideo.ui.fragment.home.HomeFragment;
import demo.soho.com.baogevideo.ui.fragment.user.UserFragment;
import demo.soho.com.baogevideo.ui.widget.FragmentTabHost;
import demo.soho.com.baogevideo.util.L;

public class MainActivity extends AppCompatActivity implements Frag2ActivImp {
    @BindView(android.R.id.tabhost)
    FragmentTabHost tabhost;

    private String texts[] = {"首页", "频道", "我的"};
    private int imageButton[] = {R.drawable.tab_home, R.drawable.tab_channel, R.drawable.tab_user};
    private Class fragmentArray[] = {HomeFragment.class, ChannelFragment.class, UserFragment.class};

    private int current;
    private int previous;
    long touchTime = 0;
    long waitTime = 2000;
    private HomeFragment homeFragment;
    private ChannelFragment channelFragment;
    private UserFragment userFragment;
    protected MainActivity mContext;

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        if(fragment instanceof HomeFragment){
            homeFragment = (HomeFragment) fragment;
        }else if(fragment instanceof ChannelFragment){
            channelFragment = (ChannelFragment) fragment;
        }else if(fragment instanceof UserFragment){
            userFragment = (UserFragment) fragment;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        current = getIntent().getIntExtra("current", 0);
        tabhost.setup(this, getSupportFragmentManager(), R.id.frameLayout);
        for(int i = 0; i < texts.length; i++){
            TabHost.TabSpec spec = tabhost.newTabSpec(texts[i]).setIndicator(getView(i));
            tabhost.addTab(spec, fragmentArray[i], null);
            tabhost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
        }
        L.e("current:"+current);
        tabhost.setCurrentTab(current);

        mContext = this;
        initEvent();
        // 预加载数据
        preloadData();
        //设置广告条
        setupBannerAd();
    }
    /**
     * 预加载数据
     */
    private void preloadData() {
        // 设置服务器回调 userId，一定要在请求广告之前设置，否则无效
        VideoAdManager.getInstance(mContext).setUserId("111");
        // 请求视频广告
        // 注意：不必每次展示视频广告前都请求，只需在应用启动时请求一次
        VideoAdManager.getInstance(mContext)
                .requestVideoAd(mContext, new VideoAdRequestListener() {

                    @Override
                    public void onRequestSuccess() {
                        L.e("请求视频广告成功");
                    }

                    @Override
                    public void onRequestFailed(int errorCode) {
                        L.e("请求视频广告失败，errorCode: %s" + errorCode);
                        switch (errorCode) {
                            case ErrorCode.NON_NETWORK:
                                Toast.makeText(mContext, "网络异常", Toast.LENGTH_SHORT).show();
                                break;
                            case ErrorCode.NON_AD:
                                Toast.makeText(mContext, "暂无视频广告", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(mContext, "请稍后再试", Toast.LENGTH_SHORT).show();
                                break;
                        }
                    }
                });
    }
    /**
     * 设置广告条广告
     */
    private void setupBannerAd() {
        /**
         * 悬浮布局
         */
        // 实例化LayoutParams
        FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置广告条的悬浮位置，这里示例为右下角
        layoutParams.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        // 获取广告条
        final View bannerView = BannerManager.getInstance(mContext)
                .getBannerView(mContext, new BannerViewListener() {

                    @Override
                    public void onRequestSuccess() {
                        L.e("请求广告条成功");
                    }

                    @Override
                    public void onSwitchBanner() {
                        L.e("广告条切换");
                    }

                    @Override
                    public void onRequestFailed() {
                        L.e("请求广告条失败");
                    }
                });
        // 添加广告条到窗口中
        ((Activity) mContext).addContentView(bannerView, layoutParams);
    }

    private void initEvent() {

        tabhost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                L.e(""+tabId);
                int index = 0;
                current = index;
                for(String s : texts){
                    if (tabId.equals(s)){
                        previous = current;
                        current = index;
                        break;
                    }
                    index++;
                }
            }
        });
    }

    private View getView(int pos) {
        View view = View.inflate(MainActivity.this, R.layout.tabcontent, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        TextView textView = (TextView) view.findViewById(R.id.text);

        imageView.setImageResource(imageButton[pos]);
        textView.setText(texts[pos]);
        return view;
    }
    @Override
    public void postData(Fragment fragment, int[] data) {
//        tabhost.setCurrentTab(data[0]);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode) {
            onBackPressed();
            return true;
        }else{
            return super.onKeyDown(keyCode, event);
        }
    }
    @Override
    public void onBackPressed() {
        // 点击后退关闭轮播插屏广告
        if (SpotManager.getInstance(mContext).isSlideableSpotShowing()) {
            SpotManager.getInstance(mContext).hideSlideableSpot();
        } else {
            super.onBackPressed();
        }

        long currentTime = System.currentTimeMillis();
        if ((currentTime - touchTime) >= waitTime) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            touchTime = currentTime;
        } else {
            MainActivity.this.finish();
            SpotManager.getInstance(this).onAppExit();
        }
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        // 轮播插屏广告
        SpotManager.getInstance(mContext).onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
        // 轮播插屏广告
        SpotManager.getInstance(mContext).onStop();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //展示广告条窗口的onDestroy()回调方法中调用
        SpotManager.getInstance(this).onDestroy();
        // 视频广告（包括普通视频广告、原生视频广告）
        VideoAdManager.getInstance(mContext).onAppExit();
        // 展示广告条窗口的 onDestroy() 回调方法中调用
        BannerManager.getInstance(mContext).onDestroy();
    }
}
