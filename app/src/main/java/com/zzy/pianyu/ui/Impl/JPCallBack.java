package com.zzy.pianyu.ui.Impl;


import android.graphics.Bitmap;

/**
 * 截屏回调
 * Created by 27928 on 2016/1/3.
 */
public interface JPCallBack {
    void suc(Bitmap bm);
    void error();
    void onBefore();
    void onAfter();
}
