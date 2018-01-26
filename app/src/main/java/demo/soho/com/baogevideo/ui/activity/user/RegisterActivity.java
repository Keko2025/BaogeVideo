package demo.soho.com.baogevideo.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import demo.soho.com.baogevideo.R;
import demo.soho.com.baogevideo.model.CodeBean;
import demo.soho.com.baogevideo.ui.activity.base.BaseActivity;
import demo.soho.com.baogevideo.util.CountDownTimerUtils;
import demo.soho.com.baogevideo.util.StringUtils;
import demo.soho.com.baogevideo.util.http.OkHttpUtil;
import demo.soho.com.baogevideo.util.http.Url;

/**
 * @author dell
 * @data 2018/1/25.
 */

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.etv_user_phone)
    EditText userPhone;
    @BindView(R.id.etv_phone_code)
    EditText phoneCode;
    @BindView(R.id.btn_get_code)
    Button getCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.id_back, R.id.btn_get_code, R.id.tv_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_back:
                finish();
                break;
            case R.id.btn_get_code:
                if(StringUtils.isEmptyString(userPhone.getText().toString())){
                    Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                    return;
                }
                CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(getCode, 60000, 1000);
                mCountDownTimerUtils.start();
                getCode(userPhone.getText().toString());
                break;
            case R.id.tv_next:
                if(!StringUtils.isEmptyString(userPhone.getText().toString()) && !StringUtils.isEmptyString(phoneCode.getText().toString())){
                    startActivity(new Intent(this,RegisterNextActivity.class)
                            .putExtra("code",phoneCode.getText().toString())
                            .putExtra("phone",userPhone.getText().toString()));
                }else {
                    Toast.makeText(this, "手机或验证码不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    /**
     * 获取验证码
     * @param s
     */
    private void getCode(String phoneNo) {
        Map<String,Object> parameters = new HashMap<>();
        parameters.put("mobile",phoneNo);
        new OkHttpUtil().post(Url.GET_PHONE_CODE, parameters, new OkHttpUtil.HttpCallback() {
            @Override
            public void onSuccess(String data) {
                CodeBean codeBean = new Gson().fromJson(data,CodeBean.class);
                Toast.makeText(RegisterActivity.this, codeBean.getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String msg) {
                super.onError(msg);
            }

            @Override
            public void onFailure(String msg) {
                super.onFailure(msg);
            }

            @Override
            public void onStart() {
                super.onStart();
            }
        });
    }
}
