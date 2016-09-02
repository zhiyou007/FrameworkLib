package com.zzy.framework.adater.abslistview;

import android.content.Context;

import java.util.List;

/**
 * Created by zhiyou007 on 2016/8/16.
 */
public abstract class CommonMultiTypeAdapter<T> extends MultiItemTypeAdapter<T> {
    public CommonMultiTypeAdapter(Context context, List<T> datas)
    {
        super(context, datas);
        addMuTypeDelegate();
    }

    abstract public void addMuTypeDelegate();


}
