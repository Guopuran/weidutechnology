package tech.wd.com.common.model;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 *
 * @作者 然
 *
 * @描述 M层接口
 *
 * @创建日期 2019/2/27 11:05
 *
 */
public interface Imodel {
    //get请求
    void getRequestModel(String url, Class clazz, ModelCallBack callBack);
    //post请求
    void postRequestModel(String url, Map<String, String> params, Class clazz, ModelCallBack callBack);
    //delete请求
    void deleteRequestModel(String url, Class clazz, ModelCallBack callBack);
    //put请求
    void putRequestModel(String url, Map<String, String> params, Class clazz, ModelCallBack callBack);
    //上传图片
    void postImageRequestModel(String url, File file, Class clazz, ModelCallBack callBack);
    //多图上传
    void postMoreImageRequestModel(String url,Map<String,String> params, List<File> list , Class clazz ,ModelCallBack callBack);

}