package demo.soho.com.baogevideo.ui.activity.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import demo.soho.com.baogevideo.R;
import demo.soho.com.baogevideo.ui.activity.base.BaseActivity;

/**
 * @author dell
 * @data 2018/1/25.
 */

public class VideoCacheActivity extends BaseActivity {
    @BindView(R.id.id_title)
    TextView idTitle;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cache);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.id_back)
    public void onClick() {
        finish();
    }
}
