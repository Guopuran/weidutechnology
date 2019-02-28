package tech.wd.com.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.wd.com.common.base.BaseActivity;
import tech.wd.com.common.util.ToastUtil;

/**
 * Author: 范瑞旗
 * Date: 2019/2/27 20:01
 * Description: Home页 底部导航Fragment切换
 */
public class HomeActivity extends BaseActivity {


    /*@BindView(R.id.activity_img_information)
    ImageView activityImgInformation;
    @BindView(R.id.activity_text_information)
    TextView activityTextInformation;
    @BindView(R.id.activity_linear_information)
    LinearLayout activityLinearInformation;
    @BindView(R.id.activity_img_message)
    ImageView activityImgMessage;
    @BindView(R.id.activity_text_message)
    TextView activityTextMessage;
    @BindView(R.id.activity_linear_message)
    LinearLayout activityLinearMessage;
    @BindView(R.id.activity_img_community)
    ImageView activityImgCommunity;
    @BindView(R.id.activity_text_community)
    TextView activityTextCommunity;
    @BindView(R.id.activity_linear_community)
    LinearLayout activityLinearCommunity;*/

    @Override
    protected void initData() {
        initFragment();
    }

    private void initFragment() {
        Fragment IMfragment = (Fragment) ARouter.getInstance().build("/communication/IMFragment").navigation();
        if (IMfragment != null) {
            Toast.makeText(this, "show", Toast.LENGTH_SHORT).show();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.frame, IMfragment);
            transaction.commit();
        } else {
            Toast.makeText(this, "hide", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void success(Object object) {

    }

    @Override
    protected void failed(String error) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {


    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
