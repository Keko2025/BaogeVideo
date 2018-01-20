package demo.soho.com.baogevideo.ui.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import demo.soho.com.baogevideo.R;
import demo.soho.com.baogevideo.ui.fragment.base.BaseFragment;
import demo.soho.com.baogevideo.util.TabUtil;


/**
 * @author dell
 * @data 2018/1/19.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.toolbar_tab)
    TabLayout toolbarTab;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private AllVideoFragment allVideoFragment;
    private RankFragment rankFragment;
    private List<Fragment> list = new ArrayList<Fragment>();
    private Context mContext;
    private View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {}

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this,view);
        initTab();
        if (allVideoFragment == null) {
            allVideoFragment = new AllVideoFragment();
        }
        if (rankFragment == null) {
            rankFragment = new RankFragment();
        }
        list.add(allVideoFragment);
        list.add(rankFragment);

        FragmentPagerAdapter viewPageAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

        };
        viewPager.setAdapter(viewPageAdapter);
        viewPager.setOffscreenPageLimit(1); //预加载
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(toolbarTab));
        toolbarTab.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }
    private void initTab() {
        toolbarTab.post(new Runnable() {
            @Override
            public void run() {
                TabUtil.setIndicator(toolbarTab,30,30);
            }
        });
    }
    @OnClick(R.id.search)
    public void onClick() {}
}
