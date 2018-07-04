package demo.soho.com.baogevideo.ui.activity.user.adv;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import abc.abc.abc.AdManager;
import abc.abc.abc.listener.Interface_ActivityListener;
import abc.abc.abc.listener.OffersWallDialogListener;
import abc.abc.abc.onlineconfig.OnlineConfigCallBack;
import abc.abc.abc.onlineconfig.ntp.NtpResultListener;
import abc.abc.abc.os.EarnPointsOrderInfo;
import abc.abc.abc.os.EarnPointsOrderList;
import abc.abc.abc.os.OffersBrowserConfig;
import abc.abc.abc.os.OffersManager;
import abc.abc.abc.os.PointsChangeNotify;
import abc.abc.abc.os.PointsEarnNotify;
import abc.abc.abc.os.PointsManager;
import demo.soho.com.baogevideo.R;

import static demo.soho.com.baogevideo.BaogeApp.context;

/**
 * @author dell
 * @data 2018/1/25.
 */

public class OffersAdsActivity extends Activity
        implements View.OnClickListener, PointsChangeNotify, PointsEarnNotify {

    /**
     * 显示积分余额的控件
     */
    TextView mTextViewPoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);

        mTextViewPoints = (TextView) findViewById(R.id.pointsBalance);
        findViewById(R.id.btn_show_offerswall).setOnClickListener(this);
        findViewById(R.id.btn_show_offerswall_dialog).setOnClickListener(this);
        findViewById(R.id.btn_award_points).setOnClickListener(this);
        findViewById(R.id.btn_spend_points).setOnClickListener(this);
        findViewById(R.id.btn_get_online_config).setOnClickListener(this);
        findViewById(R.id.btn_check_reach_ntp_time).setOnClickListener(this);
        findViewById(R.id.btn_check_ad_config).setOnClickListener(this);

        showDemoInfo();

        initYoumi();
    }

    private void initYoumi() {
        // 自v6.3.0起，所有其他代码必须在初始化接口调用之后才能生效
        // 初始化接口，应用启动的时候调用，参数：appId, appSecret, isEnableYoumiLog
        // 有米android 积分墙sdk 5.0.0之后支持定制浏览器顶部标题栏的部分UI
        // setOfferBrowserConfig();

        // 如果开发者使用积分墙的服务器回调,
        // 1.需要告诉sdk，现在采用服务器回调
        // 2.建议开发者传入自己系统中用户id（如：邮箱账号之类的）（请限制在50个字符串以内）
        // 3.务必在下面的OffersManager.getInstance(this).onAppLaunch();代码之前声明使用服务器回调

         OffersManager.getInstance(this).setUsingServerCallBack(true);
         OffersManager.getInstance(this).setCustomUserId("111");

        // 如果使用积分广告，请务必调用积分广告的初始化接口:
        OffersManager.getInstance(this).onAppLaunch();

        // (可选)注册积分监听-随时随地获得积分的变动情况
        PointsManager.getInstance(this).registerNotify(this);

        // (可选)注册积分订单赚取监听（sdk v4.10版本新增功能）
        PointsManager.getInstance(this).registerPointsEarnNotify(this);

        // (可选)设置是否在通知栏显示下载相关提示。默认为true，标识开启；设置为false则关闭。（sdk v4.10版本新增功能）
        // AdManager.getInstance(this).setIsDownloadTipsDisplayOnNotification(false);

        // (可选)设置安装完成后是否在通知栏显示已安装成功的通知。默认为true，标识开启；设置为false则关闭。（sdk v4.10版本新增功能）
        // AdManager.getInstance(this).setIsInstallationSuccessTipsDisplayOnNotification(false);

        // (可选)设置是否在通知栏显示积分赚取提示。默认为true，标识开启；设置为false则关闭。
        // 如果开发者采用了服务器回调积分的方式，那么本方法将不会生效
        // PointsManager.getInstance(this).setEnableEarnPointsNotification(false);

        // (可选)设置是否开启积分赚取的Toast提示。默认为true，标识开启；设置为false这关闭。
        // 如果开发者采用了服务器回调积分的方式，那么本方法将不会生效
        // PointsManager.getInstance(this).setEnableEarnPointsToastTips(false);

        // 查询积分余额
        // 从5.3.0版本起，客户端积分托管将由 int 转换为 float
        float pointsBalance = PointsManager.getInstance(this).queryPoints();
        mTextViewPoints.setText("积分余额：" + pointsBalance);
    }

    /**
     * 退出时回收资源
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // （可选）注销积分监听
        // 如果在onCreate调用了PointsManager.getInstance(this).registerNotify(this)进行积分余额监听器注册，那这里必须得注销
        PointsManager.getInstance(this).unRegisterNotify(this);

        // （可选）注销积分订单赚取监听
        // 如果在onCreate调用了PointsManager.getInstance(this).registerPointsEarnNotify(this)进行积分订单赚取监听器注册，那这里必须得注销
        PointsManager.getInstance(this).unRegisterPointsEarnNotify(this);

        // 回收积分广告占用的资源
        OffersManager.getInstance(this).onAppExit();
    }

    /**
     * 积分余额发生变动时，就会回调本方法（本回调方法执行在UI线程中）
     * <p/>
     * 从5.3.0版本起，客户端积分托管将由 int 转换为 float
     */
    @Override
    public void onPointBalanceChange(float pointsBalance) {
        mTextViewPoints.setText("积分余额：" + pointsBalance);
    }

    /**
     * 积分订单赚取时会回调本方法（本回调方法执行在UI线程中）
     */
    @Override
    public void onPointEarn(Context context, EarnPointsOrderList list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        // 遍历订单并且toast提示
        for (int i = 0; i < list.size(); ++i) {
            EarnPointsOrderInfo info = list.get(i);
            Toast.makeText(this, info.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {

            // 展示全屏的积分墙界面
            case R.id.btn_show_offerswall:

                // 调用方式一：直接打开全屏积分墙
                // OffersManager.getInstance(this).showOffersWall();

                // 调用方式二：直接打开全屏积分墙，并且监听积分墙退出的事件onDestory
                OffersManager.getInstance(this).showOffersWall(new Interface_ActivityListener() {

                    /**
                     * 当积分墙销毁的时候，即积分墙的Activity调用了onDestory的时候回调
                     */
                    @Override
                    public void onActivityDestroy(Context context) {
                        Toast.makeText(context, "全屏积分墙退出了", Toast.LENGTH_SHORT).show();
                    }
                });

                break;

            // 展示对话框的积分墙界面(本方法支持多种重载格式，开发者可以参考文档或者使用代码提示快捷键来了解)
            case R.id.btn_show_offerswall_dialog:
                OffersManager.getInstance(this).showOffersWallDialog(this, new OffersWallDialogListener() {

                    @Override
                    public void onDialogClose() {
                        Toast.makeText(OffersAdsActivity.this, "积分墙对话框关闭了", Toast.LENGTH_SHORT).show();
                    }
                });
                break;

            // 奖励10积分, 注：调用本方法后，积分余额马上变更，可留意onPointBalanceChange是不是被调用了
            // 从5.3.0版本起，客户端积分托管将由 int 转换为 float
            case R.id.btn_award_points:
                PointsManager.getInstance(this).awardPoints(10.0f);
                break;

            // 消费20积分, 注：调用本方法后，积分余额马上变更，可留意onPointBalanceChange是不是被调用了
            // 从5.3.0版本起，客户端积分托管将由 int 转换为 float
            case R.id.btn_spend_points:
                PointsManager.getInstance(this).spendPoints(20.0f);
                break;

            // 获取在线参数
            case R.id.btn_get_online_config:
                Toast.makeText(this, "获取在线参数中...", Toast.LENGTH_LONG).show();

                // 注意：这里获取的在线参数的key为 ：isOpen，为演示的key ， 开发者需要将key替换为开发者在自己有米后台上面设置的key
                AdManager.getInstance(this).asyncGetOnlineConfig("isOpen", new OnlineConfigCallBack() {
                    /**
                     * 获取在线参数成功就会回调本方法（本回调方法执行在UI线程中）
                     */
                    @Override
                    public void onGetOnlineConfigSuccessful(String key, String value) {
                        // 获取在线参数成功
                        Toast.makeText(OffersAdsActivity.this,
                                String.format("在线参数获取结果：\nkey=%s, value=%s", key, value),
                                Toast.LENGTH_LONG
                        )
                                .show();

                        // //
                        // 开发者在这里可以判断一下获取到的value值，然后设置一个boolean值并将其保存在文件中，每次调用广告之前从文件中获取boolean
                        // 值并判断一下是否可以展示广告
                        // if (key.equals("isOpen")) {
                        // if (value.equals("1")) {
                        // // 如果满足开发者自己的定义：如示例中如果key=isOpen value=1 则定义为开启广告
                        // // 则将flag（boolean值）设置为true，然后每次调用广告代码之前都判断一下flag，如果flag为true则执行展示广告的代码
                        // flag = true;
                        // // 写入文件 ...
                        // }
                        // }

                    }

                    /**
                     * 获取在线参数失败就会回调本方法（本回调方法执行在UI线程中）
                     */
                    @Override
                    public void onGetOnlineConfigFailed(String key) {
                        // 获取在线参数失败，可能原因有：键值未设置或为空、网络异常、服务器异常
                        Toast.makeText(OffersAdsActivity.this,
                                String.format("在线参数获取结果：\n获取在线key=%s失败!\n具体失败原因请查看Log，Log标签：YoumiSdk", key),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
                break;

            // 检查是否达到指定的网络时间——ntp
            // 使用场合：开发者可以指定一个日期，然后到达该日期之后才开启广告
            // 具体做法：当获取到结果的时候，可以把结果（boolean）保存在文件中，然后每次启动的时候获取文件的内容，然后根据内容来判断是否开启广告
            case R.id.btn_check_reach_ntp_time:

                // 检查现在是否到达2014年11月12号 GTM+8
                final int targetYear = 2014;
                final int targetMonth = 11;
                final int targetMonthDay = 15;

                // 这里演示异步方法的使用，同步方法的的使用请查看文档
                AdManager.getInstance(this)
                        .asyncCheckIsReachNtpTime(targetYear,
                                targetMonth,
                                targetMonthDay,
                                new NtpResultListener() {

                                    /**
                                     * 当获取到网络时间时，会回调本方法（本方法执行在UI线程中）
                                     */
                                    @Override
                                    public void onCheckNtpFinish(boolean result) {
                                        String logText = String.format(Locale.getDefault(),
                                                "是否到达日期: %d-%d-%d " + "%s",
                                                targetYear,
                                                targetMonth,
                                                targetMonthDay,
                                                result ? "是" : "否"
                                        );
                                        Log.i("ntp_", logText);
                                        Toast.makeText(OffersAdsActivity.this, logText, Toast.LENGTH_LONG).show();
                                    }
                                }
                        );

                break;

            // 检查积分墙广告配置
            case R.id.btn_check_ad_config:
                checkConfig();
                break;

            default:
                break;
        }
    }

    /**
     * 检查广告配置
     */
    private void checkConfig() {
        StringBuilder sb = new StringBuilder();

        addTextToSb(sb,
                OffersManager.getInstance(this).checkOffersAdConfig() ? "广告配置结果：正常" :
                        "广告配置结果：异常，具体异常请查看Log，Log标签：YoumiSdk"
        );
        addTextToSb(sb, "%s服务器回调", OffersManager.getInstance(this).isUsingServerCallBack() ? "已经开启" : "没有开启");
        addTextToSb(sb,
                "%s通知栏下载相关的通知",
                AdManager.getInstance(this).isDownloadTipsDisplayOnNotification() ? "已经开启" : "没有开启"
        );
        addTextToSb(sb,
                "%s通知栏安装成功的通知",
                AdManager.getInstance(this).isInstallationSuccessTipsDisplayOnNotification() ? "已经开启" : "没有开启"
        );
        addTextToSb(sb,
                "%s通知栏赚取积分的提示",
                PointsManager.getInstance(this).isEnableEarnPointsNotification() ? "已经开启" : "没有开启"
        );
        addTextToSb(sb,
                "%s积分赚取的Toast提示",
                PointsManager.getInstance(this).isEnableEarnPointsToastTips() ? "已经开启" : "没有开启"
        );

        new AlertDialog.Builder(this).setTitle("检查结果")
                .setMessage(sb.toString())
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }

                })
                .create()
                .show();
    }

    /**
     * 格式化字符串
     */
    private void addTextToSb(StringBuilder sb, String format, Object... args) {
        sb.append(String.format(format, args));
        sb.append(System.getProperty("line.separator"));
    }

    /**
     * 设置积分墙浏览器标题栏样式
     */
    private void setOfferBrowserConfig() {

        // 设置标题栏——标题
        OffersBrowserConfig.getInstance(this).setBrowserTitleText("秒取积分");

        // 设置标题栏——背景颜色（ps：积分墙标题栏默认背景颜色为#FFBB34）
        OffersBrowserConfig.getInstance(this).setBrowserTitleBackgroundColor(Color.BLUE);

        // 设置标题栏——是否显示积分墙右上角积分余额区域 true：是 false：否
        OffersBrowserConfig.getInstance(this).setPointsLayoutVisibility(true);

        // 设置标题栏——是否显示有米的logo
        OffersBrowserConfig.getInstance(this).setLogoVisibility(false);

    }

    /**
     * 显示demo的一些详细信息
     */
    private void showDemoInfo() {
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(this.getPackageName(), 0);
            String message = String.format(Locale.getDefault(),
                    "vc: %d, vn: %s, install time: %3$tY-%3$tm-%3$td %3$tH:%3$tM:%3$tS",
                    packageInfo.versionCode,
                    packageInfo.versionName,
                    packageInfo.firstInstallTime
            );
            ((TextView) findViewById(R.id.tv_version)).setText(message);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }
}
