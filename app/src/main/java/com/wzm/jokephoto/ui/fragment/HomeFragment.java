package com.wzm.jokephoto.ui.fragment;

import android.graphics.drawable.Animatable;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.squareup.okhttp.Request;
import com.ufreedom.floatingview.Floating;
import com.ufreedom.floatingview.FloatingBuilder;
import com.ufreedom.floatingview.FloatingElement;
import com.ufreedom.floatingview.effect.TranslateFloatingTransition;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.wzm.framework.Tools.Logger;
import com.wzm.framework.Tools.Tag;
import com.wzm.framework.Tools.Tools;
import com.wzm.framework.adater.recyleview.CommonAdapter;
import com.wzm.framework.adater.recyleview.ViewHolder;
import com.wzm.framework.base.BaseFragment;
import com.wzm.framework.uihelper.ImgHelper;
import com.wzm.jokephoto.R;
import com.wzm.jokephoto.ui.Impl.CallBackData;
import com.wzm.jokephoto.ui.bean.JzBean;
import com.wzm.jokephoto.ui.widgets.FlowLikeView;
import com.wzm.jokephoto.ui.widgets.JustifyTextView;
import com.wzm.tools.DES2;
import com.wzm.tools.GsonTools;
import com.wzm.tools.HttpUtils;
import com.wzm.tools.MD5Util;
import com.wzm.tools.ShareManager;
import com.wzm.tools.UIHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

import butterknife.Bind;

/**
 * Created by zhiyou007 on 2016/9/2.
 *
 * 如果需要
 */
public class HomeFragment extends BaseFragment{

    @Bind(R.id.ry_view)
    RecyclerView ry_view;

    @Bind(R.id.refreshview)
    SpringView springView;

    private Floating mFloating;

    private CommonAdapter<JzBean> mAdapter;

    private ArrayList<JzBean> mDatas = new ArrayList<JzBean>();

    private int pos;

    private StaggeredGridLayoutManager mStaggeredLayoutManager;


    private int lid = 0;
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
        ry_view.setLayoutManager(mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        ry_view.setHasFixedSize(true);




        mFloating = new Floating(getActivity());

        springView.setHeader(new DefaultHeader(mContext));
        springView.setFooter(new DefaultFooter(mContext));

        //位置发生变化
        mStaggeredLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mAdapter = new CommonAdapter<JzBean>(mContext,R.layout.cell_pianyu,mDatas) {
            @Override
            protected void convert(ViewHolder holder,final JzBean jzBean, int position) {

                JustifyTextView tv_info = holder.getView(R.id.tv_info);
                tv_info.setText(Html.fromHtml(jzBean.getContent()).toString());

                holder.setText(R.id.tv_like,String.valueOf(jzBean.getDing()));
                holder.setText(R.id.tv_share,String.valueOf(jzBean.getShare()));

                final FlowLikeView flowLikeView = holder.getView(R.id.flowLikeView);
                final TextView tv_like = holder.getView(R.id.tv_like);
                LinearLayout lly_like = holder.getView(R.id.lly_like);
                LinearLayout lly_share = holder.getView(R.id.lly_share);


                lly_like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DingJZ(jzBean,view,flowLikeView,tv_like);
                    }
                });

                lly_share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ShareManager.getInstance().initShare(getActivity(), jzBean.getContent(), jzBean.getImgurl(), new UMShareListener() {
                            @Override
                            public void onResult(SHARE_MEDIA share_media) {
                                Tools.ToastMsg(mContext,"分享成功");
                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                                Tools.ToastMsg(mContext,"分享失败"+throwable.getMessage());
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media) {
                                Tools.ToastMsg(mContext,"分享取消");
                            }
                        });
                    }
                });

                final SimpleDraweeView iv_img = holder.getView(R.id.iv_img);

                ControllerListener controllerListener = new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(
                            String id,
                            @Nullable ImageInfo imageInfo,
                            @Nullable Animatable anim) {
                        if (imageInfo == null) {
                            return;
                        }
                        int imgWidth = imageInfo.getWidth();
                        int imgHeight = imageInfo.getHeight();

                        //Logger.info("width:"+imgWidth+"--height:"+imgHeight);

                        if (imgWidth != 0 && imgHeight != 0) {

                            int wheight = (int) ((float) (mScreenWidth * imgHeight / imgWidth));
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                    RelativeLayout.LayoutParams.MATCH_PARENT, wheight);
                            iv_img.setLayoutParams(params);

                            //Logger.info("whight:"+wheight);
                        }
                    }

                    @Override
                    public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {

                    }

                    @Override
                    public void onFailure(String id, Throwable throwable) {

                    }
                };


                try{
                    String imgPath = jzBean.getImgurl();
                    imgPath = DES2.decrypt(imgPath, MD5Util.key);
                    ImgHelper.display(iv_img, imgPath, controllerListener);
                }catch (Exception e)
                {
                    Logger.info("解密失败");
                }

            }
        };


        ry_view.setAdapter(mAdapter);
        ry_view.setItemAnimator(new DefaultItemAnimator());


        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                getData(Tag.EVENT_REFRESH_DATA);
            }
            @Override
            public void onLoadmore() {
                getData(Tag.EVENT_LOAD_MORE_DATA);
            }
        });

    }


    public void DingJZ(final JzBean jb,final View view,final FlowLikeView flowLikeView,final TextView tv_like)
    {
        try{
            JSONObject json = new JSONObject();
            json.put("lid",jb.getId());
            json.put("ukey", UIHelper.getDeviceId(mContext));

            String data = DES2.encrypt(json.toString(),MD5Util.key);
            String urlpath = HttpUtils.DING+"?data="+ URLEncoder.encode(data,"utf-8");
            Logger.info(urlpath);
            HttpUtils.get(mContext, urlpath, Tag.EVENT_BEGIN, new CallBackData() {
                @Override
                public void CommonParseData(String data, boolean isCache, int event_tag) {
                    Logger.info(data);
                    try{
                        JSONObject retJson = new JSONObject(data);
                        int s = retJson.getInt("s");
                        if(s==1)
                        {
                            jb.setDing(jb.getDing()+1);
                            flowLikeView.addLikeView();
                            FloatingElement floatingElement = new FloatingBuilder()
                                    .anchorView(view)
                                    .targetView(R.layout.float_like)
                                    .floatingTransition(new TranslateFloatingTransition())
                                    .build();
                            mFloating.startFloating(floatingElement);

                            tv_like.setText(String.valueOf(jb.getDing()));
                        }else if(s == 2){
                            flowLikeView.addLikeView();
                        }else{
                            Tools.ToastMsg(mContext,"内部错误,请稍后重试");
                        }
                    }catch (JSONException e)
                    {

                    }

                }

                @Override
                public void FailData(int code, int event_tag) {

                }

                @Override
                public void onBefore(Request request) {

                }

                @Override
                public void onAfter() {

                }
            });

        }catch (Exception e)
        {

        }


    }




    public void getData(int event_tag)
    {
        if(Tag.EVENT_REFRESH_DATA == event_tag)
        {
            lid = 0;
        }
        String urlpath = HttpUtils.LIST+"?lid="+lid+"&pos="+pos+"&size="+mDatas.size();
        Logger.info(urlpath);
        HttpUtils.get(mContext, urlpath, event_tag, new CallBackData() {
            @Override
            public void CommonParseData(String data, boolean isCache, int event_tag) {
                try{
                    JSONObject retJson = new JSONObject(data);
                    int s = retJson.getInt("s");
                    if(s==1)
                    {
                        JSONArray jzarray = retJson.getJSONArray("data");
                        ArrayList<JzBean> temp = GsonTools.getInstance().getList(jzarray,JzBean.class);

                        if(temp.size()==0)
                        {
                            Tools.ToastMsg(mContext,"没有更多内容了");
                        }
                        mDatas.addAll(temp);

                        lid = mDatas.get(mDatas.size()-1).getId();
                        mAdapter.notifyDataSetChanged();
                    }
                }catch (JSONException e)
                {

                }finally {
                    springView.onFinishFreshAndLoad();
                }

            }

            @Override
            public void FailData(int code, int event_tag) {
                springView.onFinishFreshAndLoad();
            }

            @Override
            public void onBefore(Request request) {

            }

            @Override
            public void onAfter() {

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
        springView.callFresh();
    }

}
