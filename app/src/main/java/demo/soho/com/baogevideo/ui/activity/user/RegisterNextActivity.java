package demo.soho.com.baogevideo.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import demo.soho.com.baogevideo.R;
import demo.soho.com.baogevideo.model.CodeBean;
import demo.soho.com.baogevideo.ui.activity.base.BaseActivity;
import demo.soho.com.baogevideo.util.L;
import demo.soho.com.baogevideo.util.StringUtils;
import demo.soho.com.baogevideo.util.http.OkHttpUtil;
import demo.soho.com.baogevideo.util.http.Url;

import static android.R.attr.data;

/**
 * @author dell
 * @data 2018/1/25.
 */

public class RegisterNextActivity extends BaseActivity {
    @BindView(R.id.etv_username)
    EditText etvUsername;
    @BindView(R.id.etv_password)
    EditText etvPassword;
    @BindView(R.id.etv_confirm_password)
    EditText etvConfirmPassword;
    private Intent intent;
    private String code;
    private String userPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_next);
        ButterKnife.bind(this);

        intent = getIntent();
        if(intent != null){
            code = intent.getStringExtra("code");
            userPhone = intent.getStringExtra("phone");
        }
    }

    @OnClick({R.id.id_back, R.id.id_achieve})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_back:
                finish();
                break;
            case R.id.id_achieve:
                if(StringUtils.isEmptyString(etvUsername.getText().toString())){
                    Toast.makeText(this, "请填写昵称", Toast.LENGTH_SHORT).show();
                }else if(StringUtils.isEmptyString(etvPassword.getText().toString())){
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                }
                achieveRegister();
                break;
        }
    }

    /**
     * 完成注册
     * TODO: device_token:未知加密方式
     */
    private void achieveRegister() {
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("mobile",etvUsername.getText().toString());
        parameters.put("password",etvPassword.getText().toString());
        parameters.put("verify_code",code);
        parameters.put("username",userPhone);
        parameters.put("device_token","");
        new OkHttpUtil().post(Url.COMPLETE_REGISTER, parameters, new OkHttpUtil.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                L.e("register:" + data);
                CodeBean codeBean = new Gson().fromJson(data,CodeBean.class);
                Toast.makeText(RegisterNextActivity.this, codeBean.getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
                CodeBean codeBean = new Gson().fromJson(msg,CodeBean.class);
                Toast.makeText(RegisterNextActivity.this, codeBean.getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
            }
        });
    }
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
