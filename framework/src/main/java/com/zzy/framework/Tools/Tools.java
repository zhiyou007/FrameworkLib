package com.zzy.framework.Tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * 和项目无关的一些必备方法
 * Created by zhiyou007 on 2016/9/5.
 */
public class Tools {
    /**
     * Activity跳转通用方法（传值，动画，finish）
     * @param context
     * @param clazz
     * @param bd
     * @param transition_in
     * @param transition_out
     * @param isFinish
     * @param <T>
     */
    public static <T> void goActivity(Context context, Class<T> clazz, Bundle bd, int transition_in, int transition_out, boolean isFinish)
    {

        Intent intent = new Intent(context, clazz);
        if(bd != null)
        {
            intent.putExtras(bd);
        }
        context.startActivity(intent);

        if(context instanceof Activity)
        {//在context可能是application
            ((Activity)context).overridePendingTransition(transition_in, transition_out);
            //是否需要finish页面
            if(isFinish)
            {
                ((Activity)context).finish();
            }
        }
    }


    public static void ToastMsg(Context context,String msg)
    {
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }
}
