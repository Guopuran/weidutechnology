package tech.wd.com.information.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.wd.com.common.base.BaseFragment;
import tech.wd.com.common.util.Apis;
import tech.wd.com.information.R;
import tech.wd.com.information.R2;
import tech.wd.com.information.adpter.InformationListAdpter;
import tech.wd.com.information.bean.InformationBannerBean;
import tech.wd.com.information.bean.InformationListBean;


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
    @BindView(R2.id.information_xrecycle)
    XRecyclerView information_xrecycle;
    private  int page=1;
    InformationListAdpter informationListAdpter;
    @Override
    protected void initData() {
        //banner图请求数据
        getInformationBannerData();
        //资讯列表展示数据
        initInformationLayout();
    }
    //资讯列表展示数据
    public void initInformationLayout()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        information_xrecycle.setLayoutManager(linearLayoutManager);
        informationListAdpter = new InformationListAdpter(getContext());
        information_xrecycle.setAdapter(informationListAdpter);
        information_xrecycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                getInformationListData();
                information_xrecycle.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                getInformationListData();
                information_xrecycle.loadMoreComplete();
            }

        });
        getInformationListData();
    }
    //资讯列表请求数据
    public void getInformationListData()
    {
        getRequest(String.format(Apis.INFORMATIONLIST_URL,page,10),InformationListBean.class);
    }
      //banner图请求数据
      public void getInformationBannerData(){
        getRequest(Apis.INFORMATIONBANNER_URL,InformationBannerBean.class);
      }

    @Override
    protected void success(Object object) {
        if (object instanceof InformationBannerBean){
             final InformationBannerBean informationBannerBean = (InformationBannerBean) object;
            List<String> titleList = new ArrayList<>();
            //将标题添加新的list集合
            for (int i=0;i<informationBannerBean.getResult().size();i++)
            {
                titleList.add(informationBannerBean.getResult().get(i).getTitle());
            }
            //给banner设置图片和文字
             information_xbanner.setData(R.layout.image_fresco,informationBannerBean.getResult(),titleList);
             information_xbanner.loadImage(new XBanner.XBannerAdapter() {
                 @Override
                 public void loadBanner(XBanner banner, Object model, View view, int position) {
                     InformationBannerBean.ResultBean bean = (InformationBannerBean.ResultBean) model;
                        SimpleDraweeView simpleDraweeView1 = view.findViewById(R.id.my_image_view);
                         DraweeController controller = Fresco.newDraweeControllerBuilder()
                                 .setUri(bean.getImageUrl())
                                 .setAutoPlayAnimations(true)
                                 .build();
                         simpleDraweeView1.setController(controller);
                 }
             });
        }
        if (object instanceof  InformationListBean){
            InformationListBean informationListBean = (InformationListBean) object;
            List<InformationListBean.ResultBean> result = informationListBean.getResult();
            if (page==1){
                informationListAdpter.setmList(result);
            }
            else {
                informationListAdpter.addmList(result);
            }
             if (result.size()==0){
                information_xrecycle.loadMoreComplete();
             }
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
