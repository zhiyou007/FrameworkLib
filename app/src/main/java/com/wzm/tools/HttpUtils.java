package com.wzm.tools;

import android.content.Context;

import com.squareup.okhttp.Request;
import com.wzm.framework.Tools.Logger;
import com.wzm.framework.Tools.OkHttpClientManager;
import com.wzm.jokephoto.ui.Impl.CallBackData;
import com.wzm.jokephoto.ui.Impl.CommonData;
import com.wzm.jokephoto.ui.bean.ResponeInfo;

import java.util.Map;

/**
 * Created by zhiyou007 on 2015/10/9.
 */
public class HttpUtils {
    public static final String GETID = "http://121.41.88.44/pianyu/u.php";
    public static final String LIST = "http://121.41.88.44/pianyu/getList.php";
    public static final String DING = "http://121.41.88.44/pianyu/ding.php";
    public static final String MYLIKE="http://121.41.88.44/pianyu/getMyLike.php";
    //public static final String LIST = "http://192.168.3.2/pianyu/getList.php";
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


    public static void get(Context context,String url,final int event_tag,final CallBackData callback)
    {
        OkHttpClientManager.getAsyn(url, new OkHttpClientManager.ResultCallback<String>() {
            @Override
            public void onError(Request request, Exception e) {

                Logger.error(e.getMessage());
                callback.FailData(e.hashCode(),event_tag);
            }

            @Override
            public void onResponse(String response) {
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
