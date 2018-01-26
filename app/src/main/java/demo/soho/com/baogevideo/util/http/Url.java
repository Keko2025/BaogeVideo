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
    //获取验证码
    public static String GET_PHONE_CODE = BASE_URL + "index.php?s=/Member/sendSMS";
    //完成注册
    public static String COMPLETE_REGISTER = BASE_URL + "index.php?s=/Member/register";
    //登录
    public static String USER_LOGIN_API = BASE_URL + "index.php?s=/Member/login";
    //视频收藏
    public static String VIDEO_COLLECT_ADD_API = BASE_URL + "index.php?s=/Collects/add";
    //我的收藏
    public static String VIDEO_MY_COLLECT_API = BASE_URL + "index.php?s=/Collects/my";
    //清空所有缓存列表
    public static String CLEAR_COLLECT_LIST_API = BASE_URL + "index.php?s=/Collects/del";
    //退出登录
    public static String EXIT_APP_API = BASE_URL + "index.php?s=/Member/logout";

}
