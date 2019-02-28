package tech.wd.com.information.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.stx.xhb.xbanner.XBanner;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.wd.com.common.base.BaseFragment;
import tech.wd.com.information.R;
import tech.wd.com.information.R2;
import tech.wd.com.information.bean.InformationBannerBean;
import tech.wd.com.information.util.Apis;

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
    @BindView(R2.id.information_xbanner)
    XBanner information_xbanner;

    @Override
    protected void initData() {
        //banner图请求数据
        getInformationBannerData();
    }
      //banner图请求数据
      public void getInformationBannerData(){
          getRequest(Apis.INFORMATIONBANNER_URL,InformationBannerBean.class);
      }
    @Override
    protected void success(Object object) {
        if (object instanceof InformationBannerBean){
             final InformationBannerBean informationBannerBean = (InformationBannerBean) object;
             information_xbanner.setData(informationBannerBean.getResult(),null);
             information_xbanner.loadImage(new XBanner.XBannerAdapter() {
                 @Override
                 public void loadBanner(XBanner banner, Object model, View view, int position) {
                     InformationBannerBean.ResultBean bean = (InformationBannerBean.ResultBean) model;
                      SimpleDraweeView simpleDraweeView = new SimpleDraweeView(getContext());
                   /*  View view1 = View.inflate(getContext(),R.layout.information_banner_item,null);
                     SimpleDraweeView information_banner_image = view1.findViewById(R.id.information_banner_item_image);
                     TextView information_banner_text = view1.findViewById(R.id.information_banner_item_text);*/
                     for (int i=0;i<informationBannerBean.getResult().size();i++)
                     {
                         simpleDraweeView.setImageURI(informationBannerBean.getResult().get(i).getImageUrl());
                     }
                 }
             });
        }
    }

    @Override
    protected void failed(String error) {

    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_information;
    }
}
