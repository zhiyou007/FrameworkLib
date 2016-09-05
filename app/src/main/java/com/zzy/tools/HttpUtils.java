package com.zzy.tools;

import android.content.Context;
import android.text.TextUtils;
import com.squareup.okhttp.Request;
import com.zzy.framework.Tools.Logger;
import com.zzy.framework.Tools.OkHttpClientManager;
import com.zzy.pianyu.ui.Impl.CommonData;
import com.zzy.pianyu.ui.bean.ResponeInfo;

import java.util.Map;

/**
 * Created by zhiyou007 on 2015/10/9.
 */
public class HttpUtils {

    /**
     * POST非json格式
     * @param context
     * @param params  post参数
     * @param callback  数据处理回调
     * @param isShowCoin  是否需要显示增加金币
     */
    public static void post(final Context context, String url, final int event_tag, Map<String, String> params, final CommonData callback, final boolean isShowCoin)
    {
        OkHttpClientManager.postAsyn(url, params, new OkHttpClientManager.ResultCallback<ResponeInfo>() {
            @Override
            public void onError(Request request, Exception e) {
                callback.FailData(0, event_tag);
            }

            @Override
            public void onResponse(ResponeInfo response) {
                //可以显示一个增加金币的功能
//                if (isShowCoin) {
//                    String coin = response.getCoin();
//                    if (!TextUtils.isEmpty(coin) && !coin.equals("0")) {
//                        UIHelper.showCustomCoinToast(context, coin, R.mipmap.toast_coin);
//                    }
//                }
                callback.CommonParseData(response, false, event_tag);
            }

            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                callback.onBefore(request);
            }

            @Override
            public void onAfter() {
                super.onAfter();
                callback.onAfter();
            }
        }, null);
    }

    public static void get(Context context,String url,final int event_tag,final CommonData callback)
    {
        OkHttpClientManager.getAsyn(url, new OkHttpClientManager.ResultCallback<ResponeInfo>() {
            @Override
            public void onError(Request request, Exception e) {
                callback.FailData(e.hashCode(),event_tag);
            }

            @Override
            public void onResponse(ResponeInfo response) {
                callback.CommonParseData(response,false,event_tag);
            }

            @Override
            public void onBefore(Request request) {
                super.onBefore(request);
                callback.onBefore(request);
            }

            @Override
            public void onAfter() {
                super.onAfter();
                callback.onAfter();
            }
        });
    }


}
