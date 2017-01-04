package com.wzm.jokephoto.ui;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.wzm.tools.A;
import com.wzm.tools.ImagePipelineConfigFactory;

/**
 * Created by zhiyou007 on 2016/10/28.
 */
public class PyApplication extends Application {
    public static int iAd = 0;
    @Override
    public void onCreate() {
        super.onCreate();

        ImagePipelineConfig config = ImagePipelineConfigFactory.getOkHttpImagePipelineConfig(this);
        //图片加载库
        Fresco.initialize(this,config);

        PlatformConfig.setWeixin(A.WXID, A.WXKEY);
        PlatformConfig.setSinaWeibo(A.WBID, A.WXKEY);
        PlatformConfig.setQQZone(A.QQID, A.QQKEY);
        Config.REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";



        UMShareAPI.get(this);

        Config.DEBUG = true;
    }
}
