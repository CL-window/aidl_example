package slack.cl.com.aidl.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import slack.cl.com.aidl.IDownLoadAidlInterface;
import slack.cl.com.aidl.bean.News;

public class DownLoadService extends Service {

    public final String TAG = "slack"; // this.getClass().getSimpleName();

    private List<News> newsList = new ArrayList<>();

    // bindService 时调用，返回任何你想返回给activity的IBinder类型数据
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"onBind...");
        return stub;
        // TODO: Return the communication channel to the service.
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG,"onCreate...");
        initData();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"onStartCommand...");
        // 此处可以接收 data
        return START_NOT_STICKY;
        // 下面这个会 在service被kill 后自动重启
//        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.i(TAG,"onDestroy...");
        super.onDestroy();
    }

    // test
    private void initData(){
        for (int i = 0;i < 5; i++){
            newsList.add(new News("title"+i,"author"+i,"context"+i,"time"+i));
        }
    }

    int progress = 0;
    // 实现aidl Stub接口  build-->generated-->sources-->aidl-->debug
    IDownLoadAidlInterface.Stub stub = new IDownLoadAidlInterface.Stub() {
        @Override
        public void downLoadUpdate() throws RemoteException {
            Log.i(TAG,"downLoadUpdate...");
            progress = 0;
        }

        @Override
        public News addNews(News news) throws RemoteException {
            Log.i(TAG,"addNews...");
            newsList.add(0,news);
            return news;
        }

        @Override
        public int onDownLoadProgress() throws RemoteException {
//            Log.i(TAG,"onDownLoadProgress...");
            return progress += 5;
        }

        @Override
        public List<News> getNews() throws RemoteException {
            Log.i(TAG,"getNews...");
            return newsList;
        }
    };

}
