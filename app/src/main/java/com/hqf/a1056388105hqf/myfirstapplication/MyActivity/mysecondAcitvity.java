package com.hqf.a1056388105hqf.myfirstapplication.MyActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hqf.a1056388105hqf.myfirstapplication.MyMethod.MediaBitmapChange;
import com.hqf.a1056388105hqf.myfirstapplication.R;
import java.util.Timer;
import java.util.TimerTask;
/**
 * Created by Administrator on 2017/5/17.
 */
public class mysecondAcitvity extends Activity {
    private Timer timer;
    private Button Searchbutton;
    private ImageView[] imageViews=new ImageView[5];
    private ImageView imageview;
    private int imageid[];
    private int currentPosition=0;
    private ImageView imageview_back;
    private RadioGroup radiogroup;
    private RadioButton radiobutton;
    //  尝试
    private LinearLayout se;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //自定义标题
        /* create a full screen window */
        setContentView(R.layout.mysecondlayout);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        se = (LinearLayout)findViewById(R.id.second);
        se.setBackground(new BitmapDrawable(MediaBitmapChange.blurBitmap(
                BitmapFactory.decodeResource(getResources(),R.mipmap.item3),mysecondAcitvity.this)));
        imageid =new int[]{R.mipmap.radio_button_off,R.mipmap.radio_button_click};
        imageViews[0] = (ImageView) findViewById(R.id.click_1);
        imageViews[1] = (ImageView) findViewById(R.id.click_2);
        imageViews[2] = (ImageView) findViewById(R.id.click_3);
        imageViews[3] = (ImageView) findViewById(R.id.click_4);
        imageViews[4] = (ImageView) findViewById(R.id.click_5);
        imageview = (ImageView) findViewById(R.id.imageview);
        imageview_back = (ImageView) findViewById(R.id.imageview2);
        imageViews[0].setOnTouchListener(new mTouchListener());
        imageViews[1].setOnTouchListener(new mTouchListener());
        imageViews[2].setOnTouchListener(new mTouchListener());
        imageViews[3].setOnTouchListener(new mTouchListener());
        imageViews[4].setOnTouchListener(new mTouchListener());
        imageViews[0].setOnClickListener(new myclickListener());
        Searchbutton = (Button)findViewById(R.id.searchmusic_bar);
        Searchbutton.setOnClickListener(new myclickListener());
        imageview.setImageResource(R.mipmap.item5);
        imageViews[4].setImageResource(R.mipmap.radio_button_click);
        timer=new Timer();
        startAnimation();
    }
    TimerTask timertask = new TimerTask() {
        @Override
        public void run() {

        }
    };
    class myclickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(v==Searchbutton){
                //  切换的效果
                startActivity(new Intent(mysecondAcitvity.this,MySearchMusicActivity.class));
                overridePendingTransition(R.anim.push_in,R.anim.push_out);
            }
        }
    }
    class mTouchListener implements View.OnTouchListener{
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return false;
        }
    }
    public void ChangeClick_Image(int currentPosition){
        for(int i=0;i<5;i++)
        {
            if(i!=currentPosition){
                imageViews[i].setImageResource(imageid[0]);
            }
            else {
                imageViews[currentPosition].setImageResource(imageid[1]);
            }
        }
    }
    // 页面开启只有启动切换图片的动画
    public void startAnimation(){
        if(timer!=null){
            timertask=new TimerTask() {
                @Override
                public void run() {

                    //  利用UI线程
                    runOnUiThread(new Runnable() {
                        public void run() {
                            //  3秒切换一张图片
                            switch(currentPosition){
                                case 0:
                                    //  切换图片
                                    if(imageview.getVisibility()==View.INVISIBLE)
                                    {
                                        //  设置是否可视化
                                        imageview.setVisibility(View.VISIBLE);
                                        //  设置交替动画
                                        imageview_back.setAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.right_out));
                                        imageview.setAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.left_in));
                                        //  交替Imageview设置背景
                                        imageview.setImageResource(R.mipmap.item1);
                                        imageview_back.setVisibility(View.INVISIBLE);
                                    }
                                    else {
                                        imageview_back.setVisibility(View.VISIBLE);
                                        imageview.setAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.right_out));
                                        imageview_back.setAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.left_in));
                                        imageview_back.setImageResource(R.mipmap.item1);
                                        imageview.setVisibility(View.INVISIBLE);
                                    }

                                    break;
                                case 1:
                                    if(imageview.getVisibility()==View.INVISIBLE)
                                    {
                                        imageview.setVisibility(View.VISIBLE);
                                        imageview_back.setAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.right_out));
                                        imageview.setAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.left_in));
                                        imageview.setImageResource(R.mipmap.item2);
                                        imageview_back.setVisibility(View.INVISIBLE);
                                    }
                                    else {
                                        imageview_back.setVisibility(View.VISIBLE);
                                        imageview.setAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.right_out));
                                        imageview_back.setAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.left_in));
                                        imageview_back.setImageResource(R.mipmap.item2);
                                        imageview.setVisibility(View.INVISIBLE);
                                    }
                                    break;
                                case 2:
                                    if(imageview.getVisibility()==View.INVISIBLE)
                                    {
                                        imageview.setVisibility(View.VISIBLE);
                                        imageview_back.setAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.right_out));
                                        imageview.setAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.left_in));
                                        imageview.setImageResource(R.mipmap.item3);
                                        imageview_back.setVisibility(View.INVISIBLE);
                                    }
                                    else {
                                        imageview_back.setVisibility(View.VISIBLE);
                                        imageview.setAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.right_out));
                                        imageview_back.setAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.left_in));
                                        imageview_back.setImageResource(R.mipmap.item3);
                                        imageview.setVisibility(View.INVISIBLE);
                                    }
                                    break;
                                case 3:
                                    if(imageview.getVisibility()==View.INVISIBLE)
                                    {
                                        imageview.setVisibility(View.VISIBLE);
                                        imageview_back.setAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.right_out));
                                        imageview.setAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.left_in));
                                        imageview.setImageResource(R.mipmap.item4);
                                        imageview_back.setVisibility(View.INVISIBLE);
                                    }
                                    else {
                                        imageview_back.setVisibility(View.VISIBLE);
                                        imageview.setAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.right_out));
                                        imageview_back.setAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.left_in));
                                        imageview_back.setImageResource(R.mipmap.item4);
                                        imageview.setVisibility(View.INVISIBLE);
                                    }
                                    break;
                                case 4:
                                    if(imageview.getVisibility()==View.INVISIBLE)
                                    {
                                        imageview.setVisibility(View.VISIBLE);
                                        imageview_back.setAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.right_out));
                                        imageview.setAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.left_in));
                                        imageview.setImageResource(R.mipmap.item5);
                                        imageview_back.setVisibility(View.INVISIBLE);
                                    }
                                    else {
                                        imageview_back.setVisibility(View.VISIBLE);
                                        imageview.setAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.right_out));
                                        imageview_back.setAnimation(AnimationUtils.loadAnimation(getApplication(), R.anim.left_in));
                                        imageview_back.setImageResource(R.mipmap.item5);
                                        imageview.setVisibility(View.INVISIBLE);
                                    }
                                    break;
                            }
                            if(currentPosition==4){
                                currentPosition=0;
                            }
                            else currentPosition++;
                        }
                    });
                }
            };
            timer.schedule(timertask,1000,3000);
        }
        else {
            System.out.println("Timer控件失败....");
        }
    }
}
