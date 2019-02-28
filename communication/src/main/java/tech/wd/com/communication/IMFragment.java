package tech.wd.com.communication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tech.wd.com.common.base.BaseFragment;
import tech.wd.com.communication.message.MessageFragment;
import tech.wd.com.communication.relation.RelationFragment;

/**
 * @作者 然
 * @描述 即时通讯页面
 * @创建日期 2019/2/28 8:49
 */
@Route(path = "/communication/IMFragment")
public class IMFragment extends BaseFragment {

    @BindView(R2.id.im_radio_message)
    RadioButton im_radio_message;
    @BindView(R2.id.im_radio_relation)
    RadioButton im_radio_relation;
    @BindView(R2.id.im_radio)
    RadioGroup im_radio;
    @BindView(R2.id.im_image_more)
    ImageView im_image_more;
    @BindView(R2.id.im_viewpager)
    ViewPager im_viewPager;

    @Override
    protected void initData() {
        //加载fragment
        initViewPager();
    }

    private void initViewPager() {
        final List<Fragment> list=new ArrayList<>();
        list.add(new MessageFragment());
        list.add(new RelationFragment());
        im_viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
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
        return 0;
    }
}
