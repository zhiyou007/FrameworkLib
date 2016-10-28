package com.zzy.pianyu.ui;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.zzy.tools.ImagePipelineConfigFactory;

/**
 * Created by zhiyou007 on 2016/10/28.
 */
public class PyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ImagePipelineConfig config = ImagePipelineConfigFactory.getOkHttpImagePipelineConfig(this);
        //图片加载库
        Fresco.initialize(this,config);
    }
}
