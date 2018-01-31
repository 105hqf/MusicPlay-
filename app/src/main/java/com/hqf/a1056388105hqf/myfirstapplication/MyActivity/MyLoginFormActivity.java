package com.hqf.a1056388105hqf.myfirstapplication.MyActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hqf.a1056388105hqf.myfirstapplication.MyMethod.MediaBitmapChange;
import com.hqf.a1056388105hqf.myfirstapplication.R;

/**
 * Created by Administrator on 2017/10/11.
 */


//  登录界面
public class MyLoginFormActivity extends AppCompatActivity {

    private Button LoginForPhoneNumber;       //  登录按钮
    private Button SignUp;                    //  注册按钮
    private Button Tourist_use;               //  游客试用的登录按钮

    //  一个layout对象
    private LinearLayout loginTop;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_and_sign_up_layout);
        //  沉浸式界面设计
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //  虚化背景
        loginTop = (LinearLayout) findViewById(R.id.Login_Top);
        loginTop.setBackground(new BitmapDrawable(MediaBitmapChange.blurBitmap(
                BitmapFactory.decodeResource(getResources(),R.mipmap.bground),MyLoginFormActivity.this)));
        //  实例化
        LoginForPhoneNumber = (Button)findViewById(R.id.Login);
        SignUp = (Button)findViewById(R.id.SignUp);
        //  注册监听事件
        LoginForPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MyLoginFormActivity.this,loginActivity.class),1);
            }
        });
        //  注册监听事件
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //  只写入一个游客试用的功能，其余的后期加
        Tourist_use = (Button)findViewById(R.id.Tourist_use); //  获得实例化
        Tourist_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  点击后直接跳转主界面
                startActivity(new Intent(MyLoginFormActivity.this,MytestForMainActivity.class));
                //  关闭当前界面
                finish();
            }
        });

    }
}
