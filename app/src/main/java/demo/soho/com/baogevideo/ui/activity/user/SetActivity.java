package demo.soho.com.baogevideo.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import demo.soho.com.baogevideo.R;
import demo.soho.com.baogevideo.model.CodeBean;
import demo.soho.com.baogevideo.ui.activity.base.BaseActivity;
import demo.soho.com.baogevideo.util.SpUtil;
import demo.soho.com.baogevideo.util.http.OkHttpUtil;
import demo.soho.com.baogevideo.util.http.Url;

/**
 * @author dell
 * @data 2018/1/25.
 */

public class SetActivity extends BaseActivity {
    private String token;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);

        intent = getIntent();
        if(intent != null){
            token = intent.getStringExtra("token") != null ? intent.getStringExtra("token") : "";
        }
    }

    @OnClick({R.id.id_back, R.id.id_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_back:
                finish();
                break;
            case R.id.id_exit:
                exitApp();
                break;
        }
    }

    /**
     * 退出登录
     */
    private void exitApp() {
        Map<String,Object> paramters = new HashMap<>();
        paramters.put("token",token);
        new OkHttpUtil().post(Url.EXIT_APP_API, paramters, new OkHttpUtil.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                SpUtil.remove(getApplicationContext(), "token");
                CodeBean codeBean = new Gson().fromJson(data,CodeBean.class);
                Toast.makeText(SetActivity.this, codeBean.getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
            }
        });
    }
}
