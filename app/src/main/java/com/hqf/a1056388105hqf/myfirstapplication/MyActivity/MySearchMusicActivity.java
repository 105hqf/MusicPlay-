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
 * Created by Administrator on 2017/6/21.
 */
public class MySearchMusicActivity extends AppCompatActivity {
    public Button CancelButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_searchmusic_in_database);
        //  窗体的沉浸式：
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //

        //  当点击取消按钮后，将该Activity注销
        CancelButton = (Button)findViewById(R.id.cancelbutton);
        CancelButton .setOnClickListener(new  MyClickListener());
    }
    class MyClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(v==CancelButton){
                startActivity(new Intent(MySearchMusicActivity.this,mysecondAcitvity.class));
                overridePendingTransition(R.anim.left_in,R.anim.right_out);
                finish();
            }
        }
    }
}
