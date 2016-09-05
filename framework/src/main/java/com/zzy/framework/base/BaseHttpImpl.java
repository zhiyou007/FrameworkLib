package com.zzy.framework.base;

/**
 * 基础界面回调（载入中/隐藏载入/显示错误(空页面，错误，网络)/网络接口请求回调）
 * Created by zhiyou007 on 2015/10/27.
 */
public interface BaseHttpImpl {
    /**
     * show loading message
     *
     * @param msg
     */
    void showLoading(String msg);

    /**
     * hide loading
     */
    void hideLoading();

    /**
     * show error message
     */
    void showError(int code, int event_tag);

    //网络返回数据回调
    void CommonDataComing(int event_tag, Object data);

}
