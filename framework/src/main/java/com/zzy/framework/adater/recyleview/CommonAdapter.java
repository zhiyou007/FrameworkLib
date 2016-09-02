package com.zzy.framework.adater.recyleview;

import android.content.Context;
import android.view.LayoutInflater;

import com.zzy.framework.adater.recyleview.base.ItemViewDelegate;

import java.util.List;


/**
* https://github.com/hongyangAndroid/baseAdapter
* 上述地址可以查看使用方法
*/

public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T>
{
   protected Context mContext;
   protected int mLayoutId;
   protected List<T> mDatas;
   protected LayoutInflater mInflater;

   public CommonAdapter(final Context context, final int layoutId, List<T> datas)
   {
       super(context, datas);
       mContext = context;
       mInflater = LayoutInflater.from(context);
       mLayoutId = layoutId;
       mDatas = datas;

       addItemViewDelegate(new ItemViewDelegate<T>()
       {
           @Override
           public int getItemViewLayoutId()
           {
               return layoutId;
           }

           @Override
           public boolean isForViewType( T item, int position)
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

   protected abstract void convert(ViewHolder holder, T t, int position);


}
