package tech.wd.com.community;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.HashMap;

import tech.wd.com.common.base.BaseFragment;
import tech.wd.com.community.apis.Apis;
import tech.wd.com.community.bean.CommunityListBean;

@Route(path = "/community/CommunityFragment")
public class CommunityFragment extends BaseFragment {


    @Override
    protected void initData() {

       getRequest(Apis.URL_COMMUNITY_LIST,CommunityListBean.class);

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
        return R.layout.community;
    }
}
