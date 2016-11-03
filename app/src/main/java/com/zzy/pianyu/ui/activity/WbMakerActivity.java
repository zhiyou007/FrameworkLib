package com.zzy.pianyu.ui.activity;

import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.zzy.framework.Tools.Tools;
import com.zzy.framework.adater.recyleview.CommonMultiTypeAdapter;
import com.zzy.framework.adater.recyleview.ViewHolder;
import com.zzy.framework.adater.recyleview.base.ItemViewDelegate;
import com.zzy.framework.base.BaseActivity;
import com.zzy.pianyu.R;
import com.zzy.pianyu.ui.bean.MakerBean;
import com.zzy.pianyu.ui.widgets.sweetsheet.entity.MenuEntity;
import com.zzy.pianyu.ui.widgets.sweetsheet.sweetpick.BlurEffect;
import com.zzy.pianyu.ui.widgets.sweetsheet.sweetpick.RecyclerViewDelegate;
import com.zzy.pianyu.ui.widgets.sweetsheet.sweetpick.SweetSheet;

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
                    public void convert(ViewHolder holder, MakerBean makerBean,final int position) {
                        //图片长按编辑事件
                        SimpleDraweeView iv_img = holder.getView(R.id.iv_img);
                        iv_img.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                //长按弹出操作选项
                                setUpActionView(position);
                                return false;
                            }
                        });
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


    public void setUpActionView(int index)
    {
        final ArrayList<MenuEntity> list = new ArrayList<>();
        //添加假数据
        MenuEntity menuEntity1 = new MenuEntity();
        menuEntity1.iconId = R.drawable.heart0;
        menuEntity1.title = "code";
        MenuEntity menuEntity = new MenuEntity();
        menuEntity.iconId = R.drawable.heart0;
        menuEntity.title = "QQ";
        list.add(menuEntity1);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        list.add(menuEntity);
        // SweetSheet 控件,根据 rl 确认位置
        final SweetSheet mSweetSheet = new SweetSheet(rl_view);

        //设置数据源 (数据源支持设置 list 数组,也支持从菜单中获取)
        mSweetSheet.setMenuList(list);
        //根据设置不同的 Delegate 来显示不同的风格.
        mSweetSheet.setDelegate(new RecyclerViewDelegate(true));
        //根据设置不同Effect 来显示背景效果BlurEffect:模糊效果.DimEffect 变暗效果
        mSweetSheet.setBackgroundEffect(new BlurEffect(8));
        //设置点击事件
        mSweetSheet.setOnMenuItemClickListener(new SweetSheet.OnMenuItemClickListener() {
            @Override
            public boolean onItemClick(int position, MenuEntity menuEntity1) {
                //即时改变当前项的颜色

                ((RecyclerViewDelegate) mSweetSheet.getDelegate()).notifyDataSetChanged();

                //根据返回值, true 会关闭 SweetSheet ,false 则不会.
                Toast.makeText(mContext, menuEntity1.title + "  " + position, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        //Tools.ToastMsg(mContext,"xxxxxxxxxxxxxx");

        mSweetSheet.toggle();
    }
}
