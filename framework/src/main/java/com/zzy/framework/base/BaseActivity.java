package com.zzy.framework.base;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.umeng.analytics.MobclickAgent;
import com.zzy.framework.R;
import com.zzy.framework.uihelper.VaryViewHelperController;

import butterknife.ButterKnife;

/**
 * Created by zhiyou007 on 2015/10/27.
 */
public abstract class BaseActivity extends SwipeActivity  implements BaseViewImpl {
    protected Context mContext = null;
    /**
     * Screen information
     */
//    protected int mScreenWidth = 0;
//    protected int mScreenHeight = 0;
//    protected float mScreenDensity = 0.0f;

    private VaryViewHelperController mVaryViewHelperController = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            getBundleExtras(extras);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Window window = getWindow();
                // Translucent status bar
                window.getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

//      目前没用到
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
//        {
//            SystemBarTintManager tintManager = new SystemBarTintManager(this);
//            tintManager.setStatusBarTintEnabled(true);
//            tintManager.setStatusBarTintColor(R.color.status_bar);
//        }

          //获取屏幕宽度高度的需求
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        mScreenDensity = displayMetrics.density;
//        mScreenHeight = displayMetrics.heightPixels;
//        mScreenWidth = displayMetrics.widthPixels;



        if (getContentViewLayoutID() != 0) {
            setContentView(getContentViewLayoutID());
        } else {
            throw new IllegalArgumentException("You must return a right contentView layout resource Id");
        }

        initViewsAndEvents(savedInstanceState);

    }



//    protected void setStatusBar() {
//        //StatusBarUtils.setTranslucent(this);
//        StatusBarUtils.setColor(this, getResources().getColor(R.color.primary));
//    }






    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                super.finish();
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 获取loadingView的父容器VIEW
     */
    protected abstract View getLoadingTargetView();

    /**
     * 传值
     *
     * @param extras
     */
    protected abstract void getBundleExtras(Bundle extras);


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        //setStatusBar();
        //一定要放在setContentView后面
        ButterKnife.bind(this);


        if (null != getLoadingTargetView()) {
            mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
        }

    }


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(mContext);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(mContext);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

        ButterKnife.unbind(this);

//        if (isBindEventBusHere()) {
//            EventBus.getDefault().unregister(this);
//        }

    }



    /**
     * bind layout resource file
     *
     * @return id of layout resource
     */
    protected abstract int getContentViewLayoutID();

    /**
     * init all views and add events
     */
    protected abstract void initViewsAndEvents(Bundle savedInstanceState);




    /**
     * 以下几个方法是载入中，显示错误，隐藏载入等的接口方法
     */

    @Override
    public void showLoading(String msg) {
        toggleShowLoading(true, null);
    }

    @Override
    public void hideLoading() {
        toggleShowLoading(false, null);
    }

    @Override
    public  void showError(int code, int event_tag) {
        toggleShowError(true, getResources().getString(R.string.common_no_network_msg),0, null);
    }


    /**
     * toggle show loading
     * 加载时显示的VIEW
     * @param toggle
     */
    protected void toggleShowLoading(boolean toggle, String msg) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showLoading(msg);
        } else {
            mVaryViewHelperController.restore();
        }
    }


    /**
     * toggle show error
     * 发生错误时显示的VIEW
     * @param toggle
     */
    protected void toggleShowError(boolean toggle, String msg, int resId, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            if(resId==0)
            {
                mVaryViewHelperController.showError(R.mipmap.something_data_failed, msg, onClickListener);
            }else{
                mVaryViewHelperController.showError(resId, msg, onClickListener);
            }
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show error
     * 发生错误时显示的VIEW
     * @param toggle
     */
    protected void toggleShowNetworkError(boolean toggle, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            mVaryViewHelperController.showError(R.mipmap.something_wifi_wrong, getResources().getString(R.string.common_no_network_msg), onClickListener);
        } else {
            mVaryViewHelperController.restore();
        }
    }

    /**
     * toggle show empty
     * 数据为空时显示的VIEW
     * @param toggle
     */
    protected void toggleShowEmpty(boolean toggle, String msg, int resId, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            if(resId==0)
            {
                mVaryViewHelperController.showEmpty(R.mipmap.something_empty, msg, onClickListener);
            }else{
                mVaryViewHelperController.showEmpty(resId, msg, onClickListener);
            }

        } else {
            mVaryViewHelperController.restore();
        }
    }

    protected View toggleView(boolean toggle, int layoutId)
    {
        View view = null;
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }

        if (toggle) {
            view = mVaryViewHelperController.showView(layoutId);
        } else {
            mVaryViewHelperController.restore();
        }
        return view;
    }
}
