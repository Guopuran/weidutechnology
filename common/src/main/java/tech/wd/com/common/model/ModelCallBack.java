package tech.wd.com.common.model;
/**
 *
 * @作者 然
 * 
 * @描述 M层与P层的连接
 *
 * @创建日期 2019/2/27 11:18
 *
 */
public interface ModelCallBack {

    void successData(Object object);

    void failedData(String error);
}
