package demo.soho.com.baogevideo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * @author dell
 * @data 2018/1/30.
 */

public class MyInstalledReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent){
//        //接收安装广播
//        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
//            String packageName = intent.getDataString();
//            if(packageName.equals("com.cp.diyicaipiao")){
//                Toast.makeText(context, "第一彩票安装完成", Toast.LENGTH_SHORT).show();
//            }
//            System.out.println("安装了:" +packageName + "包名的程序");
//            EventBus.getDefault().post(5);
//        }
//        //接收卸载广播
//        if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
//            String packageName = intent.getDataString();
//            System.out.println("卸载了:"  + packageName + "包名的程序");
//        }
    }
}