package tech.wd.com.common.view;
/**
 *
 * @作者 然
 *
 * @描述 V层接口
 *
 * @创建日期 2019/2/27 11:02
 *
 */
public interface IView {
    //成功
    void successData(Object object);
    //失败
    void failedData(String error);
}
