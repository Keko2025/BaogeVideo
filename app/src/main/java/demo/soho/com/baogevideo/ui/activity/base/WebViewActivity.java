package demo.soho.com.baogevideo.ui.activity.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;


import butterknife.BindView;
import butterknife.ButterKnife;
import demo.soho.com.baogevideo.R;

/**
 * @author dell
 * @data 2018/1/2.
 */
public class WebViewActivity extends AppCompatActivity {
    private static final int FILECHOOSER_RESULTCODE = 1111;
    @BindView(R.id.webView)
    WebView webView;
    private ProgressBar progressbar;
    private WebSettings settings;
    private String url;
    final Activity activity = this;
    private ValueCallback<Uri[]> mUploadCallbackAboveL;
    private ValueCallback<Uri> mUploadMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        ButterKnife.bind(this);


        url = getIntent().getStringExtra("url");
        initWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {

        settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);//设置可以支持缩放
        settings.setDisplayZoomControls(false); //隐藏webview缩放按钮

        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);

        if (Build.VERSION.SDK_INT >= 21) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setAppCacheEnabled(true);
        settings.setUseWideViewPort(true);      //将图片调整到适合webview的大小
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webView.loadUrl(url);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
