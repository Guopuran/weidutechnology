package tech.wd.com.common.presenter;

import java.io.File;
import java.util.List;
import java.util.Map;

import tech.wd.com.common.model.ImodelImpl;
import tech.wd.com.common.model.ModelCallBack;
import tech.wd.com.common.view.IView;

/**
 *
 * @作者 然
 *
 * @描述 P层实现类
 *
 * @创建日期 2019/2/27 11:33
 *
 */
public class IpresenterImpl implements Ipresenter {
    private ImodelImpl mImodelImpl;
    private IView mIView;

    public IpresenterImpl(IView mIView) {
        this.mIView = mIView;
        //实例化
        mImodelImpl=new ImodelImpl();
    }

    //解绑
    public void  onDeatch(){
        //解绑M层
        if (mImodelImpl!=null){
            mImodelImpl=null;
        }

        //解绑V层
        if (mIView!=null){
            mIView=null;
        }
    }

    //get请求
    @Override
    public void getRequestIpresenter(String url, Class clazz) {
        mImodelImpl.getRequestModel(url , clazz , new ModelCallBack() {

            @Override
            public void successData(Object object) {
                mIView.successData(object);
            }

            @Override
            public void failedData(String error) {
                mIView.failedData(error);
            }
        });
    }

    //post请求
    @Override
    public void postRequestIpresenter(String url, Map<String, String> params, Class clazz) {
        mImodelImpl.postRequestModel(url , params , clazz, new ModelCallBack() {

            @Override
            public void successData(Object object) {
                mIView.successData(object);
            }

            @Override
            public void failedData(String error) {
                mIView.failedData(error);
            }
        });
    }
    //delete请求
    @Override
    public void deleteRequestIpresenter(String url, Class clazz) {
        mImodelImpl.deleteRequestModel(url , clazz , new ModelCallBack() {

            @Override
            public void successData(Object object) {
                mIView.successData(object);
            }

            @Override
            public void failedData(String error) {
                mIView.failedData(error);
            }

        });
    }
    //post请求
    @Override
    public void putRequestIpresenter(String url, Map<String, String> params, Class clazz) {
        mImodelImpl.putRequestModel(url , params , clazz, new ModelCallBack() {

            @Override
            public void successData(Object object) {
                mIView.successData(object);
            }

            @Override
            public void failedData(String error) {
                mIView.failedData(error);
            }
        });
    }
    //图片上传
    @Override
    public void postImageRequestIpresenter(String url, File file, Class clazz) {
        mImodelImpl.postImageRequestModel(url, file, clazz, new ModelCallBack() {
            @Override
            public void successData(Object object) {
                mIView.successData(object);
            }

            @Override
            public void failedData(String error) {
                mIView.failedData(error);
            }
        });
    }
    //多图上传
    @Override
    public void postMoreImageRequestIpresenter(String url, Map<String, String> params, List<File> list, Class clazz) {
        mImodelImpl.postMoreImageRequestModel(url, params,list, clazz, new ModelCallBack() {
            @Override
            public void successData(Object object) {
                mIView.successData(object);
            }

            @Override
            public void failedData(String error) {
                mIView.failedData(error);
            }
        });
    }
}
