package demo.soho.com.baogevideo;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.tencent.bugly.Bugly;

import cn.jpush.android.api.JPushInterface;

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
        initJPush();

        Bugly.init(this, "00f3576606", true);  //TODO,发布时改 false
    }

    private void initJPush() {
        JPushInterface.setDebugMode(false);
        JPushInterface.init(this);
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
    }
}
