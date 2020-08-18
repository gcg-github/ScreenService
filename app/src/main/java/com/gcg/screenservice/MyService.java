package com.gcg.screenservice;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;

public class MyService extends IntentService {

    private static final String TAG = "SCREENSERVICE";
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    public MyService(){
        super(TAG);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        //Intent in  = new Intent(this,MyService.class);
        //pendingIntent = PendingIntent.getForegroundService(this, 0, in, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentTo = new Intent("com.yan.receive.MY_ALARM");
        pendingIntent = PendingIntent.getBroadcast(this, 0, intentTo, 0);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        //开机后一般会停留在锁屏页面且短时间内没有进行解锁操作屏幕会进入休眠状态，此时就需要先唤醒屏幕和解锁屏幕
        //屏幕唤醒
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP|PowerManager.FULL_WAKE_LOCK, "mytest:gcg");//最后的参数是LogCat里用的Tag
        wl.acquire();

        //屏幕解锁
        KeyguardManager km= (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock kl = km.newKeyguardLock("StartupReceiver");//参数是LogCat里用的Tag
        kl.disableKeyguard();

        wl.release();

        Log.v("wocao", "111111111111111");

        int alarmType = AlarmManager.ELAPSED_REALTIME_WAKEUP;
        long triggerAtMillis = SystemClock.elapsedRealtime()+(5*1000);//执行间隔时间5s
        long intervalMillis = 2*1000;
        alarmManager.setInexactRepeating(alarmType, triggerAtMillis, intervalMillis, pendingIntent);
    }
}
