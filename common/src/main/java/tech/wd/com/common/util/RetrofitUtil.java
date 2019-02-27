package tech.wd.com.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tech.wd.com.common.R;

/**
 *
 * @作者 然
 *
 * @描述 Retrofit网络请求的工具类
 *
 * @创建日期 2019/2/27 11:42
 *
 */
public class RetrofitUtil {
    private static RetrofitUtil instance;
    private ObservedApis mObservedApis;
    private final String BaseUrl="https://mobile.bwstudent.com/techApi/";
    public static synchronized RetrofitUtil getInstance(){
        if (instance==null){
            instance=new RetrofitUtil();
        }
        return instance;
    }
    /*
     * 默认信任所有的证书
     * */
    public static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory sslSocketFactory = null;
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{createTrustAllManager()}, new SecureRandom());
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {

        }
        return sslSocketFactory;
    }

    public static X509TrustManager createTrustAllManager() {
        X509TrustManager tm = null;
        try {

            tm =   new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                    //do nothing，接受任意客户端证书
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType)
                        throws CertificateException {
                    //do nothing，接受任意服务端证书
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };
            InputStream trustStream = ContextUtil.getContext.getResources().openRawResource(R.raw.server);
            testReadX509CerFile(trustStream);
        } catch (Exception e) {

        }
        return tm;
    }
    //主机地址验证
    final HostnameVerifier hostnameVerifier = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return hostname.equals("mobile.bwstudent.com");
        }
    };
    private RetrofitUtil(){
        //拦截器
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient mClient=new OkHttpClient.Builder()
                //读取超时
                .readTimeout(10,TimeUnit.SECONDS)
                //连接超时
                .connectTimeout(10,TimeUnit.SECONDS)
                //写超时
                .writeTimeout(10,TimeUnit.SECONDS)

                .hostnameVerifier(hostnameVerifier)
                //添加拦截器
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original=chain.request();
                        //取出保存的userID，sessionID
                        SharedPreferences mSharedPreferences=ContextUtil.getContext.getSharedPreferences("User",Context.MODE_PRIVATE);
                        String userId = mSharedPreferences.getString("userId","");
                        String sessionId = mSharedPreferences.getString("sessionId", "");
                        Request.Builder builder1 = original.newBuilder();
                        builder1.method(original.method(),original.body());
                        builder1.addHeader("ak","0110010010000");
                        builder1.addHeader("Content-Type","application/x-www-form-urlencoded");
                        if(!TextUtils.isEmpty(userId)&&!TextUtils.isEmpty(sessionId)){
                            builder1.addHeader("userId",userId);
                            builder1.addHeader("sessionId",sessionId);
                        }

                        Request request = builder1.build();

                        return chain.proceed(request);
                    }
                })
                .build();

        //Retrofit的创建
        Retrofit mRetrofit=new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BaseUrl)
                .client(mClient)
                .build();
        mObservedApis=mRetrofit.create(ObservedApis.class);
    }

    //get请求
    public void get(String url,ICallBack callBack){
        mObservedApis.get(url)
                //后执行在哪个线程
                .subscribeOn(Schedulers.io())
                //最终完成后执行在哪个线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(callBack));

    }


    //post请求
    public void post(String url, Map<String,String> params, ICallBack callBack){
        if (params==null){
            params=new HashMap<>();
        }
        mObservedApis.post(url,params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(callBack));
    }

    //delete请求
    public void delete(String url,ICallBack callBack){
        mObservedApis.delete(url)
                //后执行在哪个线程
                .subscribeOn(Schedulers.io())
                //最终完成后执行在哪个线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(callBack));

    }

    //put请求
    public void put(String url, Map<String,String> params,ICallBack callBack){
        if (params==null){
            params=new HashMap<>();
        }
        mObservedApis.put(url,params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(callBack));
    }
    //图片上传
    public void postImage(String url, File file, ICallBack callBack){

        RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),file);
        MultipartBody.Part filePart=MultipartBody.Part.createFormData("image",file.getName(),requestBody);
        mObservedApis.postImage(url,filePart)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(callBack));

    }
    //多图片上传
    public void postMoreImage(String url, Map<String,String> params,List<File> list, ICallBack callBack){
        MultipartBody.Part[] parts=new MultipartBody.Part[list.size()];
        int index=0;
        for (File file: list){
            RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),file);
            MultipartBody.Part filePart=MultipartBody.Part.createFormData("image",file.getName(),requestBody);
            parts[index]=filePart;
            index++;
        }

        mObservedApis.postMoreImage(url,params,parts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver(callBack));

    }
    private Observer getObserver(final ICallBack callBack) {
        //Rxjava
        Observer observer=new Observer<ResponseBody>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                if (callBack!=null){
                    callBack.failureData(e.getMessage());
                    Log.i("Tag",e.getMessage());
                }
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String result = responseBody.string();
                    if (callBack!=null){
                        callBack.successData(result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callBack!=null){
                        callBack.failureData(e.getMessage());
                    }
                }
            }
        };
        return observer;
    }

    public interface ICallBack{
        void successData(String result);
        void failureData(String error);
    }

    /***
     * 读取*.cer公钥证书文件， 获取公钥证书信息
     * @author xgh
     */
    public static  void testReadX509CerFile(InputStream inStream) throws Exception{

        try {
            // 读取证书文件

            // 创建X509工厂类
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            // 创建证书对象
            X509Certificate oCert = (X509Certificate) cf
                    .generateCertificate(inStream);
            inStream.close();
            SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
            String info = null;
            // 获得证书版本
            info = String.valueOf(oCert.getVersion());
            System.out.println("证书版本:" + info);
            // 获得证书序列号
            info = oCert.getSerialNumber().toString(16);
            System.out.println("证书序列号:" + info);
            // 获得证书有效期
            Date beforedate = oCert.getNotBefore();
            info = dateformat.format(beforedate);
            System.out.println("证书生效日期:" + info);
            Date afterdate = oCert.getNotAfter();
            info = dateformat.format(afterdate);
            System.out.println("证书失效日期:" + info);
            // 获得证书主体信息
            info = oCert.getSubjectDN().getName();
            System.out.println("证书拥有者:" + info);
            // 获得证书颁发者信息
            info = oCert.getIssuerDN().getName();
            System.out.println("证书颁发者:" + info);
            // 获得证书签名算法名称
            info = oCert.getSigAlgName();
            System.out.println("证书签名算法:" + info);

        } catch (Exception e) {
            System.out.println("解析证书出错！");
            e.printStackTrace();
        }
    }
}
