package com.zzy.pianyu.ui.Impl;

import com.squareup.okhttp.Request;
import com.zzy.pianyu.ui.bean.ResponeInfo;

import org.json.JSONException;

/**
 * 网络回调接口
 * Created by zhiyou007 on 2015/10/9.
 */
public interface CallBackData {
    /**
     * 返回数据结构
     * @param data
     * @param isCache
     * @param event_tag
     */
    void CommonParseData(String data, boolean isCache, int event_tag);

    /**
     * 获取失败调用
     * @param code
     * @param event_tag
     */
    void FailData(int code, int event_tag);

    /**
     * 数据返回前（比如需要载入一个loading）
     * @param request
     */
    void onBefore(Request request);

    /**
     * 数据返回后（比如需要关闭一个loading）
     */
    void onAfter();
}
