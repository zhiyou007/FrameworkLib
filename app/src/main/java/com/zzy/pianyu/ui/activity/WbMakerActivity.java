package com.zzy.pianyu.ui.activity;

import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.zzy.framework.Tools.Tools;
import com.zzy.framework.adater.recyleview.CommonMultiTypeAdapter;
import com.zzy.framework.adater.recyleview.ViewHolder;
import com.zzy.framework.adater.recyleview.base.ItemViewDelegate;
import com.zzy.framework.base.BaseActivity;
import com.zzy.pianyu.R;
import com.zzy.pianyu.ui.bean.MakerBean;

import java.util.ArrayList;

import butterknife.Bind;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

/**
 * Created by zhiyou007 on 2016/9/20.
 */
public class WbMakerActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.ry_view)
    RecyclerView ry_view;

    @Bind(R.id.fab_speed_dial)
    FabSpeedDial fabSpeedDial;

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


        // 创建一个线性布局管理器
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        // 默认是Vertical，可以不写
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ry_view.setLayoutManager(mLayoutManager);

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
                            ry_view.scrollToPosition(mDatas.size());
                        }
                        break;
                    case R.id.action_text:

                        {
                            MakerBean mb = new MakerBean();
                            mb.type = 1;
                            mDatas.add(mb);
                            mAdapter.notifyDataSetChanged();
                            ry_view.scrollToPosition(mDatas.size());
                        }
                        break;
                    case R.id.action_txt_img:

                        {
                            MakerBean mb = new MakerBean();
                            mb.type = 3;
                            mDatas.add(mb);
                            mAdapter.notifyDataSetChanged();
                            ry_view.scrollToPosition(mDatas.size());
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
                    public void convert(ViewHolder holder, MakerBean makerBean, int position) {
                        Tools.ToastMsg(mContext,"2");
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
                    public void convert(ViewHolder holder, MakerBean makerBean, int position) {
                        Tools.ToastMsg(mContext,"1");
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
                    public void convert(ViewHolder holder, MakerBean makerBean, int position) {
                        Tools.ToastMsg(mContext,"3");
                    }
                });

            }
        };

        ry_view.setAdapter(mAdapter);

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
}
