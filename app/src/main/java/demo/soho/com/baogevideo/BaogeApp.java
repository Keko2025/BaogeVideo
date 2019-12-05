package demo.soho.com.baogevideo;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

/**
 * @author dell
 * @data 2018/1/20.
 */
public class BaogeApp extends Application {
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

        initFresco();
        initBugly();
        initUmeng();
        initXGAPush();
    }

    /**
     * bugly
     */
    private void initBugly() {
        Bugly.init(this, "0bead122a5", false);
        Beta.autoDownloadOnWifi = true;
        Beta.canShowApkInfo = true;
    }

    /**
     * 信鸽
     */
    private void initXGAPush() {
        XGPushConfig.enableDebug(this,true);
        XGPushManager.registerPush(this, new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                //token在设备卸载重装的时候有可能会变
            }
            @Override
            public void onFail(Object data, int errCode, String msg) {
            }
        });
    }

    /**
     * 初始化
     */
    private void initUmeng() {
        //设置log开关
        UMConfigure.setLogEnabled(true);
        //日志加密
        UMConfigure.setEncryptEnabled(true);
        UMConfigure.init(this, "5a93cd57f43e4877fe0002c8", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, null);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
    }

    private void initFresco() {
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .setResizeAndRotateEnabledForNetwork(true)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .build();
        Fresco.initialize(this, config);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);

        // 安装tinker
        Beta.installTinker();
    }
}
