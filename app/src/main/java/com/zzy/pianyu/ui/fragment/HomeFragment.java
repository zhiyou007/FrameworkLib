package com.zzy.pianyu.ui.fragment;

import android.graphics.Bitmap;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.zzy.framework.Tools.Logger;
import com.zzy.framework.Tools.OkHttpClientManager;
import com.zzy.framework.Tools.Tag;
import com.zzy.framework.adater.recyleview.CommonAdapter;
import com.zzy.framework.adater.recyleview.ViewHolder;
import com.zzy.framework.base.BaseFragment;
import com.zzy.framework.base.BaseHttpImpl;
import com.zzy.pianyu.R;
import com.zzy.pianyu.ui.Impl.BasePresenterImpl;
import com.zzy.pianyu.ui.Impl.HomeTypeImpl;
import com.zzy.pianyu.ui.bean.JzBean;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by zhiyou007 on 2016/9/2.
 *
 * 如果需要
 */
public class HomeFragment extends BaseFragment implements BaseHttpImpl {

    @Bind(R.id.ry_view)
    RecyclerView ry_view;

    @Bind(R.id.refreshview)
    SpringView springView;

    private CommonAdapter<JzBean> mAdapter;

    private ArrayList<JzBean> mDatas = new ArrayList<JzBean>();

    private int pos;

    private StaggeredGridLayoutManager mStaggeredLayoutManager;

    private HomeTypeImpl mImpl;
    public static final HomeFragment newInstance(int pos)
    {
        HomeFragment fragment = new HomeFragment();
        fragment.pos = pos;
        return fragment ;
    }


    @Override
    protected View getLoadingTargetView() {
        return ry_view;
    }

    @Override
    protected void initViewsAndEvents() {
        ry_view.setLayoutManager(mStaggeredLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        ry_view.setHasFixedSize(true);

        springView.setHeader(new DefaultHeader(mContext));
        springView.setFooter(new DefaultFooter(mContext));


        mStaggeredLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mAdapter = new CommonAdapter<JzBean>(mContext,R.layout.cell_pianyu,mDatas) {
            @Override
            protected void convert(ViewHolder holder, JzBean jzBean, int position) {

                TextView tv_info = holder.getView(R.id.tv_info);
                tv_info.setText(Html.fromHtml(jzBean.getContent()));

                final ImageView iv_img = holder.getView(R.id.iv_img);

                Glide.with(mContext)
                        .load(jzBean.getImgurl())
                        .asBitmap()
                        .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                            @Override
                            public void onResourceReady(Bitmap bitmap, GlideAnimation<? super Bitmap> glideAnimation) {
                                int width = bitmap.getWidth();
                                int height = bitmap.getHeight();

                                LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) iv_img.getLayoutParams();
                                linearParams.width = mScreenWidth/2;
                                linearParams.height = height*mScreenWidth/2/width;
                                iv_img.setLayoutParams(linearParams);
                                iv_img.setImageBitmap(bitmap);
                            }
                        });
            }
        };
        ry_view.setAdapter(mAdapter);
        ry_view.setItemAnimator(new DefaultItemAnimator());


        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                mImpl.setId(0);
                mImpl.CommonGetData(Tag.EVENT_REFRESH_DATA);
            }
            @Override
            public void onLoadmore() {
                mImpl.setId(mDatas.get(mDatas.size()-1).getId());
                mImpl.CommonGetData(Tag.EVENT_LOAD_MORE_DATA);
            }
        });

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home;
    }

    //fragemnt第一次显示，才懒加载网络数据
    @Override
    protected void onFirstUserVisible() {
        mImpl = new HomeTypeImpl(mContext,this,true);//true需要载入中的样式，就要定义上面载入中的TargetView
        //刷新
        //mImpl.CommonGetData(Tag.EVENT_REFRESH_DATA);

        springView.callFresh();
    }

    @Override
    public void CommonDataComing(int event_tag, Object data) {
        //网络
        Logger.info(data.toString());
        if (event_tag == Tag.EVENT_REFRESH_DATA)
        {
            mDatas.clear();
        }

        ArrayList<JzBean> temp = (ArrayList<JzBean>)data;
        if(null != temp && temp.size()>0)
        {
            mDatas.addAll(temp);
        }

        Logger.info(mDatas.size()+"----------------------");
        mAdapter.notifyDataSetChanged();

        springView.onFinishFreshAndLoad();

    }


}
