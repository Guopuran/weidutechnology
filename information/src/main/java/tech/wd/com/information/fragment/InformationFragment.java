package tech.wd.com.information.fragment;

import android.support.v4.app.Fragment;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;

import tech.wd.com.common.base.BaseFragment;
import tech.wd.com.information.R;

/**
 *
 *@作者 刘
 *
 *@描述 资讯的fragment
 *
 *@时间 2019/2/27 16:37
 *
 */
@Route(path = "/information/InformationFragment")
public class InformationFragment extends BaseFragment {

    @Override
    protected void initData() {

    }

    @Override
    protected void success(Object object) {

    }

    @Override
    protected void failed(String error) {

    }

    @Override
    protected void initView(View view) {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_information;
    }
}
