package demo.soho.com.baogevideo.ui.activity.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import demo.soho.com.baogevideo.R;
import demo.soho.com.baogevideo.model.VideoDescBean;
import demo.soho.com.baogevideo.ui.widget.LandLayoutVideo;
import demo.soho.com.baogevideo.util.L;
import demo.soho.com.baogevideo.util.http.OkHttpUtil;
import demo.soho.com.baogevideo.util.http.Url;

/**
 * @author dell
 * @data 2018/1/20.
 */

public class VideoDesActivity extends AppCompatActivity {
    @BindView(R.id.detail_player)
    LandLayoutVideo detailPlayer;
    @BindView(R.id.toolbar_tab)
    TabLayout toolbarTab;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    private SVProgressHUD progressDialog;
    private String videoId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_desc);

        ButterKnife.bind(this);
        progressDialog = new SVProgressHUD(this);
        Intent intent = getIntent();
        if (intent != null) {
            videoId = intent.getStringExtra("videoId");
            iniData(videoId);
        }
    }

    private void iniData(String videoId) {
        L.e("videoId:"+videoId);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id",videoId);
        parameters.put("token","ffbafc11f95f30402c814b89727af15e");
        new OkHttpUtil().post(Url.VIDEO_DESC_API, parameters, new OkHttpUtil.HttpCallback() {
            @Override
            public void onStart() {
                super.onStart();
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
            public void onSuccess(String data) {
                L.e("response:" + data);
                VideoDescBean videoBean = new Gson().fromJson(data,VideoDescBean.class);
            }
        });
    }
}
