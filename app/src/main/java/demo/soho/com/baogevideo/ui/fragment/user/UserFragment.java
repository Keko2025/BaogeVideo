package demo.soho.com.baogevideo.ui.fragment.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import demo.soho.com.baogevideo.BaogeApp;
import demo.soho.com.baogevideo.R;
import demo.soho.com.baogevideo.model.UserBean;
import demo.soho.com.baogevideo.ui.activity.user.CollectActivity;
import demo.soho.com.baogevideo.ui.activity.user.LoginActivity;
import demo.soho.com.baogevideo.ui.activity.user.NotifyActivity;
import demo.soho.com.baogevideo.ui.activity.user.RegisterActivity;
import demo.soho.com.baogevideo.ui.activity.user.ReplyActivity;
import demo.soho.com.baogevideo.ui.activity.user.SetActivity;
import demo.soho.com.baogevideo.ui.activity.user.ThumpActivity;
import demo.soho.com.baogevideo.ui.activity.user.UserLevelActivity;
import demo.soho.com.baogevideo.ui.activity.user.VideoCacheActivity;
import demo.soho.com.baogevideo.ui.activity.user.VideoHisActivity;
import demo.soho.com.baogevideo.ui.fragment.base.BaseFragment;
import demo.soho.com.baogevideo.util.L;
import demo.soho.com.baogevideo.util.SpUtil;
import demo.soho.com.baogevideo.util.StringUtils;
import demo.soho.com.baogevideo.util.http.OkHttpUtil;
import demo.soho.com.baogevideo.util.http.Url;

/**
 * @author dell
 * @data 2018/1/19.
 * 我的
 */

public class UserFragment extends BaseFragment {
    @BindView(R.id.user_default_ava)
    SimpleDraweeView channelAuthorImg;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.rl_no_login)
    RelativeLayout rlNoLogin;
    @BindView(R.id.rl_has_login)
    RelativeLayout hasLogin;
    @BindView(R.id.user_ava)
    SimpleDraweeView userAva;
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_nikename)
    TextView tvNikename;
    @BindView(R.id.tv_user_level)
    TextView tvUserLevel;
    private Context mContext;
    private String token;
    private UserBean userBean;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void initData() {
        Map<String, Object> parameters = new HashMap<>();
        new OkHttpUtil().post(Url.VIDEO_ALL_API, parameters, new OkHttpUtil.HttpCallback() {
            @Override
            public void onSuccess(String data) {

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

    @Override
    public void onResume() {
        super.onResume();
        token = (String) SpUtil.get(BaogeApp.context, "token", "");
        if (!StringUtils.isEmptyString(token)) {
            hasLogin.setVisibility(View.VISIBLE);
            rlNoLogin.setVisibility(View.GONE);
        } else {
            hasLogin.setVisibility(View.GONE);
            rlNoLogin.setVisibility(View.VISIBLE);
        }
        Object object = SpUtil.get("user", "user_data");

        if (object != null) {
            userBean = (UserBean) object;
            L.e("userData：" + userBean);
            if (userBean != null) {
                setData(userBean);
            }
        }
    }

    /**
     * 控件赋值
     *
     * @param userBean
     */
    private void setData(UserBean userBean) {
        tvUsername.setText(userBean.getData().getUsername());
        tvNikename.setText(userBean.getData().getMedal());
    }

    @OnClick({R.id.tv_register, R.id.tv_login, R.id.tv_user_level, R.id.tv_cache, R.id.tv_collect, R.id.tv_play_history, R.id.tv_response, R.id.tv_thump_up, R.id.tv_notify, R.id.tv_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_register:
                startActivity(new Intent(mContext, RegisterActivity.class));
                break;
            case R.id.tv_login:
                startActivity(new Intent(mContext, LoginActivity.class));
                break;
            case R.id.tv_user_level:
                startActivity(new Intent(mContext, UserLevelActivity.class));
                break;
            case R.id.tv_cache:
                startActivity(new Intent(mContext, VideoCacheActivity.class));
                break;
            case R.id.tv_collect:
                startActivity(new Intent(mContext, CollectActivity.class));
                break;
            case R.id.tv_play_history:
                startActivity(new Intent(mContext, VideoHisActivity.class));
                break;
            case R.id.tv_response:
                startActivity(new Intent(mContext, ReplyActivity.class));
                break;
            case R.id.tv_thump_up:
                startActivity(new Intent(mContext, ThumpActivity.class));
                break;
            case R.id.tv_notify:
                startActivity(new Intent(mContext, NotifyActivity.class));
                break;
            case R.id.tv_setting:
                startActivity(new Intent(mContext, SetActivity.class).putExtra("token",token));
                break;
        }
    }
}
