package demo.soho.com.baogevideo.listener;


import com.shuyu.gsyvideoplayer.listener.VideoAllCallBack;

/**
 * Created by dell on 2017/2/21.
 */
public class SampleListener implements VideoAllCallBack {
    @Override
    public void onClickStartThumb(String s, Object... objects) {}
    @Override
    public void onClickBlank(String s, Object... objects) {}
    @Override
    public void onClickBlankFullscreen(String s, Object... objects) {}
    @Override
    public void onStartPrepared(String url, Object... objects) {}
    @Override
    public void onPrepared(String s, Object... objects) {}
    @Override
    public void onClickStartIcon(String s, Object... objects) {}
    @Override
    public void onClickStartError(String s, Object... objects) {}
    @Override
    public void onClickStop(String s, Object... objects) {}
    @Override
    public void onClickStopFullscreen(String s, Object... objects) {}
    @Override
    public void onClickResume(String s, Object... objects) {}
    @Override
    public void onClickResumeFullscreen(String s, Object... objects) {}
    @Override
    public void onClickSeekbar(String s, Object... objects) {}
    @Override
    public void onClickSeekbarFullscreen(String s, Object... objects) {}
    @Override
    public void onAutoComplete(String s, Object... objects) {}
    @Override
    public void onEnterFullscreen(String s, Object... objects) {}
    @Override
    public void onQuitFullscreen(String s, Object... objects) {}
    @Override
    public void onQuitSmallWidget(String s, Object... objects) {}
    @Override
    public void onEnterSmallWidget(String s, Object... objects) {}
    @Override
    public void onTouchScreenSeekVolume(String s, Object... objects) {}
    @Override
    public void onTouchScreenSeekPosition(String s, Object... objects) {}
    @Override
    public void onTouchScreenSeekLight(String s, Object... objects) {}
    @Override
    public void onPlayError(String s, Object... objects) {}
}
