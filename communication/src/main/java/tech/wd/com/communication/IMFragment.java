package tech.wd.com.communication;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import tech.wd.com.common.base.BaseFragment;
import tech.wd.com.communication.message.MessageFragment;
import tech.wd.com.communication.relation.activity.AddFriendGroupActivity;
import tech.wd.com.communication.relation.activity.CreateGroupActivity;
import tech.wd.com.communication.relation.fragment.RelationFragment;

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
        //添加
        initPop();
    }


    private void initPop() {
        final View pop_view=LayoutInflater.from(getActivity()).inflate(R.layout.more_pop, null, false);
        ConstraintLayout pop_friend=pop_view.findViewById(R.id.more_pop_friend);
        ConstraintLayout pop_group=pop_view.findViewById(R.id.more_pop_group);
        final PopupWindow popupWindow=new PopupWindow(pop_view,ViewGroup.LayoutParams.WRAP_CONTENT ,ViewGroup.LayoutParams.WRAP_CONTENT , true);
        // 设置PopupWindow的背景
        ColorDrawable dw = new ColorDrawable(getActivity().getResources().getColor(R.color.colorpop));
        // 设置弹出窗体的背景
        popupWindow.setBackgroundDrawable(dw);

        // 设置PopupWindow是否能响应外部点击事件
        popupWindow.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        popupWindow.setTouchable(true);
        im_image_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.showAsDropDown(v,0,45);
            }
        });

        //点击进入添加好友/群
        pop_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),AddFriendGroupActivity.class);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });
        //点击进入创建群聊
        pop_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),CreateGroupActivity.class);
                startActivity(intent);
                popupWindow.dismiss();
            }
        });
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
        im_viewPager.setCurrentItem(1);
        im_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                if (i==0){
                    im_radio.check(R.id.im_radio_message);
                }else if (i==1){
                    im_radio.check(R.id.im_radio_relation);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        im_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId==R.id.im_radio_message){
                    im_viewPager.setCurrentItem(0);
                }else if (checkedId==R.id.im_radio_relation){
                    im_viewPager.setCurrentItem(1);
                }


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
        return R.layout.fragment_im;
    }
}
