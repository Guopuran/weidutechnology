package tech.wd.com.common.presenter;

import java.io.File;
import java.util.List;
import java.util.Map;

import tech.wd.com.common.model.ModelCallBack;

/**
 *
 * @作者 然
 *
 * @描述 P层接口
 *
 * @创建日期 2019/2/27 11:04
 *
 */
public interface Ipresenter {

    //get请求
    void getRequestIpresenter(String url, Class clazz);
    //post请求
    void postRequestIpresenter(String url, Map<String, String> params, Class clazz);
    //get请求
    void deleteRequestIpresenter(String url, Class clazz);
    //put请求
    void putRequestIpresenter(String url, Map<String, String> params, Class clazz);
    //上传图片
    void postImageRequestIpresenter(String url, File file, Class clazz);
    //多图上传
    void postMoreImageRequestIpresenter(String url, Map<String,String> params, List<File> list , Class clazz );

}
