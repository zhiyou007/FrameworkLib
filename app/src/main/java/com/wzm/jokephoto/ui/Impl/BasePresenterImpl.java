package com.wzm.jokephoto.ui.Impl;

import android.content.Context;

import com.squareup.okhttp.Request;
import com.wzm.framework.Tools.NetworkTools;
import com.wzm.framework.Tools.Tools;
import com.wzm.framework.base.BaseHttpImpl;
import com.wzm.jokephoto.ui.bean.ResponeInfo;
import com.wzm.tools.ErrorCode;

/**
 * Created by zhiyou007 on 2015/10/10.
 */
public class BasePresenterImpl implements CommonData {

    public Context mContext;
    public BaseHttpImpl mCallBack;
    public boolean isFirst = true;
    public boolean isShowLoading = true;
    public boolean isErr = false;

    public BasePresenterImpl(Context mContext, BaseHttpImpl callback, boolean isShowLoading) {
        this.mContext = mContext;
        this.mCallBack = callback;
        this.isShowLoading =  isShowLoading;
    }

    /**
     * 返回的数据结构
     * @param result
     */
    @Override
    public void CommonParseData(ResponeInfo result, boolean isCache, int event_tag) {

        int status = result.getS();
        if (status == 1) {

            SucData(result.getD(),event_tag);
        }else{
            //打印服务器端返回的错误信息
            Tools.ToastMsg(mContext,result.getM());
            FailData(ErrorCode.ERROR_SER_ERROR, event_tag);
        }
        //没有网络读取缓存的时候，下拉刷新会有LOADDING的BUG
        isFirst = false;

    }

    /**
     * 获取数据
     */
    @Override
    public void CommonGetData(int event_tag) {
        isErr = false;
    }

    @Override
    public void SucData(Object data, int event_tag) {
        mCallBack.CommonDataComing(event_tag, data);

    }

    @Override
    public void FailData(int code,int event_tag) {
        mCallBack.showError(code,event_tag);
        isErr = true;
    }

    @Override
    public void onBefore(Request request) {
        if(!NetworkTools.isNetworkAvailable(mContext))
        {
            if(isFirst)
            {
                mCallBack.showError(0,ErrorCode.ERROR_NETWORK);
            }else{
                Tools.ToastMsg(mContext,"网络不可用");
            }
            return;
        }

        if(isFirst)
        {
            isFirst = false;
            if (isShowLoading)
            {
                mCallBack.showLoading("加载中...");
            }
        }
    }

    @Override
    public void onAfter() {
        if(mCallBack!=null&&!isErr)
        {
            mCallBack.hideLoading();
        }
    }

}
