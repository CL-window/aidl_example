package slack.cl.com.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import slack.cl.com.aidl.bean.News;
import slack.cl.com.aidl.service.DownLoadService;

public class MainActivity extends AppCompatActivity {

    private final String TAG =  "slack"; // getClass().getSimpleName();
    private Intent serviceIntent;
    private IDownLoadAidlInterface mAIDLmanager;
    private boolean mIsBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mIsBind){
            unbindService(mServiceConnection);
            mIsBind = false;
        }
    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(TAG, "service connected...");
            mAIDLmanager = IDownLoadAidlInterface.Stub.asInterface(iBinder);
            mIsBind = true;
            if(mAIDLmanager == null){
                return;
            }
            getNews(null);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(TAG, "service disConnected...");
            mIsBind = false;
        }
    };

    // 显式启动service
    public void startServices(View view) {
        if(serviceIntent == null){
            serviceIntent = new Intent(this,DownLoadService.class);
        }
        startService(serviceIntent);
    }

    // 通讯，肯定是建立在bind基础上，就跟打电话一样，先接通
    public void bindServices(View view) {
        if(!mIsBind) {
            if(serviceIntent == null){
                serviceIntent = new Intent(this,DownLoadService.class);
            }
            bindService(serviceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
        }
    }

    public void addNews(View view) {
        if(mAIDLmanager == null){
            return;
        }
        try {
            mAIDLmanager.addNews(new News("11","11","11","11"));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void downLoad(View view) {
        if(mAIDLmanager == null){
            return;
        }
        try {
            mAIDLmanager.downLoadUpdate();
            // 获取下载进度
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int progress;
                    try {
                        while ((progress = mAIDLmanager.onDownLoadProgress()) < 100){
                            Log.i(TAG,"progress:" + progress );
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void getNews(View view) {
        if(mAIDLmanager == null){
            return;
        }
        try {
            Log.i(TAG ,"news:"+ mAIDLmanager.getNews().toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
