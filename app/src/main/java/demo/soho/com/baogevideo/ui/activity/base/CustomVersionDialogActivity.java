package demo.soho.com.baogevideo.ui.activity.base;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.allenliu.versionchecklib.callback.APKDownloadListener;
import com.allenliu.versionchecklib.callback.CommitClickListener;
import com.allenliu.versionchecklib.callback.DialogDismissListener;
import com.allenliu.versionchecklib.core.VersionDialogActivity;

import java.io.File;

/**
 * @author dell
 * @data 2018/1/29.
 */

public class CustomVersionDialogActivity extends VersionDialogActivity implements CommitClickListener, DialogDismissListener, APKDownloadListener {
    public static int customVersionDialogIndex = 3;
    public static boolean isForceUpdate = true;
    public static boolean isCustomDownloading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //这里是几个回调
        setApkDownloadListener(this);
        setCommitClickListener(this);
        setDialogDimissListener(this);
    }

    /**
     * 下载文件成功也关闭app
     * 也判断是否强制更新
     *
     * @param file
     */
    @Override
    public void onDownloadSuccess(File file) {
        forceCloseApp();
        Log.e("CustomVersionDialogActi", "文件下载成功回调");
    }

    @Override
    public void onDownloadFail() {}

    @Override
    public void onDownloading(int progress) {
//        Log.e("CustomVersionDialogActi", "正在下载中回调...");
    }

    @Override
    public void onCommitClick() {
        Log.e("CustomVersionDialogActi", "确认按钮点击回调");
    }


    /**
     * 自定义更新展示界面 直接重写此方法就好
     */
    @Override
    public void showVersionDialog() {
        customVersionDialogTwo();
    }

    /**
     * 自定义dialog
     */
    private void customVersionDialogTwo() {
//        versionDialog = new BaseDialog(this, R.style.BaseDialog, R.layout.custom_dialog_two_layout);
//        versionDialog.setCanceledOnTouchOutside(false);
//
//        TextView tvTitle = (TextView) versionDialog.findViewById(R.id.tv_title);
//        TextView tvMsg = (TextView) versionDialog.findViewById(R.id.tv_msg);
//        Button btnUpdate = (Button) versionDialog.findViewById(R.id.btn_update);
//
//        versionDialog.show();
//        //设置dismiss listener 用于强制更新,dimiss会回调dialogDismiss方法
//        versionDialog.setOnDismissListener(this);
//        //可以使用之前从service传过来的一些参数比如：title。msg，downloadurl，parambundle
//        tvTitle.setText(getVersionTitle());
//        tvMsg.setText(getVersionUpdateMsg());
//        //可以使用之前service传过来的值
//        Bundle bundle = getVersionParamBundle();
//        btnUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                downloadFile();
//                versionDialog.dismiss();
        CustomVersionDialogActivity.super.dealAPK();

//            }
//        });
//        versionDialog.show();
    }
    /**
     * 自定义下载失败重试对话框
     * 使用父类的failDialog
     */
    @Override
    public void showFailDialog() {
        super.showFailDialog();
//        Toast.makeText(this, "重写此方法使用自定义失败加载框", Toast.LENGTH_SHORT).show();
    }

    View loadingView;

    @Override
    public void dialogDismiss(DialogInterface dialog) {
        Log.e("CustomVersionDialogActi", "dialog dismiss 回调");
//        finish();
        forceCloseApp();
    }

    /**
     * 在dialogDismiss和onDownloadSuccess里面强制更新
     * 分别表示两种情况：
     * 一种用户取消下载  关闭app
     * 一种下载成功安装的时候 应该也关闭app
     */
    private void forceCloseApp() {
        if (isForceUpdate) {
//            MainActivity.mainActivity.finish();
            Uri packageURI = Uri.parse("package:" + "demo.soho.com.baogevideo");
            Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
