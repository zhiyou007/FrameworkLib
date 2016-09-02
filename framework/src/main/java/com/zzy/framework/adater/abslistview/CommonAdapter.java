package com.zzy.framework.adater.abslistview;

import android.content.Context;

import com.zzy.framework.adater.abslistview.base.ItemViewDelegate;

import java.util.List;

/**
 * https://github.com/hongyangAndroid/baseAdapter
 * 上述地址可以查看使用方法
 */

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T>
{

    public CommonAdapter(Context context, final int layoutId, List<T> datas)
    {
        super(context, datas);

        addItemViewDelegate(new ItemViewDelegate<T>()
        {
            @Override
            public int getItemViewLayoutId()
            {
                return layoutId;
            }

            @Override
            public boolean isForViewType(T item, int position)
            {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position)
            {
                CommonAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(ViewHolder viewHolder, T item, int position);

}
