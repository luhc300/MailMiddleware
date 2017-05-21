package com.fudan.ssilv.mailmiddleware;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ssilv on 2017/3/22.
 */

public class ObserverService extends Service {
    private static final String TAG = "ObserverService" ;
    public static final String ACTION = "com.fudan.ssilv.mailmiddleware.ObserverService";
    private static PhotoObserver photoObserver;
    private String path;
    @Override
    public IBinder onBind(Intent intent) {
        Log.v(TAG, "ServiceDemo onBind");
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate() {

        Log.v(TAG, "ServiceDemo onCreate");

        super.onCreate();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,notificationIntent, 0);
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.filedialog_folder);
        builder.setTicker("指定目录监控开始");
        builder.setContentTitle("正在监控目录并同步");
        builder.setContentText(path);
        Notification notification = builder.build();
        startForeground(1, notification);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.v(TAG, "ServiceDemo onStart");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG, "ServiceDemo onStartCommand");
        path = intent.getStringExtra("path");
        photoObserver = new PhotoObserver(path);
        photoObserver.startWatching();
        Toast.makeText(getApplicationContext(), "正在监控目录"+path,Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public void onDestroy() {
        photoObserver.stopWatching();
        super.onDestroy();
        stopForeground(true);
        Log.d(TAG, "onDestroy() executed");
        Toast.makeText(getApplicationContext(), "停止监控",Toast.LENGTH_SHORT).show();
    }
}
