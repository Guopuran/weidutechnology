package tech.wd.com.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
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
    private ImageView mImageView_community;
    private ImageView mImageView_information;
    private ImageView mImageView_message;

    private LinearLayout mLinearLayout_community;
    private LinearLayout mLinearLayout_information;
    private LinearLayout mLinearLayout_message;
    private TextView mTextView_community;
    private TextView mTextView_information;
    private TextView mTextView_message;

    private FragmentManager mManager;
    private FragmentTransaction mTransaction;
    private Fragment mCommunityFragment;
    private Fragment mInformationFragment;
    private Fragment mImfragment;


    /*@BindView(R2.id.activity_img_information)
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
         mImfragment = (Fragment) ARouter.getInstance().build("/communication/IMFragment").navigation();
         mInformationFragment = (Fragment) ARouter.getInstance().build("/information/InformationFragment").navigation();
         mCommunityFragment = (Fragment) ARouter.getInstance().build("/community/CommunityFragment").navigation();


        mManager = getSupportFragmentManager();
        mTransaction = mManager.beginTransaction();
        mTransaction.add(R.id.activity_home_frame,mInformationFragment,mInformationFragment.getClass().getName()).commit();

    }

    @Override
    protected void success(Object object) {

    }

    @Override
    protected void failed(String error) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {

         mImageView_community = findViewById(R.id.activity_img_community);
         mImageView_information = findViewById(R.id.activity_img_information);
         mImageView_message = findViewById(R.id.activity_img_message);

         mLinearLayout_community = findViewById(R.id.activity_linear_community);
         mLinearLayout_information = findViewById(R.id.activity_linear_information);
         mLinearLayout_message = findViewById(R.id.activity_linear_message);

         mTextView_community = findViewById(R.id.activity_text_community);
         mTextView_information = findViewById(R.id.activity_text_community);
         mTextView_message = findViewById(R.id.activity_text_community);

         onClickListener();

    }

    private void onClickListener() {

        mLinearLayout_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mImageView_information.setImageResource(R.mipmap.common_tab_informatiion_s);
                mImageView_community.setImageResource(R.mipmap.common_tab_community_n);
                mImageView_message.setImageResource(R.mipmap.common_tab_message_n);

                mTextView_information.setTextColor(getResources().getColor(R.color.homeColorThree));
                mTextView_message.setTextColor(getResources().getColor(R.color.homeColorSix));
                mTextView_community.setTextColor(getResources().getColor(R.color.homeColorSix));

                FragmentManager information = getSupportFragmentManager();

                FragmentTransaction transactionInformation = information.beginTransaction();


                transactionInformation.hide(mImfragment);
                transactionInformation.hide(mCommunityFragment);
                transactionInformation.show(mInformationFragment);
                transactionInformation.commit();


            }
        });


        mLinearLayout_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                mImageView_information.setImageResource(R.mipmap.common_tab_information_n);
                mImageView_community.setImageResource(R.mipmap.common_tab_community_n);
                mImageView_message.setImageResource(R.mipmap.common_tab_message_s);

                mTextView_information.setTextColor(getResources().getColor(R.color.homeColorSix));
                mTextView_message.setTextColor(getResources().getColor(R.color.homeColorThree));
                mTextView_community.setTextColor(getResources().getColor(R.color.homeColorSix));

                FragmentManager message = getSupportFragmentManager();
                FragmentTransaction transactionMessage = message.beginTransaction();

                if (message.findFragmentByTag(mImfragment.getClass().getName()) == null) {
                    transactionMessage.add(R.id.activity_home_frame,mImfragment,mImfragment.getClass().getName()).commit();

                    if (message.findFragmentByTag(mInformationFragment.getClass().getName()) != null) {
                        transactionMessage.hide(mInformationFragment);
                    }
                    if (message.findFragmentByTag(mCommunityFragment.getClass().getName()) != null) {
                        transactionMessage.hide(mCommunityFragment);
                    }
                }else {
                    transactionMessage.hide(mInformationFragment);
                    transactionMessage.hide(mCommunityFragment);
                    transactionMessage.show(mImfragment);
                    transactionMessage.commit();
                }



            }
        });


        mLinearLayout_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mImageView_information.setImageResource(R.mipmap.common_tab_information_n);
                mImageView_message.setImageResource(R.mipmap.common_tab_message_n);
                mImageView_community.setImageResource(R.mipmap.common_tab_community_s);

                mTextView_information.setTextColor(getResources().getColor(R.color.homeColorSix));
                mTextView_message.setTextColor(getResources().getColor(R.color.homeColorSix));
                mTextView_community.setTextColor(getResources().getColor(R.color.homeColorThree));

                FragmentManager community = getSupportFragmentManager();
                FragmentTransaction transactionCommunity = community.beginTransaction();

                if (community.findFragmentByTag(mCommunityFragment.getClass().getName()) == null) {
                    transactionCommunity.add(R.id.activity_home_frame,mCommunityFragment,mCommunityFragment.getClass().getName()).commit();

                    if (community.findFragmentByTag(mInformationFragment.getClass().getName()) != null) {
                        transactionCommunity.hide(mInformationFragment);
                    }
                    if (community.findFragmentByTag(mImfragment.getClass().getName()) != null) {
                        transactionCommunity.hide(mImfragment);
                    }
                }else {
                    transactionCommunity.hide(mInformationFragment);
                    transactionCommunity.hide(mImfragment);
                    transactionCommunity.show(mCommunityFragment);
                    transactionCommunity.commit();
                }


            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_home;
    }


}
