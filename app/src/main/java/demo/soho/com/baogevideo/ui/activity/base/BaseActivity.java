package demo.soho.com.baogevideo.ui.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.liuguangqiang.swipeback.SwipeBackActivity;
import com.liuguangqiang.swipeback.SwipeBackLayout;

import demo.soho.com.baogevideo.R;

/**
 * @author dell
 * @data 2018/1/19.
 */

public class BaseActivity extends SwipeBackActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);

        setDragEdge(SwipeBackLayout.DragEdge.LEFT);
    }
}
