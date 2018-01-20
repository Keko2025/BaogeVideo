package demo.soho.com.baogevideo.ui.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import demo.soho.com.baogevideo.R;

/**
 * @author dell
 * @data 2018/1/19.
 */

public class SplashActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }
}
