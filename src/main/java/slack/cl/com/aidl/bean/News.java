package slack.cl.com.aidl.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * <p>Description: 新闻类 需要序列化 Parcelable </p>
 * 对参数 in out 的理解：aidl里会需要用到d
 * in 表示数据只能由客户端流向服务端， out 表示数据只能由服务端流向客户端，
 * 而 inout 则表示数据可在服务端与客户端之间双向流通
 * in out 都是相对服务端来说的
 * Created by slack on 2016/9/27 11:34 .
 */
public class News implements Parcelable{

    public String title;// 标题
    public String author;// 作者
    public String context;// 内容
    public String time;// 时间

    public News() {}

    public News(String title, String author, String context, String time) {
        this.title = title;
        this.author = author;
        this.context = context;
        this.time = time;
    }

    protected News(Parcel in) {
        title = in.readString();
        author = in.readString();
        context = in.readString();
        time = in.readString();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(title);
        parcel.writeString(author);
        parcel.writeString(context);
        parcel.writeString(time);
    }

    /**
     * 参数是一个Parcel,用它来存储与传输数据 out
     * @param dest
     */
    public void readFromParcel(Parcel dest) {
        //注意，此处的读值顺序应当是和writeToParcel()方法中一致的
        title = dest.readString();
        author = dest.readString();
        context = dest.readString();
        time = dest.readString();
    }

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", context='" + context + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
