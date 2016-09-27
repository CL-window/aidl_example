// IDownLoadAidlInterface.aidl  aidl 2
package slack.cl.com.aidl;

//导入所需要使用的非默认支持数据类型的包
import slack.cl.com.aidl.bean.News;


interface IDownLoadAidlInterface {
//所有的返回值前都不需要加任何东西，不管是什么数据类型

// activity --> service
    void downLoadUpdate();

    News addNews(in News news);// in 相对服务端来说是数据流入，也可是使用inout

// service --> activity
    int onDownLoadProgress();

    List<News> getNews();
}
