package wk.shop.base;

import android.app.Application;
import android.content.Context;
import android.os.Vibrator;

import com.amap.api.location.AMapLocationClient;
import com.lzy.okgo.OkGo;

import wk.shop.model.NormalMessageModel;

public class BaseApplication extends Application {
    private static Context context;
    public Vibrator mVibrator;
    public NormalMessageModel user;
    //声明AMapLocationClient类对象
    public static AMapLocationClient mLocationClient = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        OkGo.init(this);
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
//        CrashReport.initCrashReport(getApplicationContext(), "74855b3e1f", false);
    }

    public static Context getContext() {
        return context;
    }

    //系统处于资源匮乏的状态
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}