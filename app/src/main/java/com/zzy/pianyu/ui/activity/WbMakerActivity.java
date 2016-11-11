package com.zzy.pianyu.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.internal.NavigationMenu;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zzy.framework.Tools.Tools;
import com.zzy.framework.adater.abslistview.CommonMultiTypeAdapter;
import com.zzy.framework.adater.abslistview.ViewHolder;
import com.zzy.framework.adater.abslistview.base.ItemViewDelegate;
import com.zzy.framework.base.BaseActivity;
import com.zzy.pianyu.R;
import com.zzy.pianyu.ui.bean.MakerBean;
import com.zzy.pianyu.ui.widgets.NoScrollListView;
import com.zzy.tools.ScreenShot;
import com.zzy.tools.UIHelper;

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
public class WbMakerActivity extends BaseActivity{
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.lv_list)
    NoScrollListView lv_list;

    @Bind(R.id.scrollView)
    ScrollView sc;


    @Bind(R.id.fab_speed_dial)
    FabSpeedDial fabSpeedDial;

    @Bind(R.id.rl_view)
    RelativeLayout rl_view;

    private ArrayList<MakerBean> mDatas = new ArrayList<MakerBean>();

    private CommonMultiTypeAdapter<MakerBean> mAdapter = null;

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_wbmaker;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        tv_title.setText("长微博制作器");
        //去掉标题
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Tools.ToastMsg(mContext,"返回");
                if(mDatas.size()>0)
                {

                }
            }
        });

//        // 创建一个线性布局管理器
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
//        // 默认是Vertical，可以不写
//        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        ry_view.setLayoutManager(mLayoutManager);

        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onPrepareMenu(NavigationMenu navigationMenu) {
                // TODO: Do something with yout menu items, or return false if you don't want to show them
                return true;
            }
        });

        fabSpeedDial.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                //TODO: Start some activity

                switch (menuItem.getItemId())
                {
                    case R.id.action_img:

                        {
                            MakerBean mb = new MakerBean();
                            mb.type = 2;
                            mDatas.add(mb);
                            mAdapter.notifyDataSetChanged();
                            ScrollDown(sc);
                        }
                        break;
                    case R.id.action_text:
                        {
                            MakerBean mb = new MakerBean();
                            mb.type = 1;
                            mDatas.add(mb);
                            mAdapter.notifyDataSetChanged();
                            ScrollDown(sc);
                        }
                        break;
                    case R.id.action_txt_img:

                        {
                            MakerBean mb = new MakerBean();
                            mb.type = 3;
                            mDatas.add(mb);
                            mAdapter.notifyDataSetChanged();
                            ScrollDown(sc);
                        }
                        break;
                    case R.id.action_crop:
                        //生成长图
                        {
                            Bitmap bt = ScreenShot.getbBitmap(lv_list);
                            if(bt!=null)
                            {
                                Tools.ToastMsg(mContext,"截屏成功");
                            }else{
                                Tools.ToastMsg(mContext,"截屏失败");
                            }
                        }
                        break;
                }
                return false;
            }
        });


        mAdapter = new CommonMultiTypeAdapter<MakerBean>(mContext,mDatas) {
            @Override
            public void addMuTypeDelegate() {

                //图片
                addItemViewDelegate(new ItemViewDelegate<MakerBean>(){
                    @Override
                    public int getItemViewLayoutId() {
                        return R.layout.cell_image;
                    }

                    @Override
                    public boolean isForViewType(MakerBean item, int position) {
                        if(item.type == 2)
                        {
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public void convert(ViewHolder holder, MakerBean makerBean, final int position) {
                        //图片长按编辑事件
                        SimpleDraweeView iv_img = holder.getView(R.id.iv_img);
                        iv_img.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                //长按弹出操作选项
                                showSetPhotoDialog(position);
                                return false;
                            }
                        });

                        showImage(iv_img,makerBean);
                    }
                });

                //文字
                addItemViewDelegate(new ItemViewDelegate<MakerBean>(){
                    @Override
                    public int getItemViewLayoutId() {
                        return R.layout.cell_txt;
                    }

                    @Override
                    public boolean isForViewType(MakerBean item, int position) {
                        if(item.type == 1)
                        {
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public void convert(ViewHolder holder, final MakerBean makerBean, final int position) {
                        //Tools.ToastMsg(mContext,"1");

                        TextView tv_info = holder.getView(R.id.tv_info);
                        tv_info.setText(makerBean.message);
                        tv_info.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                editDialog(view,makerBean,position);
                            }
                        });


                    }
                });

                //图片文字
                addItemViewDelegate(new ItemViewDelegate<MakerBean>(){
                    @Override
                    public int getItemViewLayoutId() {
                        return R.layout.cell_image_txt;
                    }

                    @Override
                    public boolean isForViewType(MakerBean item, int position) {
                        if(item.type == 3)
                        {
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public void convert(ViewHolder holder, final MakerBean makerBean,final int position) {
                        //Tools.ToastMsg(mContext,"3");

                        SimpleDraweeView iv_img = holder.getView(R.id.iv_img);
                        iv_img.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                //长按弹出操作选项
                                showSetPhotoDialog(position);
                                return false;
                            }
                        });

                        showImage(iv_img,makerBean);


                        TextView tv_info = holder.getView(R.id.tv_info);
                        tv_info.setText(makerBean.message);
                        tv_info.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                editDialog(view,makerBean,position);
                            }
                        });
                    }
                });

            }
        };

        lv_list.setAdapter(mAdapter);

    }

    public void ScrollDown(final ScrollView sc)
    {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                sc.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }


    public void showImage(SimpleDraweeView img,MakerBean mb)
    {
        if(mb!=null)
        {
            if(!TextUtils.isEmpty(mb.imgpath))
            {
                if(mb.imgpath.startsWith("http"))
                {//网络图片
                    UIHelper.display(mContext,img,mb.imgpath,R.mipmap.default_image,false,false);
                }else{
                    //本地图片
                    UIHelper.display(mContext, img, "file://" + mb.imgpath,R.mipmap.default_image,false,false);
                }
            }
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


//    public void setUpActionView(int index)
//    {
////        final ArrayList<MenuEntity> list = new ArrayList<>();
////        //添加假数据
////        MenuEntity menuEntity1 = new MenuEntity();
////        menuEntity1.iconId = R.drawable.heart0;
////        menuEntity1.title = "本地图片";
////        menuEntity1.titleColor = Color.parseColor("#000000");
////        MenuEntity menuEntity = new MenuEntity();
////        menuEntity.iconId = R.drawable.heart1;
////        menuEntity.title = "网络图片";
////        menuEntity.titleColor = Color.parseColor("#000000");
////        list.add(menuEntity1);
////        list.add(menuEntity);
////
////        // SweetSheet 控件,根据 rl 确认位置
////        final SweetSheet mSweetSheet = new SweetSheet(rl_view);
////
////        //设置数据源 (数据源支持设置 list 数组,也支持从菜单中获取)
////        mSweetSheet.setMenuList(list);
////        //根据设置不同的 Delegate 来显示不同的风格.
////        mSweetSheet.setDelegate(new RecyclerViewDelegate(false));
////        //根据设置不同Effect 来显示背景效果BlurEffect:模糊效果.DimEffect 变暗效果
////        mSweetSheet.setBackgroundEffect(new BlurEffect(8));
////        //设置点击事件
////        mSweetSheet.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
////            @Override
////            public boolean onItemClick(int position, MenuEntity menuEntity1) {
////                //即时改变当前项的颜色
////                switch(position)
////                {
////                    case 0:
////                        RxGalleryFinal
////                                .with(mContext)
////                                .image()
////                                .radio()
////                                .crop()
////                                .imageLoader(ImageLoaderType.FRESCO)
////                                .subscribe(new RxBusResultSubscriber<ImageRadioResultEvent>() {
////                                    @Override
////                                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
////                                        Toast.makeText(getBaseContext(), imageRadioResultEvent.getResult().getOriginalPath(), Toast.LENGTH_SHORT).show();
////                                    }
////                                })
////                                .openGallery();
////                        break;
////                    case 1:
////                        RxGalleryFinal
////                                .with(mContext)
////                                .image()
////                                .radio()
////                                .crop()
////                                .imageLoader(ImageLoaderType.FRESCO)
////                                .subscribe(new RxBusResultSubscriber<ImageRadioResultEvent>() {
////                                    @Override
////                                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
////                                        Toast.makeText(getBaseContext(), imageRadioResultEvent.getResult().getOriginalPath(), Toast.LENGTH_SHORT).show();
////                                    }
////                                })
////                                .openGallery();
////                        break;
////                }
//////                ((RecyclerViewDelegate) mSweetSheet.getDelegate()).notifyDataSetChanged();
//////
//////                //根据返回值, true 会关闭 SweetSheet ,false 则不会.
//////                Toast.makeText(mContext, menuEntity1.title + "  " + position, Toast.LENGTH_SHORT).show();
////                return true;
////            }
////        });
////
////        //Tools.ToastMsg(mContext,"xxxxxxxxxxxxxx");
////
////        mSweetSheet.toggle();
//
//
//        final SweetSheet mSweetSheet3 = new SweetSheet(rl_view);
//        CustomDelegate customDelegate = new CustomDelegate(false,
//                CustomDelegate.AnimationType.DuangLayoutAnimation);
//        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_custom_view, null, false);
//        customDelegate.setCustomView(view);
//        customDelegate.setSweetSheetColor(Color.parseColor("#EFEFEF"));
//
//        mSweetSheet3.setDelegate(customDelegate);
//
//
//
//        view.findViewById(R.id.btn_local).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                RxGalleryFinal
//                        .with(mContext)
//                        .image()
//                        .radio()
//                        .crop()
//                        .imageLoader(ImageLoaderType.FRESCO)
//                        .subscribe(new RxBusResultSubscriber<ImageRadioResultEvent>() {
//                            @Override
//                            protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
//                                Toast.makeText(getBaseContext(), imageRadioResultEvent.getResult().getOriginalPath(), Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .openGallery();
//
//
//                mSweetSheet3.dismiss();
//            }
//        });
//
//        view.findViewById(R.id.btn_online).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                RxGalleryFinal
//                        .with(mContext)
//                        .image()
//                        .radio()
//                        .crop()
//                        .imageLoader(ImageLoaderType.FRESCO)
//                        .subscribe(new RxBusResultSubscriber<ImageRadioResultEvent>() {
//                            @Override
//                            protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
//                                Toast.makeText(getBaseContext(), imageRadioResultEvent.getResult().getOriginalPath(), Toast.LENGTH_SHORT).show();
//                            }
//                        })
//                        .openGallery();
//
//                mSweetSheet3.dismiss();
//            }
//        });
//
//        mSweetSheet3.toggle();
//    }


    private AlertDialog alertDialog;
    private void showSetPhotoDialog(int index) {
        // 初始化自定义布局参数
        LayoutInflater layoutInflater = getLayoutInflater();
        // 为了能在下面的OnClickListener中获取布局上组件的数据，必须定义为final类型.
        View customLayout = layoutInflater.inflate(R.layout.showsetphototdialog,(ViewGroup) findViewById(R.id.customDialog));
        RadioButton rb1= (RadioButton) customLayout.findViewById(R.id.rb_setPhoto1);
        rb1.setOnClickListener(new btnClickListerner(index));
        RadioButton rb2= (RadioButton) customLayout.findViewById(R.id.rb_setPhoto2);
        rb2.setOnClickListener(new btnClickListerner(index));
        alertDialog = new AlertDialog.Builder(this).setView(customLayout).show();
        alertDialog.setCanceledOnTouchOutside(true);
        Window window = alertDialog.getWindow();
        window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
        window.setWindowAnimations(R.style.mystyle); // 添加动画
    }




    class btnClickListerner implements View.OnClickListener {
        private int pos;
        btnClickListerner(int index)
        {
            pos = index;
        }

        @Override
        public void onClick(View view) {
            switch(view.getId())
            {
                case R.id.rb_setPhoto1:
                    RxGalleryFinal
                            .with(mContext)
                            .image()
                            .radio()
                            .crop()
                            .imageLoader(ImageLoaderType.FRESCO)
                            .subscribe(new RxBusResultSubscriber<ImageRadioResultEvent>() {
                                @Override
                                protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                                    //Toast.makeText(getBaseContext(), imageRadioResultEvent.getResult().getOriginalPath(), Toast.LENGTH_SHORT).show();
                                    MakerBean mb = mDatas.get(pos);
                                    mb.imgpath = imageRadioResultEvent.getResult().getCropPath();
                                    mAdapter.notifyDataSetChanged();
                                }
                            })
                            .openGallery();
                    alertDialog.dismiss();
                    break;
                case R.id.rb_setPhoto2:
                    RxGalleryFinal
                            .with(mContext)
                            .image()
                            .radio()
                            .crop()
                            .imageLoader(ImageLoaderType.FRESCO)
                            .subscribe(new RxBusResultSubscriber<ImageRadioResultEvent>() {
                                @Override
                                protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                                    Toast.makeText(getBaseContext(), imageRadioResultEvent.getResult().getCropPath(), Toast.LENGTH_SHORT).show();
                                }
                            })
                            .openGallery();
                    alertDialog.dismiss();
                    break;
            }
        }
    }



    /**
     * 编辑框
     */
    private Dialog mEditDialog = null;
    private EditText mEditText = null;
    private TextView tv_count = null;
    private Button btn_no = null, btn_ok = null;
    public void editDialog(View view,final MakerBean item,final int pos)
    {

        if(mEditDialog == null)
        {
            mEditDialog = new Dialog(mContext,R.style.dialog_wei);
            mEditDialog.setContentView(R.layout.dialog_editor);
            mEditDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            Window window = mEditDialog.getWindow();
            window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置


            WindowManager windowManager = ((Activity) mContext).getWindowManager();
            Display display = windowManager.getDefaultDisplay();
            WindowManager.LayoutParams lp = mEditDialog.getWindow().getAttributes();
            lp.width = (int)(display.getWidth()); //设置宽度
            mEditDialog.getWindow().setAttributes(lp);
        }
        tv_count = (TextView)mEditDialog.findViewById(R.id.tv_count);
        mEditText = (EditText)mEditDialog.findViewById(R.id.et_intro);
        mEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                int strlen = mEditText.getText().toString().trim().length();

                int lastlen = 180-strlen;
                if(lastlen<0)
                {
                    tv_count.setTextColor(Color.RED);
                }else{
                    tv_count.setTextColor(Color.parseColor("#808080"));
                }
                tv_count.setText(String.valueOf(lastlen));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
        if(!TextUtils.isEmpty(item.message))
        {
            mEditText.setText(item.message);
        }



        btn_ok = (Button) mEditDialog.findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String script = mEditText.getText().toString().trim();
//				if (!TextUtils.isEmpty(script))
//				{
                if (script.length() > 180) {
                    Tools.ToastMsg(mContext, "你的解说太长了");
                    return;
                }

//					AppManager.getInstance(mContext).getWb().dataList.get(pos).imageIntro = script;
//					Logger.info(AppManager.getInstance(mContext).getWb().dataList.get(pos).imageIntro+"||"+AppManager.getInstance(mContext).getWb().dataList.get(pos).isImage);
                item.message = script;
                mAdapter.notifyDataSetChanged();
                mEditText.setText("");

                if (null != mEditDialog && mEditDialog.isShowing()) {
                    mEditDialog.dismiss();
                }

//                InputMethodUtils.hide(mContext,mEditText);
//
//                save();

//				}else{
//					Toast.makeText(mContext, "没输入字啊", Toast.LENGTH_SHORT).show();
//				}
            }
        });


        btn_no = (Button) mEditDialog.findViewById(R.id.btn_no);
        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                if (null != mEditDialog && mEditDialog.isShowing()) {
                    mEditDialog.dismiss();
                }
                //InputMethodUtils.hide(mContext, mEditText);
            }
        });

        if(!mEditDialog.isShowing())
        {
            mEditDialog.show();
        }
    }

}
