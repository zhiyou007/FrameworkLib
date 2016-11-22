package com.zzy.framework.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;
import com.zzy.framework.R;
import com.zzy.framework.uihelper.VaryViewHelperController;

import java.lang.reflect.Field;

import butterknife.ButterKnife;

/**
 * Fragment基础封装类
 * Created by zhiyou007 on 2015/10/26.
 */
public abstract class BaseFragment extends Fragment implements BaseViewImpl {
    /**
     * Screen information
     */
    protected int mScreenWidth = 0;
    protected int mScreenHeight = 0;
    protected float mScreenDensity = 0.0f;

    /**
     * Log tag
     */
    protected static String TAG_LOG = null;


    protected Context mContext = null;


    private boolean isFirstVisible = true;
    private boolean isFirstResume = true;
    private boolean isFirstInvisible = true;
    private boolean isPrepared;


    private VaryViewHelperController mVaryViewHelperController = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        TAG_LOG = this.getClass().getSimpleName();
//        if(isBindEventBusHere())
//        {
//            EventBus.getDefault().register(this);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /**
         * 载入VIEWID
         */
        if (getContentViewLayoutID() != 0) {
            return inflater.inflate(getContentViewLayoutID(), null);
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();
        ButterKnife.bind(this, view);
        if (null != getLoadingTargetView()) {
            mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
        }

        /**
         * 获取屏幕分辨率信息
         */

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mScreenDensity = displayMetrics.density;
        mScreenHeight = displayMetrics.heightPixels;
        mScreenWidth = displayMetrics.widthPixels;
        initViewsAndEvents();
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (isBindEventBusHere()) {
//            EventBus.getDefault().unregister(this);
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        // for bug ---> java.lang.IllegalStateException: Activity has been destroyed
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * abstract方法
     */

    /**
     * 获取loadingView的父容器VIEW
     */
    protected abstract View getLoadingTargetView();

    /**
     * 初始化界面和事件
     */
    protected abstract void initViewsAndEvents();


    protected boolean isBindEventBusHere()
    {
        return false;
    }
    /**
     * 获取layoutID
     */
    protected abstract int getContentViewLayoutID();

    /**
     * fragent第一次显示出来的时候
     */
    protected abstract void onFirstUserVisible();




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initPrepare();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isFirstResume) {
            isFirstResume = false;
            return;
        }
        if (getUserVisibleHint()) {
            onUserVisible();
        }

        MobclickAgent.onResume(mContext);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            onUserInvisible();
        }

        MobclickAgent.onPause(mContext);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false;
                initPrepare();
            } else {
                onUserVisible();
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false;
                onFirstUserInvisible();
            } else {
                onUserInvisible();
            }
        }
    }

    private synchronized void initPrepare() {
        if (isPrepared) {
            onFirstUserVisible();
        } else {
            isPrepared = true;
        }
    }




    /**
     * this method like the fragment's lifecycle method onResume()
     */
    protected void onUserVisible(){};

    /**
     * when fragment is invisible for the first time
     */
    private void onFirstUserInvisible() {
        // here we do not recommend do something
    }

    /**
     * this method like the fragment's lifecycle method onPause()
     */
    protected void onUserInvisible(){};


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
    public void showError(int code, int event_tag) {
        toggleShowError(true, "您已不在服务区", R.mipmap.something_data_failed, null);
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
    protected void toggleShowError(boolean toggle, String msg, int resid, View.OnClickListener onClickListener) {
        if (null == mVaryViewHelperController) {
            throw new IllegalArgumentException("You must return a right target view for loading");
        }
        if (toggle) {
            mVaryViewHelperController.showError(resid, msg, onClickListener);
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

}
