package demo.soho.com.baogevideo.ui.fragment.home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import butterknife.BindView;
import butterknife.ButterKnife;
import demo.soho.com.baogevideo.R;
import demo.soho.com.baogevideo.ui.fragment.base.BaseFragment;

/**
 * @author dell
 * @data 2018/1/19.
 */

public class RankFragment extends BaseFragment {
    @BindView(R.id.tablayout)
    SmartTabLayout tablayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    private Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_rank_video;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(mContext)
                .add(R.string.week_rank,WeekFragment.class)
                .add(R.string.month_rank,MonthFragment.class)
                .add(R.string.total_rank,TotalFragment.class)
                .create()
        );

        viewpager.setAdapter(adapter);
        tablayout.setViewPager(viewpager);
    }

    @Override
    protected void initData() {

    }
}
