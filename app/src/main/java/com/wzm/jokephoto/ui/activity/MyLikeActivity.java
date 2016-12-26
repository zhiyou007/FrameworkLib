package com.wzm.jokephoto.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenu;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.image.ImageInfo;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.orhanobut.dialogplus.DialogPlus;
import com.squareup.okhttp.Request;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.wzm.framework.Tools.Logger;
import com.wzm.framework.Tools.Tag;
import com.wzm.framework.Tools.Tools;
import com.wzm.framework.adater.abslistview.ViewHolder;
import com.wzm.framework.adater.abslistview.base.ItemViewDelegate;
import com.wzm.framework.adater.recyleview.CommonAdapter;
import com.wzm.framework.adater.recyleview.CommonMultiTypeAdapter;
import com.wzm.framework.base.BaseActivity;
import com.wzm.framework.uihelper.ImgHelper;
import com.wzm.jokephoto.R;
import com.wzm.jokephoto.ui.Impl.CallBackData;
import com.wzm.jokephoto.ui.bean.JzBean;
import com.wzm.jokephoto.ui.bean.MakerBean;
import com.wzm.jokephoto.ui.widgets.FlowLikeView;
import com.wzm.jokephoto.ui.widgets.JustifyTextView;
import com.wzm.jokephoto.ui.widgets.NoScrollListView;
import com.wzm.tools.DES2;
import com.wzm.tools.GsonTools;
import com.wzm.tools.HttpUtils;
import com.wzm.tools.MD5Util;
import com.wzm.tools.ScreenShot;
import com.wzm.tools.ShareManager;
import com.wzm.tools.UIHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

/**
 * Created by zhiyou007 on 2016/9/20.
 */
public class MyLikeActivity extends BaseActivity{
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.tv_title)
    TextView tv_title;


    @Bind(R.id.refreshview)
    SpringView springView;
    @Bind(R.id.ry_view)
    RecyclerView ry_view;
    private StaggeredGridLayoutManager mStaggeredLayoutManager;

    private ArrayList<JzBean> mDatas = new ArrayList<JzBean>();

    private CommonAdapter<JzBean> mAdapter = null;

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_mylike;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {

        setSwipeEnabled(true);

        tv_title.setText("我喜欢的");
        //去掉标题
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Tools.ToastMsg(mContext,"返回");
                finish();
            }
        });


        ry_view.setLayoutManager(mStaggeredLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        ry_view.setHasFixedSize(true);

        springView.setHeader(new DefaultHeader(mContext));
        springView.setFooter(new DefaultFooter(mContext));

        mStaggeredLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mAdapter = new CommonAdapter<JzBean>(mContext,R.layout.cell_pianyu,mDatas) {
            @Override
            protected void convert(com.wzm.framework.adater.recyleview.ViewHolder holder, final JzBean jzBean, int position) {

                JustifyTextView tv_info = holder.getView(R.id.tv_info);
                tv_info.setText(Html.fromHtml(jzBean.getContent()).toString());

                holder.setText(R.id.tv_like,String.valueOf(jzBean.getDing()));
                holder.setText(R.id.tv_share,String.valueOf(jzBean.getShare()));

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

                        Logger.info("width:"+imgWidth+"--height:"+imgHeight);

                        if (imgWidth != 0 && imgHeight != 0) {

                            int wheight = (int) ((float) (mScreenWidth * imgHeight / imgWidth));
                            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                                    RelativeLayout.LayoutParams.MATCH_PARENT, wheight);
                            iv_img.setLayoutParams(params);

                            Logger.info("whight:"+wheight);
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
        springView.callFresh();
    }

    public void getData(int event_tag)
    {
        if(Tag.EVENT_REFRESH_DATA == event_tag)
        {
            mDatas.clear();
        }
        String urlpath = HttpUtils.MYLIKE+"?ukey="+UIHelper.getDeviceId(mContext)+"&size="+mDatas.size();
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
    public void onBackPressed() {
        finish();
    }
}
