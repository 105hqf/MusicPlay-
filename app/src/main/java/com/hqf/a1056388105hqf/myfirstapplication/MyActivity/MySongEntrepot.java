package com.hqf.a1056388105hqf.myfirstapplication.MyActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.hqf.a1056388105hqf.myfirstapplication.R;

/**
 * Created by Administrator on 2017/7/4.
 */

public class MySongEntrepot extends AppCompatActivity {

    private Button entrepotbutton;         //  点击进入我的音乐仓库
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_mymusic_list);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        entrepotbutton = (Button)findViewById(R.id.EntrePotButton);
        entrepotbutton.setOnClickListener(new MyOnClickListener());
    }

    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //  跳转到viewpager界面
            Intent intent = new Intent();
            intent.setClass(MySongEntrepot.this,MySongControl.class);
            startActivity(intent);
            finish();
        }
    }
}
