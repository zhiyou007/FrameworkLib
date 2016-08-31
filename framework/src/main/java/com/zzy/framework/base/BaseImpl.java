package com.zzy.framework.base;

/**
 * Created by zhiyou007 on 2015/10/27.
 */
public interface BaseImpl {
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

}
