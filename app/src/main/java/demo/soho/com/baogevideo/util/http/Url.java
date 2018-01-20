package demo.soho.com.baogevideo.util.http;

import demo.soho.com.baogevideo.BuildConfig;

/**
 * @author dell
 * @data 2018/1/20.
 */

public class Url {
    public static final String BASE_URL = BuildConfig.URL_HOST;

    public static String VIDEO_ALL_API = BASE_URL + "index.php?s=/Video/index";
    public static String VIDEO_DESC_API = BASE_URL + "/index.php?s=/Video/detail";

}
