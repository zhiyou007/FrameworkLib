package com.zzy.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.facebook.imagepipeline.request.Postprocessor;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnItemClickListener;

/**
 * Created by zhiyou007 on 2015/10/9.
 */
public class UIHelper {

    /**
     * 重新计算listview高度（用于CELL高度不一造成一些显示不全）
     *
     * @param listView
     */

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        if(listView == null)
        {
            return;
        }
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    /**
     * 设置状态栏透明
     * @param activity
     */
    public static void setTranslucentStatus(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
            rootView.setFitsSystemWindows(true);
            rootView.setClipToPadding(true);
        }
    }

    public static void display(SimpleDraweeView view, String url, boolean isGif)
    {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setAutoPlayAnimations(isGif)
                .build();
        view.setController(controller);
    }

    public static void display(SimpleDraweeView view, String url, boolean isGif, int width, int height)
    {
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                .setResizeOptions(new ResizeOptions(width, height))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setAutoPlayAnimations(isGif)
                .build();
        view.setController(controller);
    }


    public static void display(Context mContext, SimpleDraweeView view, String url, int hodeImageId, boolean isGif, boolean isCircle, String border_color, String back_color, float border)
    {

            GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(mContext.getResources());
            if(isCircle)
            {
                RoundingParams roundingParams = RoundingParams.asCircle();
                roundingParams.setBorder(Color.parseColor(border_color), border);
                roundingParams.setOverlayColor(Color.parseColor(back_color));
                builder.setRoundingParams(roundingParams);
            }
            GenericDraweeHierarchy hierarchy = builder.build();
            hierarchy.setPlaceholderImage(hodeImageId);
            view.setHierarchy(hierarchy);

            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                    .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setAutoPlayAnimations(isGif)
                    .build();
            view.setController(controller);

    }


    public static void display(SimpleDraweeView view, String url, ControllerListener controllerListener)
    {

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setControllerListener(controllerListener)
                .setUri(Uri.parse(url))
                .setAutoPlayAnimations(true)
                .build();
        view.setController(controller);

    }

    public static void display(Context mContext, SimpleDraweeView view, String url, int hodeImageId, boolean isGif, boolean isCircle)
    {
        GenericDraweeHierarchy hierarchy = view.getHierarchy();
        if(isGif)
        {
            if(isCircle)
            {
                RoundingParams roundingParams = RoundingParams.asCircle();
                roundingParams.setBorder(Color.parseColor("#aaaaaa"), 2.0f);
                roundingParams.setOverlayColor(Color.parseColor("#ffffff"));
                hierarchy.setRoundingParams(roundingParams);
            }
            hierarchy.setPlaceholderImage(hodeImageId);
            view.setHierarchy(hierarchy);

            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setAutoPlayAnimations(true)
                    .build();
            view.setController(controller);
        }else{
            hierarchy.setPlaceholderImage(hodeImageId);
            view.setHierarchy(hierarchy);
            view.setImageURI(Uri.parse(url));
        }
    }



    public static void display(Context mContext, SimpleDraweeView view, String url, int hodeImageId, boolean isGif, boolean isCircle, int width, int height)
    {
        GenericDraweeHierarchy hierarchy = view.getHierarchy();

        if(isCircle)
        {
            RoundingParams roundingParams = RoundingParams.asCircle();
            roundingParams.setBorder(Color.parseColor("#aaaaaa"), 2.0f);
            roundingParams.setOverlayColor(Color.parseColor("#ffffff"));
            hierarchy.setRoundingParams(roundingParams);
        }
        hierarchy.setPlaceholderImage(hodeImageId);
        view.setHierarchy(hierarchy);

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                .setResizeOptions(new ResizeOptions(width, height))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setAutoPlayAnimations(isGif)
                .build();
        view.setController(controller);

    }


    public static void display(Context mContext, SimpleDraweeView view, String url, int hodeImageId, boolean isGif, boolean isCircle, String border_color, String back_color, float border, int width, int height)
    {
        GenericDraweeHierarchy hierarchy = view.getHierarchy();
        if(isGif)
        {
            if(isCircle)
            {
                RoundingParams roundingParams = RoundingParams.asCircle();
                roundingParams.setBorder(Color.parseColor(border_color), border);
                roundingParams.setOverlayColor(Color.parseColor(back_color));
                hierarchy.setRoundingParams(roundingParams);
            }
            hierarchy.setPlaceholderImage(hodeImageId);
            view.setHierarchy(hierarchy);

            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                    .setResizeOptions(new ResizeOptions(width, height))
                    .build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setAutoPlayAnimations(true)
                    .build();
            view.setController(controller);
        }else{
            hierarchy.setPlaceholderImage(hodeImageId);
            view.setHierarchy(hierarchy);
            view.setImageURI(Uri.parse(url));
        }
    }


    public static void display(Context mContext, SimpleDraweeView view, String url, int hodeImageId, boolean isGif, float border)
    {
        GenericDraweeHierarchy hierarchy = view.getHierarchy();
        if(isGif)
        {
            RoundingParams roundingParams = RoundingParams.fromCornersRadius(border);
            hierarchy.setRoundingParams(roundingParams);
            hierarchy.setPlaceholderImage(hodeImageId);
            view.setHierarchy(hierarchy);
            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url)).build();
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setAutoPlayAnimations(true)
                    .build();
            view.setController(controller);
        }else{
            RoundingParams roundingParams = RoundingParams.fromCornersRadius(border);
            hierarchy.setRoundingParams(roundingParams);
            hierarchy.setPlaceholderImage(hodeImageId);
            view.setHierarchy(hierarchy);
            view.setImageURI(Uri.parse(url));
        }
    }

    public static void display(SimpleDraweeView view, String url, int hodeImageId, int width, int height) {
        GenericDraweeHierarchy hierarchy = view.getHierarchy();
        hierarchy.setPlaceholderImage(hodeImageId);
        hierarchy.setFadeDuration(0);
        view.setHierarchy(hierarchy);

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                .setResizeOptions(new ResizeOptions(width, height))
                .build();
        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setOldController(view.getController())
                .setImageRequest(request)
                .setAutoPlayAnimations(true)
                .build();
        view.setController(controller);
    }



    public static void display(Context mContext, SimpleDraweeView view, String url, int hodeImageId, boolean isGif, float border, int width, int height)
    {
        GenericDraweeHierarchy hierarchy = view.getHierarchy();

        RoundingParams roundingParams = RoundingParams.fromCornersRadius(border);
        hierarchy.setRoundingParams(roundingParams);
        hierarchy.setPlaceholderImage(hodeImageId);
        view.setHierarchy(hierarchy);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                .setResizeOptions(new ResizeOptions(width, height))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setImageRequest(request)
                    .setAutoPlayAnimations(isGif)
                    .build();
        view.setController(controller);

    }

    public static void display(SimpleDraweeView view, String url, int hodeImageId, Postprocessor postprocessor) {
        GenericDraweeHierarchy hierarchy = view.getHierarchy();
        hierarchy.setPlaceholderImage(hodeImageId);
        view.setHierarchy(hierarchy);

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(url))
                .setPostprocessor(postprocessor)
                .build();
        PipelineDraweeController controller =
                (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setOldController(view.getController())
                        .build();
        view.setController(controller);
    }



    //显示弹出框
    public static DialogPlus backDialog(Context contenxt,Holder holder,int gravity)
    {
        DialogPlus dialog = DialogPlus.newDialog(contenxt)
                .setContentHolder(holder)
                .setGravity(gravity)
                .setCancelable(true)
                .create();

        return dialog;
    }


}
