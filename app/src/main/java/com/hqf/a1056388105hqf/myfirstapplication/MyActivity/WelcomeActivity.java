package com.hqf.a1056388105hqf.myfirstapplication.MyActivity;

import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.hqf.a1056388105hqf.myfirstapplication.R;

/**
 * Created by Administrator on 2017/5/17.
 */

public class WelcomeActivity extends AppCompatActivity{
    private ImageView imageView;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomelayout);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        imageView = (ImageView)findViewById(R.id.MySign);
        performAnimate(imageView,imageView.getWidth(),60);
        //  初始化一个动画脚本
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.animation_flash);
        //  为ImageView设置一个脚本
        imageView.startAnimation(animation);
        Handler handler = new Handler();
        //当计时结束,跳转至主界面
        //  当第一次使用这个音乐播放器时，进入登录注册界面。游客选择登录或者注册活着试用后才可以进入APP
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MyLoginFormActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_in,R.anim.push_out);
                WelcomeActivity.this.finish();
            }
        }, 5000);
    }
    //  自定义animation
    private void performAnimate(final View target, final int start, final int end) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(1, 100);

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            //持有一个IntEvaluator对象，方便下面估值的时候使用
            private IntEvaluator mEvaluator = new IntEvaluator();

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                //获得当前动画的进度值，整型，1-100之间
                int currentValue = (Integer)animator.getAnimatedValue();

                //计算当前进度占整个动画过程的比例，浮点型，0-1之间
                float fraction = currentValue / 100f;
                target.getLayoutParams().width = mEvaluator.evaluate(fraction, start, end);
                target.requestLayout();
            }
        });

        valueAnimator.setDuration(3000).start();
    }
}
