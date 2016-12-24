package com.wzm.jokephoto.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.wzm.framework.Tools.Tools;
import com.wzm.framework.base.BaseActivity;
import com.wzm.jokephoto.R;

import butterknife.Bind;

/**
 * Created by zhiyou007 on 2016/8/31.
 */
public class SplashActivity extends BaseActivity {

    @Bind(R.id.iv_splash)
    ImageView iv_splash;
    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override

    protected int getContentViewLayoutID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViewsAndEvents(Bundle savedInstanceState) {
        Animator animation = AnimatorInflater.loadAnimator(mContext, R.animator.splash_animator);
        animation.setTarget(iv_splash);
        animation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Tools.goActivity(mContext,MainActivity.class,null,R.anim.alpha_in,R.anim.alpha_out,true);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animation.start();
    }
}
