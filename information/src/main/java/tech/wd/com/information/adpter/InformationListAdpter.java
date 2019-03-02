package tech.wd.com.information.adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import tech.wd.com.information.R;
import tech.wd.com.information.R2;
import tech.wd.com.information.bean.InformationListBean;
/**
 *
 *@作者 刘
 *
 *@描述 资讯展示的适配器
 *
 *@时间 2019/3/1 19:37
 *
 */
public class InformationListAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<InformationListBean.ResultBean> mList;
    private Context mContext;
    private final  int  INFOR=2;
    private final  int ADVER=1;
    public InformationListAdpter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }
    public void setmList(List<InformationListBean.ResultBean> list) {
        mList.clear();
        if (list!=null){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }
    public void addmList(List<InformationListBean.ResultBean> list) {
        if (list!=null){
            mList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mList.get(position).getWhetherAdvertising()==INFOR){
            return INFOR;
        }
        if (mList.get(position).getWhetherAdvertising()==ADVER){
            return ADVER;
        }
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i==ADVER){
            View view = View.inflate(mContext, R.layout.information_list_advertisement_item,null);
            return new AdverViewHolder(view);
        }
        if (i==INFOR){
            View view = View.inflate(mContext, R.layout.information_list_item,null);
            return new InformationViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int itemCount = getItemCount();
        if (itemCount==INFOR){
            InformationViewHolder informationViewHolder = (InformationViewHolder) viewHolder;
            informationViewHolder.infor_image.setImageURI(mList.get(i).getSummary());
            informationViewHolder.infor_name.setText(mList.get(i).getSource());
            /*informationViewHolder.infor_collection_count.setText(mList.get(i).getCollection());*/
            informationViewHolder.infor_time.setText(mList.get(i).getReleaseTime()+"");
          /*  informationViewHolder.infor_share_conunt.setText();*/

        }
        if (itemCount==ADVER){
            AdverViewHolder adverViewHolder = (AdverViewHolder) viewHolder;
            InformationListBean.ResultBean.InfoAdvertisingVoBean infoAdvertisingVo = mList.get(i).getInfoAdvertisingVo();
            adverViewHolder.adv_context.setText(infoAdvertisingVo.getContent());
            adverViewHolder.adv_image.setImageURI(infoAdvertisingVo.getPic());
        }
    }
    @Override
    public int getItemCount() {
        return mList.size();
    }
    //资讯信息加载布局
    public  class InformationViewHolder  extends RecyclerView.ViewHolder{
        @BindView(R2.id.information_list_image)
        SimpleDraweeView infor_image;
        @BindView(R2.id.information_list_context)
        TextView infor_context;
        @BindView(R2.id.information_list_name)
        TextView infor_name;
        @BindView(R2.id.information_list_collection)
        ImageView infor_collection;
        @BindView(R2.id.information_list_collection_count)
        TextView infor_collection_count;
        @BindView(R2.id.information_list_share)
        ImageView infor_share;
        @BindView(R2.id.information_list_time)
        TextView infor_time;
        @BindView(R2.id.information_list_title)
        TextView infor_title;
        @BindView(R2.id.infOrmation_list_share_count)
        TextView infor_share_conunt;
        public InformationViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    //广告加载布局
    public  class AdverViewHolder  extends RecyclerView.ViewHolder{
        @BindView(R2.id.information_list_adv_image)
        SimpleDraweeView adv_image;
        @BindView(R2.id.information_list_adv_centext)
        TextView adv_context;
        public AdverViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
