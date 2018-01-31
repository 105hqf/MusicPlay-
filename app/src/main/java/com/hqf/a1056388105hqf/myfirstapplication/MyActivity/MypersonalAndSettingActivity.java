package com.hqf.a1056388105hqf.myfirstapplication.MyActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hqf.a1056388105hqf.myfirstapplication.MyMethod.DBManager;
import com.hqf.a1056388105hqf.myfirstapplication.Mybean.UserBean;
import com.hqf.a1056388105hqf.myfirstapplication.R;

/**
 * Created by Administrator on 2017/6/21.
 */

public class MypersonalAndSettingActivity extends AppCompatActivity {

    //  Login Button
    private Button loginB;
    //  原本的设置，在我的信息上显示为我的用户等级：
    private TextView MyMessage;

    //  用户参数。只能初始化一个
    private UserBean NowUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_personal_setting_layout);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //  实例化
        MyMessage = (TextView)findViewById(R.id.userMassage);
        loginB = (Button)findViewById(R.id.Login);
        //  设置登录按钮的监听事件
        loginB.setOnClickListener(new View.OnClickListener() {       //  设置监听事件
            @Override
            public void onClick(View v) {
                /*
                final AlertDialog.Builder alertDialog =new AlertDialog.Builder(MypersonalAndSettingActivity.this);
                View view =View.inflate(MypersonalAndSettingActivity.this,R.layout.layout_login_sign_up,null);
                final EditText userName= (EditText)view.findViewById(R.id.UserNameEdittext);//  获取自定义界面中的用户名输入框
                userName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(hasFocus){
                            //  当当前的输入框获取焦点时，将输入框中的初始内容全部清空
                            userName.setText("");
                            // 输入框内内容清空
                        }
                        else ;
                    }
                });
                final EditText userPWD = (EditText)view.findViewById(R.id.UserPWDEdittext); //   获取自定义界面中的密码输入框
                //  同理
                userPWD.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if(hasFocus){
                            //  清空输入框内的初始内容
                            userPWD.setText("");
                        }
                    }
                });
                //  设置AlertDialog的基本设置
                alertDialog.setTitle("请输入用户名密码").setView(view)
                        .setPositiveButton("登录", new DialogInterface.OnClickListener() {
                            //  设置登录按钮，点击使用DBM中的数据库查询方法
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBManager dbManager = new DBManager(MypersonalAndSettingActivity.this);
                                NowUser = dbManager.Select_InDatabase(userName.getText().toString(),userPWD.getText().toString());
                                if(NowUser!=null){
                                    loginB.setText(NowUser.getUserName());//  当数据库中存在该用户，则将该用户名放在原本的
                                    //  loginButton中，将LoginButton设置为查看用户的按钮
                                    MyMessage.setText("    我的用户等级为："+NowUser.getUserLevel());
                                }
                                else {
                                    Toast.makeText(MypersonalAndSettingActivity.this, "用户名密码错误.....", Toast.LENGTH_SHORT).show();
                                }
                                //Toast.makeText(MypersonalAndSettingActivity.this, userName.getEditableText().toString()+"", Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ///  登录取消按钮
                        Toast.makeText(MypersonalAndSettingActivity.this, "用户名密码出错.......", Toast.LENGTH_SHORT).show();
                    }
                }).create().show();//  启动alertDialog*/
                //  启动登录界面
                startActivityForResult(new Intent(MypersonalAndSettingActivity.this,MyLoginFormActivity.class),1);
            }
        });
    }

    //  返回的参数
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //  当从登录界面返回或者注册成功后返回用户参数

    }
}
