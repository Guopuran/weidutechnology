package tech.wd.com.common.base;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import tech.wd.com.common.R;
import tech.wd.com.common.presenter.IpresenterImpl;
import tech.wd.com.common.util.CircularLoading;
import tech.wd.com.common.util.NetWorkUtil;
import tech.wd.com.common.util.ToastUtil;
import tech.wd.com.common.view.IView;

import static android.content.Context.MODE_PRIVATE;
/**
 *
 * @作者 然
 *
 * @描述 Fragment基类
 *
 * @创建日期 2019/2/28 8:29
 *
 */
public abstract class BaseFragment extends Fragment implements IView {
    IpresenterImpl mIpresenterImpl;
    Dialog mCircularLoading;
    private SharedPreferences sharedPreferences;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResId(),container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        mIpresenterImpl=new IpresenterImpl(this);
        initView(view);
        initData();
    }

    protected abstract void initData();

    protected abstract void success(Object object);

    protected abstract void failed(String error);

    protected abstract void initView(View view);

    protected abstract int getLayoutResId();

    @Override
    public void successData(Object object) {
        //关闭
        CircularLoading.closeDialog(mCircularLoading);
        success(object);
    }

    @Override
    public void failedData(String error) {
        //关闭
        CircularLoading.closeDialog(mCircularLoading);
        failed(error);
    }

    //判断是否登录
    public void isLogin(){
        sharedPreferences=getActivity().getSharedPreferences("User",MODE_PRIVATE);
        boolean islogin = sharedPreferences.getBoolean("islogin", false);
        if (!islogin){
            ToastUtil.showToast(getActivity(),getResources().getString(R.string.islogin_name));
            return ;
        }
    }

    //get请求
    protected void getRequest(String url,Class clazz){
        if (!(NetWorkUtil.isConn(getActivity()))){
            NetWorkUtil.setNetworkMethod(getActivity());
            return ;
        }
        //显示
        mCircularLoading = CircularLoading.showLoadDialog(getActivity(), getActivity().getResources().getString(R.string.load_state_name), true);

        mIpresenterImpl.getRequestIpresenter(url,clazz);
    }

    //post请求
    protected void postRequest(String url, Map<String,String> params, Class clazz){
        if (!(NetWorkUtil.isConn(getActivity()))){
            NetWorkUtil.setNetworkMethod(getActivity());
            return ;
        }
        //显示
        mCircularLoading = CircularLoading.showLoadDialog(getActivity(), getActivity().getResources().getString(R.string.load_state_name), true);

        mIpresenterImpl.postRequestIpresenter(url,params,clazz);
        //显示
    }

    //delete请求
    protected void deleteRequest(String url, Class clazz){
        if (!(NetWorkUtil.isConn(getActivity()))){
            NetWorkUtil.setNetworkMethod(getActivity());
            return ;
        }
        //显示
        mCircularLoading = CircularLoading.showLoadDialog(getActivity(), getActivity().getResources().getString(R.string.load_state_name), true);

        mIpresenterImpl.deleteRequestIpresenter(url,clazz);
        //显示
    }

    //put请求
    protected void putRequest(String url, Map<String,String> params, Class clazz){
        if (!(NetWorkUtil.isConn(getActivity()))){
            NetWorkUtil.setNetworkMethod(getActivity());
            return ;
        }
        //显示
        mCircularLoading = CircularLoading.showLoadDialog(getActivity(), getActivity().getResources().getString(R.string.load_state_name), true);

        mIpresenterImpl.putRequestIpresenter(url,params,clazz);
        //显示
    }

    //图片请求
    protected void ImageRequest(String url, File file, Class clazz){
        if (!(NetWorkUtil.isConn(getActivity()))){
            NetWorkUtil.setNetworkMethod(getActivity());
            return ;
        }
        //显示
        mCircularLoading = CircularLoading.showLoadDialog(getActivity(), getActivity().getResources().getString(R.string.load_state_name), true);

        mIpresenterImpl.postImageRequestIpresenter(url,file,clazz);
        //显示
    }

    //多图请求
    protected void MoreImageRequest(String url, Map<String, String> params, List<File> list, Class clazz){
        if (!(NetWorkUtil.isConn(getActivity()))){
            NetWorkUtil.setNetworkMethod(getActivity());
            return ;
        }
        //显示
        mCircularLoading = CircularLoading.showLoadDialog(getActivity(), getActivity().getResources().getString(R.string.load_state_name), true);

        mIpresenterImpl.postMoreImageRequestIpresenter(url,params,list,clazz);
        //显示
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mIpresenterImpl != null){
            mIpresenterImpl.onDeatch();
        }
    }
}
