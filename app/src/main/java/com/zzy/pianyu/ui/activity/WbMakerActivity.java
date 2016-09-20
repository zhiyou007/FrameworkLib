package com.zzy.pianyu.ui.activity;

import android.os.Bundle;
import android.support.design.internal.NavigationMenu;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.zzy.framework.Tools.Tools;
import com.zzy.framework.base.BaseActivity;
import com.zzy.pianyu.R;

import butterknife.Bind;
import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

/**
 * Created by 27928 on 2016/9/20.
 */
public class WbMakerActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.tv_title)
    TextView tv_title;

    @Bind(R.id.fab_speed_dial)
    FabSpeedDial fabSpeedDial;

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
                    case R.id.action_call:
                        Tools.ToastMsg(mContext,"call");
                        break;
                    case R.id.action_text:
                        Tools.ToastMsg(mContext,"text");
                        break;
                }
                return false;
            }
        });

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
