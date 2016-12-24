package com.wzm.framework.uihelper;

import android.net.Uri;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

/**
 * Created by zhiyou007 on 2016/10/28.
 */
public class ImgHelper {
    /**
     * 图片显示
     * @param view
     * @param url
     * @param isGif
     */
    public static void display(SimpleDraweeView view, String url, boolean isGif)
    {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setAutoPlayAnimations(isGif)
                .build();
        view.setController(controller);
    }

    /**
     * 图片宽高未知的情况下处理
     * @param view
     * @param url
     * @param controllerListener
     */
    public static void display(SimpleDraweeView view,String url,ControllerListener controllerListener)
    {

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(controllerListener)
                .setUri(Uri.parse(url))
                .setAutoPlayAnimations(true)
                .build();
        view.setController(controller);

    }
}
