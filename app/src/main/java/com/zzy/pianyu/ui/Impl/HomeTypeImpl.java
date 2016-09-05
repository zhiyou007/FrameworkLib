package com.zzy.pianyu.ui.Impl;

import android.content.Context;

import com.squareup.okhttp.Request;
import com.zzy.framework.base.BaseHttpImpl;
import com.zzy.framework.base.BaseViewImpl;
import com.zzy.pianyu.ui.bean.ResponeInfo;
import com.zzy.tools.HttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 网络请求类
 * Created by zhiyou007 on 2016/9/5.
 */
public class HomeTypeImpl extends BasePresenterImpl {

    private String url = "http://121.41.88.44:7077/api/yun/rest/JzList";
    private int id = 0;
    private int pagesize = 20;
    public HomeTypeImpl(Context mContext, BaseHttpImpl callback, boolean isShowLoading) {
        super(mContext, callback, isShowLoading);
    }

    @Override
    public void CommonParseData(ResponeInfo result, boolean isCache, int event_tag) {
        super.CommonParseData(result, isCache, event_tag);
    }

    @Override
    public void CommonGetData(int event_tag) {
        super.CommonGetData(event_tag);

        //参数形式POST
//        try {
//            JSONObject params = StringUtils.getCommParams();
//            params.put("gmcmd", cmd);
//            JSONObject content = new JSONObject();
//            content.put("limit",limit);
//            params.put("gmc", URLEncoder.encode(content.toString(),"utf-8"));
//
//            HttpUtils.post(mContext, event_tag, params.toString(), this, false);
//
//        } catch (JSONException e) {
//            FailData(ErrorCode.ERROR_CODE_JSONException,event_tag);
//        } catch (UnsupportedEncodingException e) {
//            FailData(ErrorCode.ERROR_CODE_UnsupportedEncodingException,event_tag);
//        }



        //获取数据
        String geturl = url+"?jid="+id+"&pagesize="+pagesize;
        HttpUtils.get(mContext,geturl,event_tag,this);

    }

    @Override
    public void SucData(Object data, int event_tag){
        super.SucData(data, event_tag);
    }

    @Override
    public void FailData(int code, int event_tag) {
        super.FailData(code, event_tag);
    }

    @Override
    public void onBefore(Request request) {
        super.onBefore(request);
    }

    @Override
    public void onAfter() {
        super.onAfter();
    }
}
