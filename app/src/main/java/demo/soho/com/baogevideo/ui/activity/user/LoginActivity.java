package demo.soho.com.baogevideo.ui.activity.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import demo.soho.com.baogevideo.BaogeApp;
import demo.soho.com.baogevideo.R;
import demo.soho.com.baogevideo.model.CodeBean;
import demo.soho.com.baogevideo.model.UserBean;
import demo.soho.com.baogevideo.ui.activity.base.BaseActivity;
import demo.soho.com.baogevideo.util.SpUtil;
import demo.soho.com.baogevideo.util.http.OkHttpUtil;
import demo.soho.com.baogevideo.util.http.Url;

/**
 * @author dell
 * @data 2018/1/25.
 */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.etv_user_phone)
    EditText etvUserPhone;
    @BindView(R.id.etv_password)
    EditText etvPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.id_back, R.id.tv_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_back:
                finish();
                break;
            case R.id.tv_login:
                doLogin();
                break;
        }
    }
    /**
     * 登录
     */
    private void doLogin() {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("username",etvUserPhone.getText().toString());
        parameters.put("password",etvPassword.getText().toString());
        parameters.put("device_token","AjVu6NusGxijUqcuYTFfnoYZQpctF8LPfPXz670nELSz");
        new OkHttpUtil().post(Url.USER_LOGIN_API, parameters, new OkHttpUtil.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                UserBean userbean = new Gson().fromJson(data,UserBean.class);
                if(userbean != null){
                    SpUtil.putAndApply(getApplicationContext(),"token",userbean.getData().getToken());
                    SpUtil.save("user","user_data",userbean);
                }
                Toast.makeText(LoginActivity.this, userbean.getMsg(), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                CodeBean codeBean = new Gson().fromJson(msg,CodeBean.class);
                Toast.makeText(LoginActivity.this, codeBean.getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
