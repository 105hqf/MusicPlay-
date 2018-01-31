package com.hqf.a1056388105hqf.myfirstapplication.MyActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hqf.a1056388105hqf.myfirstapplication.MyMethod.MediaBitmapChange;
import com.hqf.a1056388105hqf.myfirstapplication.R;

/**
 * Created by Administrator on 2017/10/12.
 */

//  登录界面。当我选择了手机登录时弹出
public class loginActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //  基本设置
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loginbyphonenumber);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //
        linearLayout = (LinearLayout) findViewById(R.id.loginByPhone_xmltop);
        linearLayout.setBackground(new BitmapDrawable(MediaBitmapChange.blurBitmap(
                BitmapFactory.decodeResource(getResources(),R.mipmap.item3),loginActivity.this)));

    }
}
