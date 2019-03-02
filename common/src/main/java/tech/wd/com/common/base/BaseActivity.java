package tech.wd.com.common.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

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
/**
 *
 * @作者 然
 *
 * @描述 Activity基类
 *
 * @创建日期 2019/2/28 8:29
 *
 */
public abstract class BaseActivity extends AppCompatActivity implements IView {

    /**
     * 整个Activity视图的根视图
     */
    View decorView;

    /**
     * 手指按下时的坐标
     */
    float downX, downY;

    /**
     * 手机屏幕的宽度和高度
     */
    float screenWidth, screenHeight;

    IpresenterImpl mIpresenterImpl;
    Dialog mCircularLoading;
    private SharedPreferences sharedPreferences;
    public boolean isNeedFCancle = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        ButterKnife.bind(this);
        //实例
        mIpresenterImpl=new IpresenterImpl(this);

        initView(savedInstanceState);

        //拿到整个activity的视图
        decorView = getWindow().getDecorView();

        // 获得手机屏幕的宽度和高度，单位像素
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;

        initData();
    }

    protected abstract void initData();

    protected abstract void success(Object object);

    protected abstract void failed(String error);

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract int getLayoutResId();

    //是否要支持滑动关闭
    public void isSlide(Boolean bool){
        isNeedFCancle = bool;
    }

    @Override
    public void successData(Object object) {
        //关闭load圈
        CircularLoading.closeDialog(mCircularLoading);
        success(object);

    }

    @Override
    public void failedData(String error) {
        //关闭load圈
        CircularLoading.closeDialog(mCircularLoading);
        failed(error);
    }

    //判断是否登录
    public void isLogin(){
        sharedPreferences=getSharedPreferences("User",MODE_PRIVATE);
        boolean islogin = sharedPreferences.getBoolean("islogin", false);
        if (!islogin){
            ToastUtil.showToast(this,getResources().getString(R.string.islogin_name));
            return ;
        }
    }

    //get请求
    protected void getRequest(String url,Class clazz){
        if (!(NetWorkUtil.isConn(this))){
            NetWorkUtil.setNetworkMethod(this);
            return ;
        }
        //显示
        mCircularLoading = CircularLoading.showLoadDialog(this, getResources().getString(R.string.load_state_name), true);

        mIpresenterImpl.getRequestIpresenter(url,clazz);


    }

    //post请求
    protected void postRequest(String url, Map<String,String> params, Class clazz){
        if (!(NetWorkUtil.isConn(this))){
            NetWorkUtil.setNetworkMethod(this);
            return ;
        }
        //显示
        mCircularLoading = CircularLoading.showLoadDialog(this, getResources().getString(R.string.load_state_name), true);

        mIpresenterImpl.postRequestIpresenter(url,params,clazz);
        //显示
    }

    //delete请求
    protected void deleteRequest(String url, Class clazz){
        if (!(NetWorkUtil.isConn(this))){
            NetWorkUtil.setNetworkMethod(this);
            return ;
        }
        //显示
        mCircularLoading = CircularLoading.showLoadDialog(this, getResources().getString(R.string.load_state_name), true);

        mIpresenterImpl.deleteRequestIpresenter(url,clazz);
        //显示
    }

    //put请求
    protected void putRequest(String url, Map<String,String> params, Class clazz){
        if (!(NetWorkUtil.isConn(this))){
            NetWorkUtil.setNetworkMethod(this);
            return ;
        }
        //显示
        mCircularLoading = CircularLoading.showLoadDialog(this, getResources().getString(R.string.load_state_name), true);

        mIpresenterImpl.putRequestIpresenter(url,params,clazz);
        //显示
    }

    //图片请求
    protected void ImageRequest(String url, File file, Class clazz){
        if (!(NetWorkUtil.isConn(this))){
            NetWorkUtil.setNetworkMethod(this);
            return ;
        }
        //显示
        mCircularLoading = CircularLoading.showLoadDialog(this, getResources().getString(R.string.load_state_name), true);

        mIpresenterImpl.postImageRequestIpresenter(url,file,clazz);
        //显示
    }

    //多图请求
    protected void MoreImageRequest(String url, Map<String, String> params, List<File> list, Class clazz){
        if (!(NetWorkUtil.isConn(this))){
            NetWorkUtil.setNetworkMethod(this);
            return ;
        }
        //显示
        mCircularLoading = CircularLoading.showLoadDialog(this, getResources().getString(R.string.load_state_name), true);

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(!isNeedFCancle){
            return super.onTouchEvent(event);
        }else {
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                //手指按下时的坐标
                downX = event.getX();

            }else if(event.getAction() == MotionEvent.ACTION_MOVE){
                float moveDistanceX = event.getX()-downX;
                if(moveDistanceX > 0){
                    decorView.setX(moveDistanceX);
                }
            }else if(event.getAction() == MotionEvent.ACTION_UP){
                float moveDistanceX = event.getX()-downX;
                if(moveDistanceX > 0){
                    if(moveDistanceX > screenWidth / 2){
                        ContinueMove(moveDistanceX);
                    }else {
                        reToBackLeft(moveDistanceX);
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public void ContinueMove(float moveDistanceX){
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(moveDistanceX, screenWidth);
        valueAnimator.setDuration(50);
        valueAnimator.start();

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float x = (float) animation.getAnimatedValue();
                decorView.setX(x);
            }
        });

        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                finish();
            }
        });

    }

    private void reToBackLeft(float moveDistanceX){
        ObjectAnimator.ofFloat(decorView,"X",moveDistanceX,0)
                .setDuration(50)
                .start();
    }

}
