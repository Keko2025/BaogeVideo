package service;

import android.content.Intent;
import android.os.IBinder;

import com.allenliu.versionchecklib.core.AVersionService;

/**
 * @author dell
 * @data 2018/1/29.
 */

public class UpdataService extends AVersionService {
    public UpdataService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onResponses(AVersionService service, String response) {
        service.showVersionDialog("https://apk.kosungames.com/app-diyicaipiao-release.apk\n",
                "检测到新版本", "当前App版本过低,需更新最新版本 \n\n立即更新？");

    }
}
