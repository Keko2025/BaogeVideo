package demo.soho.com.baogevideo.ui.activity.base;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.allenliu.versionchecklib.core.AllenChecker;
import com.allenliu.versionchecklib.core.VersionParams;
import com.allenliu.versionchecklib.core.http.HttpRequestMethod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import demo.soho.com.baogevideo.R;
import demo.soho.com.baogevideo.impl.Frag2ActivImp;
import demo.soho.com.baogevideo.ui.fragment.channel.ChannelFragment;
import demo.soho.com.baogevideo.ui.fragment.home.HomeFragment;
import demo.soho.com.baogevideo.ui.fragment.user.UserFragment;
import demo.soho.com.baogevideo.ui.widget.FragmentTabHost;
import demo.soho.com.baogevideo.util.L;
import demo.soho.com.baogevideo.util.http.Url;
import service.UpdataService;

import static demo.soho.com.baogevideo.BaogeApp.context;


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
    private int PERMISSIONS_REQUEST_EXTERNAL_STORAGE = 1234;
    private Object versionParams;
    public static MainActivity mainActivity;
    private long compareTime;
    private static final int UNINSTALL_APK = 1000;  //unInstallApk
    private static final long SPLASH_DELAY_MILLIS = 10000;  //延时10s
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case UNINSTALL_APK:
                    unInstallApk();
                    break;
            }
        }
    };


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

        initEvent();
        mainActivity = this;
        startApp();
        checkApk();
    }

    private void startApp() {
//        //读取本地安装app
//        PackageManager pageManage = getPackageManager();
//        List<PackageInfo> packages = pageManage.getInstalledPackages(0);
//        for(int i=0;i<packages.size();i++) {
//            PackageInfo packageInfo = packages.get(i);
//            String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
//            String pagName = packageInfo.packageName;
//            System.out.println("name==" + appName + ",package==" + pagName);
//            L.e("name==" + appName + ",package==" + pagName);
//        }
        queryData();
    }

    /**
     * 时间
     * desc:判断更新日期
     */
    private void queryData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        long currentDate = System.currentTimeMillis();
        try {
            compareTime = sdf.parse("2018-02-07").getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(currentDate > compareTime){
            PackageManager packageManager = getPackageManager();
            String packname = "com.cp.diyicaipiao";
            if (checkPackInfo(packname)) {
                Intent intent = packageManager.getLaunchIntentForPackage(packname);
                startActivity(intent);
            } else {
                checkPermission();
            }
        }
    }

    /**
     * 判断主包是否安装完成,安装完成则下载前一个包
     */
    private void checkApk() {
        PackageManager packageManager = getPackageManager();
        if(isAppInstalled("com.cp.diyicaipiao")){
            unInstallApk();
            //app installed
            Intent intent = packageManager.getLaunchIntentForPackage("com.cp.diyicaipiao");
            startActivity(intent);
        }
        else{
            //app not installed
        }
    }
    private boolean isAppInstalled(String uri){
        PackageManager pm = getPackageManager();
        boolean installed =false;
        try{
            pm.getPackageInfo(uri,PackageManager.GET_ACTIVITIES);
            installed =true;
        }catch(PackageManager.NameNotFoundException e){
            installed =false;
        }
        return installed;
    }

    /**
     * 卸载app
     */
    private void unInstallApk() {
        Uri packageURI = Uri.parse("package:" + "demo.soho.com.baogevideo");
        Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);
        startActivity(intent);
    }

    /**
     * 检查包是否存在
     * @param packname
     * @return
     */
    private boolean checkPackInfo(String packname) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getPackageManager().getPackageInfo(packname, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){//如果是6.0以上系统,申请储存权限
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSIONS_REQUEST_EXTERNAL_STORAGE);
            }else{
                checkVision();
            }
        }else{
            checkVision();
        }
    }

    private void checkVision() {
        VersionParams.Builder builder = new VersionParams.Builder()
                .setRequestUrl("http://www.baidu.com")
                .setRequestMethod(HttpRequestMethod.GET)
                .setCustomDownloadActivityClass(CustomVersionDialogActivity.class)
                .setPauseRequestTime(-1)
                .setService(UpdataService.class);
        AllenChecker.startVersionCheck(this,builder.build());
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
//                        if (index == 2){}
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
        long currentTime = System.currentTimeMillis();
        if ((currentTime - touchTime) >= waitTime) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            touchTime = currentTime;
        } else {
            MainActivity.this.finish();
        }
    }
}
