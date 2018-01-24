package demo.soho.com.baogevideo.util.http;

import demo.soho.com.baogevideo.BuildConfig;

/**
 * @author dell
 * @data 2018/1/20.
 */

public class Url {

    public static final String BASE_URL = BuildConfig.URL_HOST;

    //首页全部视频api
    public static String VIDEO_ALL_API = BASE_URL + "index.php?s=/Video/index";
    //视频详情
    public static String VIDEO_DESC_API = BASE_URL + "index.php?s=/Video/detail";
    //相关视频
    public static String VIDEO_RELATED = BASE_URL + "index.php?s=/Video/index";
    //视频评论
    public static String VIDEO_COMMONT = BASE_URL + "index.php?s=/Comments/index";
    //排行
    public static String VIDEO_WEEK_RANK = BASE_URL + "index.php?s=/Video/ranking";
    //精选频道api
    public static String VIDEO_CHANNEL_API = BASE_URL + "index.php?s=/Channel/index";
    //频道详情
    public static String VIDEO_CHANNEL_DESC_API = BASE_URL + "index.php?s=/Channel/detail";
    //频道热门排名
    public static String VIDEO_CHANNEL_HOT = BASE_URL + "index.php?s=/Video/index";
    //频道时间排名
    public static String VIDEO_CHANNEL_TIME = BASE_URL + "index.php?s=/Video/index";

}
