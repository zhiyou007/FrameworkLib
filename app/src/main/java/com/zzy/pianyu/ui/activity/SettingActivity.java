package com.zzy.pianyu.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.zzy.framework.base.BaseActivity;
import com.zzy.pianyu.R;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;

/**
 * Created by zhiyou007 on 2016/9/6.
 */
public class SettingActivity extends BaseActivity {
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.btn_theme)
    Button btn_theme;
    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }



    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {

        //去掉标题
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }



}
