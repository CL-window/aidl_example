// News.aidl  aidl 1
package slack.cl.com.aidl.bean;
//这个文件的作用是引入了一个序列化对象 News 供其他的AIDL文件使用
// 否则易报 couldn't find import for class ...  意思就是找不到这个类。
//注意：News.aidl与News.java的 package 应当是一样的

//注意parcelable是小写
parcelable News;