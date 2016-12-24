package com.wzm.jokephoto.ui.Impl;

import com.squareup.okhttp.Request;
import com.wzm.jokephoto.ui.bean.ResponeInfo;

import org.json.JSONException;

/**
 * 网络回调接口
 * Created by zhiyou007 on 2015/10/9.
 */
public interface CommonData {
    /**
     * 返回数据结构
     * @param data
     * @param isCache
     * @param event_tag
     */
    void CommonParseData(ResponeInfo data, boolean isCache, int event_tag);

    /**
     * 开始获取数据
     * @param event_tag
     */
    void CommonGetData(int event_tag);

    /**
     * 有用的数据
     * @param data
     * @param event_tag
     * @throws JSONException
     */
    void SucData(Object data, int event_tag)  throws JSONException;

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
