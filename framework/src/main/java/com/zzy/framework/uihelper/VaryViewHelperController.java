/*
 * Copyright (c) 2015 [1076559197@qq.com | tchen0707@gmail.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zzy.framework.uihelper;


import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zzy.framework.R;


public class VaryViewHelperController {

    private IVaryViewHelper helper;

    public VaryViewHelperController(View view) {
        this(new VaryViewHelper(view));
    }

    public VaryViewHelperController(IVaryViewHelper helper) {
        super();
        this.helper = helper;
    }

    /**
     * 错误信息（没有网络，解析异常等）
     * @param onClickListener
     */
    public void showError(int icon, String message, View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.ui_error);

        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        if(!TextUtils.isEmpty(message))
        {
            textView.setText(Html.fromHtml(message));
        }


        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        if(icon!=0)
        {
            imageView.setImageResource(icon);
        }else{
            imageView.setImageResource(R.mipmap.something_data_failed);
        }


        if (null != onClickListener) {
            layout.setOnClickListener(onClickListener);
        }

        helper.showLayout(layout);
    }

    /**
     * 显示空列表的时候
     * @param emptyMsg
     * @param onClickListener
     */
    public void showEmpty(int icon, String emptyMsg, View.OnClickListener onClickListener) {
        View layout = helper.inflate(R.layout.ui_empty);
        TextView textView = (TextView) layout.findViewById(R.id.message_info);
        if (!TextUtils.isEmpty(emptyMsg)) {
            textView.setText(emptyMsg);
        }

        ImageView imageView = (ImageView) layout.findViewById(R.id.message_icon);
        if(icon!=0)
        {
            imageView.setImageResource(icon);
        }else{
            imageView.setImageResource(R.mipmap.something_empty);
        }


        if (null != onClickListener) {
            layout.setOnClickListener(onClickListener);
        }

        helper.showLayout(layout);
    }

    public View showView(int layoutid)
    {
        View layout = helper.inflate(layoutid);
        helper.showLayout(layout);
        return layout;
    }

    /**
     * 页面载入中显示
     * @param msg
     */
    public void showLoading(String msg) {
        View layout = helper.inflate(R.layout.ui_loading);
        if (!TextUtils.isEmpty(msg)) {
            TextView textView = (TextView) layout.findViewById(R.id.loading_msg);
            textView.setText(msg);
        }
        helper.showLayout(layout);
    }

    /**
     * 页面复位
     * 例如正确载入完成时调用
     */
    public void restore() {
        helper.restoreView();
    }
}
