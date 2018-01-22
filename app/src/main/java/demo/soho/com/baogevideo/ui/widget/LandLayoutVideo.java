package demo.soho.com.baogevideo.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import demo.soho.com.baogevideo.R;


/**
 * Created by dell on 2017/2/24.
 */
public class LandLayoutVideo extends StandardGSYVideoPlayer {
    private boolean isSelected = false;
    private ImageView startImg;
    private LinearLayout bottomView;

    /**
     * 1.5.0开始加入，如果需要不同布局区分功能，需要重载
     */
    public LandLayoutVideo(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }
    public LandLayoutVideo(Context context) {
        super(context);
    }
    public LandLayoutVideo(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    //这个必须配置最上面的构造才能生效

    @Override
    public int getLayoutId() {
        if (mIfCurrentIsFullscreen) {
            return R.layout.sample_video_land;
        }
        return R.layout.sample_video_normal;
    }
    public ImageView getStartImg() {
        return startImg;
    }

    public LinearLayout getBottomView(){
        return bottomView;
    }
    @Override
    protected void init(final Context context) {
        super.init(context);
        GSYVideoType.setShowType(GSYVideoType.SCREEN_MATCH_FULL);
        if (mIfCurrentIsFullscreen) {
        }
        bottomView = (LinearLayout) findViewById(R.id.layout_bottom);
        startImg = (ImageView) findViewById(R.id.start);
    }
    @Override
    protected void updateStartImage() {
        ImageView imageView = (ImageView) mStartButton;
        if (mCurrentState == CURRENT_STATE_PLAYING) {
            imageView.setImageResource(R.drawable.play_pause);
        } else if (mCurrentState == CURRENT_STATE_ERROR) {
            imageView.setImageResource(R.drawable.play_start);
        }else if(mCurrentState == CURRENT_STATE_AUTO_COMPLETE){
            imageView.setImageResource(R.drawable.play_start);
        }else {
            imageView.setImageResource(R.drawable.play_start);
        }
    }
}
