package com.zzy.pianyu.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.zzy.framework.base.BaseFragment;
import com.zzy.pianyu.R;

import butterknife.Bind;

/**
 * Created by zhiyou007 on 2016/9/2.
 */
public class HomeFragment extends BaseFragment {
    @Bind(R.id.tv_info)
    TextView tv_info;
    private int pos;
    public static final HomeFragment newInstance(int pos)
    {
        HomeFragment fragment = new HomeFragment();
        fragment.pos = pos;
        return fragment ;
    }


    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        tv_info.setText(pos+"");
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void onFirstUserVisible() {

    }
}
