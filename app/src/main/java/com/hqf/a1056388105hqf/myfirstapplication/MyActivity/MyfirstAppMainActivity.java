package com.hqf.a1056388105hqf.myfirstapplication.MyActivity;
/**
 * 胡情丰   毕业设计
 *
 *
 *
 *
 * 2017-9-11
 * 音乐播放改为Service播放之后，对界面的进度条和播放、暂停、停止进行同样的修改
 */

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.hqf.a1056388105hqf.myfirstapplication.MyMethod.DBManager;
import com.hqf.a1056388105hqf.myfirstapplication.MyMethod.DIYImageView;
import com.hqf.a1056388105hqf.myfirstapplication.MyMethod.MediaBitmapChange;
import com.hqf.a1056388105hqf.myfirstapplication.MyMusicService.MyMusicService;
import com.hqf.a1056388105hqf.myfirstapplication.MySong.MusicBean;
import com.hqf.a1056388105hqf.myfirstapplication.Mybean.musicBean;
import com.hqf.a1056388105hqf.myfirstapplication.R;
public class MyfirstAppMainActivity extends AppCompatActivity {

        public Timer timer;
        //  定义一个计时器
        private int NowTime=0;
        private Button PlayButton,NextSongButton,LastSongButton;              //  三个按钮
        private Button CycleButton;                                           //  循环歌曲设置的Button
        private TextView mySongPasstime;                                      //  歌曲的总时间
        private ImageView myProgressIconImage;                                //  进度条
        private RelativeLayout.LayoutParams layoutParamsImage;                //  获取进度图表的基本信息
        private RelativeLayout.LayoutParams layoutParamsSonglength;           //  获取歌曲时间的基本信息
        private TextView myProgressBar;                                       //  获取进度条的对象
        private double Speed;                                                 //  进度条每秒钟自动前行的距离
        private Intent myintent;                                              //  页面跳转
        private TextView mySong_allTime;                                      //  当前音乐的总时长
        private String MySongPath;                                            //  本地歌曲的路径
        private int rateByProAndSonglength;                                   //  进度条长度和歌曲的比率
        private int playstate=0;      //  播放状态标识，0代表刚刚打开页面，1代表正在播放（没有播放结束），-1表示播放结束
        private TextView PassTime_Progress;                                   //  进度条已经读取的部分
        private RelativeLayout.LayoutParams layoutParamsPass;                 //  layout参数，关于已放进度条
        private double temp=0.0;                                              //  实验：利用中间值来判断时候移动光标
        private int Musicstate=0;                                             //  当前播放状态：0暂停，1播放,-1为结束
        private String[] Temp;
        private String songnumber="-1";
        private boolean is_cycle=false;                                       //  是够循环播放歌曲的设置
        private TextView mMusicName;                                          //  歌曲名
        private ImageButton BackToMusicControl ;                              //  返回按钮，返回歌曲库
        private LinearLayout fourth;
        private TextView MusicTitle;                                          //  歌曲标题
        private TextView MusicSinger;                                         //  歌手名
        private DIYImageView SongImage;                                          //  歌曲海报

    //  权限
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    /**
     * 主界面生成
     * @param savedInstanceState
     */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myfirst_app_main);
            if (Build.VERSION.SDK_INT >= 21) {
                View decorView = getWindow().getDecorView();
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        ///StatusBarCompat.setStatusBarColor(this, color, lightStatusBar);
        verifyStoragePermissions(MyfirstAppMainActivity.this);
        //  Button设置监听
        //  获取个各个控件的ID
        PlayButton = (Button) findViewById(R.id.Playbutton);
        NextSongButton = (Button) findViewById(R.id.NextSongbutton);
        LastSongButton=(Button)findViewById(R.id.LastSongbutton);
            //  返回按钮，歌名菜单
        mMusicName = (TextView)findViewById(R.id.ShowSongName);
        BackToMusicControl = (ImageButton)findViewById(R.id.first_backforward);
            BackToMusicControl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //  为返回按钮添加监听事件。返回到歌曲库
                    //  开启一个新的界面，接受该界面回传的值
                    Bundle bundlemessage = new Bundle();
                    bundlemessage.putInt("position",MyElement_java.Now_playSnumber);
                    bundlemessage.putString("returnflag", "2");
                    myintent=new Intent();
                    myintent.putExtras(bundlemessage);
                    myintent.setClass(MyfirstAppMainActivity.this,MySongControl.class);
                    startActivityForResult(myintent, 1);
                    overridePendingTransition(R.anim.push_in,R.anim.push_out);
                }
            });
        //  各种初始化
        MusicTitle = (TextView)findViewById(R.id.ShowSongName);//  歌曲标题
        MusicSinger = (TextView)findViewById(R.id.Singer);//  歌手
        SongImage = (DIYImageView) findViewById(R.id.mImageView);//  歌曲海报
        PlayButton.setOnClickListener(new mClickListener());//  播放按钮
        NextSongButton.setOnClickListener(new mClickListener());//  下一首歌按钮
        LastSongButton.setOnClickListener(new mClickListener());//  上一首歌按钮
        CycleButton = (Button) findViewById(R.id.cyclebutton);//  播放模式按钮；
        CycleButton .setOnClickListener(new mClickListener());
        myProgressIconImage=(ImageView)findViewById(R.id.myProgressIcon);
        myProgressIconImage.setOnTouchListener(new myTouchListener());
        mySongPasstime = (TextView) findViewById(R.id.MySongNowTime);
        myProgressBar = (TextView)findViewById(R.id.ProgressBar);
        mySong_allTime = (TextView)findViewById(R.id.MySongTime);
        PassTime_Progress = (TextView)findViewById(R.id.PassPart);
        //  获取myProgressIconImage的layout属性，将其赋值给layoutParams
        layoutParamsImage = (RelativeLayout.LayoutParams)myProgressIconImage.getLayoutParams();
        layoutParamsSonglength = (RelativeLayout.LayoutParams)myProgressBar.getLayoutParams();
        layoutParamsPass = (RelativeLayout.LayoutParams)PassTime_Progress.getLayoutParams();
        //  将基本需要的对象置空
        timer=null;
        timerTask=null;
        MyElement_java.mediaPlayer=null;
        Temp = new String[2];
        Intent bindIntent=new Intent(MyfirstAppMainActivity.this, MyMusicService.class);
        startService(bindIntent);
            DBManager dbManager = new DBManager(this);
            musicBean music=dbManager.getMusicInfor("/sdcard/C.mp3");
            MusicSinger.setText(music.getMusicAuthor());
            MusicTitle.setText(music.getMusicName());
            if(Integer.parseInt(music.getMusicImage())==1){//  在歌曲海报处显示歌曲海报
                    SongImage.setImageResource(R.mipmap.sc);
                    SongImage.setType(DIYImageView.TYPE_CIRCLE);
                    fourth = (LinearLayout)findViewById(R.id.fourth);
                    //  旋转的动画设计
                    Animation operatingAnim = AnimationUtils.loadAnimation(this, R.anim.circle);
                    LinearInterpolator lin = new LinearInterpolator();
                    operatingAnim.setInterpolator(lin);
                    SongImage.startAnimation(operatingAnim);//  歌曲海报进行旋转效果
                   fourth.setBackground(new BitmapDrawable(MediaBitmapChange.blurBitmap(
                            BitmapFactory.decodeResource(getResources(),R.mipmap.sc),MyfirstAppMainActivity.this)));
                 }
            Toast.makeText(this, MyElement_java.MyMusic.getMusicPath()+"", Toast.LENGTH_SHORT).show();

        //  和MusicService建立数据绑定
        bindService(bindIntent, connection, BIND_AUTO_CREATE);
    }
    private MyMusicService.MyBinder myBinder;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
        //
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (MyMusicService.MyBinder) service;
            //  如果歌曲资源存在，显示歌曲的时长标记
            player();
            mySong_allTime.setText(MyElement_java.Songlength);
            Is_returnSongPath(getIntent());
        }
    };
    /**
     * Checks if the app has permission to write to device storage
     *
     * If the app does not has permission then the user will be prompted to grant permissions
     *
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
    /**
     * 按钮监听
     */
    class mClickListener implements View.OnClickListener {
        public void onClick(View v){
            //  歌曲播放按钮:点击后转换为暂停按钮，当为暂停按钮时被点击，触发相应时间，在转换为播放按钮
                if(v==PlayButton)
                {
                    //  计时事件触发‘
                    if(MyElement_java.mediaPlayer==null&&playstate == 0){
                        PlayButton.setEnabled(false);
                    }
                    else if(MyElement_java.mediaPlayer!=null&&playstate == 1){          //  当当前mediaplayer存在对象，且不为null,playstate参数1，表示正在播放，则执行
                        //  当当前按钮的Text为播放，则歌曲继续播放
                           if(Musicstate == 0){
                               MyElement_java. mediaPlayer.start();
                               startTimer();
                               PlayButton.setBackgroundResource(R.mipmap.musicpause);
                               Musicstate = 1;
                           }
                           //  当当前的button Text为暂停，则将歌曲暂停
                           else {
                               stopTimer();
                               myBinder.pause_Mediaplayer();
                               PlayButton.setBackgroundResource(R.mipmap.musicplay);
                               Musicstate = 0;
                           }
                    }
                    else if(MyElement_java.mediaPlayer==null&&playstate == -1){
                        //  当当前mediapl==null并且是刚刚播放完毕，则这时候的按钮点击事件为重新播放
                        Is_PlayMusic(0);
                        myBinder.Playmusicbypath(MyElement_java.mylist.get(MyElement_java.Now_playSnumber).getMusicPath());
                        mySong_allTime.setText(MyElement_java.Songlength);
                        playstate=1;
                        Musicstate = -1;
                    }
                }
            //  下一首歌
            else if(v == NextSongButton){
                    if(MyElement_java.mylist!=null){
                        //
                        //  当前歌曲的位置播放下一首，依次循环
                        if(MyElement_java.Now_playSnumber==MyElement_java.mylist.size()-1) {
                            MyElement_java.Now_playSnumber=0;
                            Is_PlayMusic(1);
                            myBinder.Playmusicbypath(MyElement_java.mylist.get(MyElement_java.Now_playSnumber).getMusicPath());
                            mySong_allTime.setText(MyElement_java.Songlength);
                        }
                        else {
                            ++MyElement_java.Now_playSnumber;
                            Is_PlayMusic(1);
                            myBinder.Playmusicbypath(MyElement_java.mylist.get(MyElement_java.Now_playSnumber).getMusicPath());
                            mySong_allTime.setText(MyElement_java.Songlength);
                        }

                    }
                    else
                        Toast.makeText(MyfirstAppMainActivity.this, "当前没有歌曲...", Toast.LENGTH_SHORT).show();
            }
            //  上一首歌
            else if(v==LastSongButton) {
                    if (MyElement_java.mylist != null) {
                        //  当前歌曲的位置播放上一首，依次循环
                        if (MyElement_java.Now_playSnumber == 0) {
                            MyElement_java.Now_playSnumber = MyElement_java.mylist.size() - 1;
                            Is_PlayMusic(1);
                            myBinder.Playmusicbypath(MyElement_java.mylist.get(MyElement_java.Now_playSnumber).getMusicPath());
                            mySong_allTime.setText(MyElement_java.Songlength);
                        } else {
                            --MyElement_java.Now_playSnumber;
                            Is_PlayMusic(1);
                            myBinder.Playmusicbypath(MyElement_java.mylist.get(MyElement_java.Now_playSnumber).getMusicPath());
                            mySong_allTime.setText(MyElement_java.Songlength);
                        }
                        //  重新设置音乐播放器
                    } else
                        Toast.makeText(MyfirstAppMainActivity.this, "当前没有歌曲...", Toast.LENGTH_SHORT).show();
                }
                //  当点击的是歌曲播放状态的Button
            else if(v==CycleButton){
        //  点击后，是执行循环播放
        is_cycle=true;
        Toast.makeText(MyfirstAppMainActivity.this, "循环播放...", Toast.LENGTH_SHORT).show();
        //  之后要修改为所有状态
    }
}
    }
    //  startActivityResult函数执行后，当新页面关闭后执行该函数
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==1&&resultCode==1){
            myBinder.Playmusicbypath(MyElement_java.MyMusic.getMusicPath());
            Is_returnSongPath(data);
            mySong_allTime.setText(MyElement_java.Songlength);
        }

    }
    private void Is_returnSongPath(Intent data){
        //  返回的参数是经过一个‘-’符号相隔开的两个信息：一个是第几首歌，然后是歌曲的地址
        Temp = data.getStringExtra("data").split("_");
        //  获取歌曲的地址
        MySongPath=Temp[0];
        //获取歌曲的第几首 //
        songnumber = Temp[1];
        MyElement_java.Now_playSnumber = Integer.parseInt(songnumber);
        player();
        playstate = 1;
        Musicstate = 1;
    }
    private void player(){
        mySongPasstime.setText("00:00");
        ClearTimer();
        layoutParamsImage.leftMargin=0;
        myProgressIconImage.setLayoutParams(layoutParamsImage);
        layoutParamsPass.width = 0;
        PassTime_Progress.setLayoutParams(layoutParamsPass);
        rateByProAndSonglength = MyElement_java.Song_AllLength/(myProgressBar.getWidth()-15);
        startTimer();
        PlayButton.setEnabled(true);
        PlayButton.setBackgroundResource(R.mipmap.musicpause);
        Musicstate = 1;
    }
    private void Is_PlayMusic(int flag){
        //  当前歌曲为从页面选择歌曲后的播放
            if(flag==1) {//  选择歌曲播放
                if(MyElement_java.mediaPlayer!=null){
                    //  有返回参数时，清空mediaPlayer的资源，重新赋予资源
                    MyElement_java.mediaPlayer.stop();
                    MyElement_java.mediaPlayer.release();
                    MyElement_java.mediaPlayer=null;
                }
                mySongPasstime.setText("00:00");
                ClearTimer();
                layoutParamsImage.leftMargin=0;
                myProgressIconImage.setLayoutParams(layoutParamsImage);
                layoutParamsPass.width = 0;
                PassTime_Progress.setLayoutParams(layoutParamsPass);
                rateByProAndSonglength = MyElement_java.Song_AllLength/(myProgressBar.getWidth()+60);
                startTimer();
                PlayButton.setEnabled(true);
                PlayButton.setBackgroundResource(R.mipmap.musicpause);
                Musicstate = 1;
            }

            //  该处为音乐播放结束，重新播放
            else
            {
                mySongPasstime.setText("00:00");
                ClearTimer();
                layoutParamsImage.leftMargin=0;
                myProgressIconImage.setLayoutParams(layoutParamsImage);
                layoutParamsPass.width = 0;
                PassTime_Progress.setLayoutParams(layoutParamsPass);
                startTimer();
                PlayButton.setEnabled(true);
                PlayButton.setBackgroundResource(R.mipmap.musicpause);
                Musicstate = 1;
                Toast.makeText(MyfirstAppMainActivity.this, "else", Toast.LENGTH_SHORT).show();
            }
    }
    /**
     * 触屏事件
     * 光标随着手机的移动而移动位置，最大值不超过ProgressBar的长度
     */
    private class myTouchListener implements View.OnTouchListener {
        private float Event_X,NowEvent_X;//  当前点击的位置，目前移动到的位置
        private float Chang_Overflow;         //  float转int 多余的float存入
        private String N_time;
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(MyElement_java.mediaPlayer!=null) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {              //  接收到手指点下事件
                    //  获取当前的Icon的位置
                    Event_X = event.getX();
                    Chang_Overflow = Event_X - (int) Event_X;
                    myBinder.pause_Mediaplayer();
                }
                if (event.getAction() == MotionEvent.ACTION_MOVE) {              //  光标随着手指移动，但是最远不超过当前音乐的长度
                    //  手指移动，光标的MarginLeft增加相应的距离
                    NowEvent_X = (int) event.getX();
                    if (layoutParamsImage.leftMargin >= (myProgressBar.getWidth() - 25))            //  当光标移动到进度条最右边以外时，不再进行移动
                        layoutParamsImage.leftMargin = myProgressBar.getWidth() - 27;
                    else {
                        layoutParamsImage.leftMargin += NowEvent_X - Event_X;         //  增加手指移动的增量，仅仅从X轴移动
                        if (layoutParamsImage.leftMargin < 0)
                            layoutParamsImage.leftMargin = 0;  //  当光标移动到进度条的最左边时，停止移动
                    }
                    //  将新的layout参数赋值给光标、
                    myProgressIconImage.setLayoutParams(layoutParamsImage);
                    //  修改音乐当前的时间
                    if (layoutParamsImage.leftMargin == 0)
                        mySongPasstime.setText("00:00");                     //  当没有进行移动时，置空时间
                    else {
                        N_time = getSongLength(layoutParamsImage.leftMargin * rateByProAndSonglength);
                        mySongPasstime.setText(N_time);
                    }
                    stopTimer();
                    NowTime = layoutParamsImage.leftMargin * rateByProAndSonglength / 1000;
                    layoutParamsPass.width = layoutParamsImage.leftMargin;
                    PassTime_Progress.setLayoutParams(layoutParamsPass);
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    //  当手指离开触屏时，触发事件
                    myBinder.seek_Mediaplayer(layoutParamsImage.leftMargin * rateByProAndSonglength);
                    startTimer();
                }
                return true;
            }
            else return false;
        }
    }

    //**
    //  UI线程来编写计时器的功能，用来表示歌曲播放的时间
    //  歌曲播放按钮点击后触发事件
        TimerTask timerTask = new TimerTask(){
            public void run() {

                runOnUiThread(new Runnable() {
                    public void run() {
                        NowTime = NowTime + 1;
                        //  歌曲现在默认不超过10分钟
                        String PassTime = "0" + NowTime / 60 + ":" + (NowTime % 60);
                        mySongPasstime.setText(PassTime);
                    }// UI thread
                });
            }
        };
    //  timer启动和关闭方法
    //  利用mediaPlayer.getDuration()获取的文件的时长获取歌曲播放的长度，默认四舍五入取整
    public String getSongLength(int getlength)        //  getDuration返回的参数是以毫秒为单位
    {
        if(getlength!=0){
            String returnSonglength;
            int length=getlength/1000;
            --length;
            int minute=length/60;                 //  记录分钟
            int second=length-minute*60;          //  记录秒钟
            if(second<10)
                returnSonglength="0"+minute+":"+second+"0";
            else returnSonglength="0"+minute+":"+second;
            return returnSonglength;
        }
        else return null;
    }
    /**
     * timer开启
     */
    public void startTimer(){
        if(timer==null)
        {
            timer=new Timer();
        }
        //  重新初始化一个timer的timerTask事件
        if(timerTask==null)
        {
            timerTask = new TimerTask(){
                public void run() {

                    runOnUiThread(new Runnable() {
                        public void run() {
                            String PassTime;
                            Speed=(double)(myProgressBar.getWidth()-15)/((double)MyElement_java.Song_AllLength/1000);
                            NowTime = NowTime + 1;
                            //Toast.makeText(MyfirstAppMainActivity.this, NowTime+"", Toast.LENGTH_SHORT).show();
                            //  歌曲现在默认不超过10分钟
                            if(NowTime<MyElement_java.Song_AllLength/1000){
                                if(NowTime%60<10)
                                {
                                    PassTime = "0" + NowTime / 60 + ":" +"0" + (NowTime % 60);
                                    mySongPasstime.setText(PassTime);
                                }
                                else {
                                    PassTime = "0" + NowTime / 60 + ":" + (NowTime % 60);
                                    mySongPasstime.setText(PassTime);
                                }
                                if(layoutParamsImage.leftMargin<=(myProgressBar.getWidth()-25)){
                                    //  timer进行的同时，移动ImageView
                                    //  改变ImageView的Layout_marginLeft
                                    //  为了使进度条精确点移动，添加一个Temp参数，和速度相加，当超过1.00时
                                    //  进度条移动一个单位，并Temp减去1.0
                                    temp=temp+Speed;
                                    int count=0;
                                    if(temp>=1.0){
                                        {
                                            while(temp>1.0){temp=temp-1.0;count++;}
                                            layoutParamsImage.leftMargin=(int)(layoutParamsImage.leftMargin + count);
                                            //  已经播放的部分设置其他的颜色
                                            layoutParamsPass.width = layoutParamsImage.leftMargin;
                                            PassTime_Progress.setLayoutParams(layoutParamsPass);
                                            myProgressIconImage.setLayoutParams(layoutParamsImage);
                                        }
                                    }
                                }
                            }
                            else {
                                PlayButton.setBackgroundResource(R.mipmap.musicplay);
                                Musicstate = -1;
                                myBinder.stop_Mediaplayer();
                            }
                        }// UI thread
                    });
                }
            };
        }
        if(timer!=null&&timerTask!=null){
            timer.schedule(timerTask, 1000, 1000);
        }
    }
    /**
     * timer关闭
     *
     */
    public void stopTimer(){
        //  在线程中对timer计时器的关闭
        if(timer!=null)
        {
            timer.cancel();
            timer=null;
        }
        if(timerTask!=null){
            //  注销timerTask事件
            timerTask.cancel();
            //  timerTask质空
            timerTask=null;
        }
    }
    public void ClearTimer(){
        //  在线程中对timer计时器的关闭
        if(timer!=null)
        {
            timer.cancel();
            timer=null;
        }
        if(timerTask!=null){
            //  注销timerTask事件
            timerTask.cancel();
            //  timerTask质空
            timerTask=null;
        }
        //  计时器清零
        NowTime=0;
    }
    //  利用handler在辅助线程中赋值
    private Handler hander = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Toast.makeText(MyfirstAppMainActivity.this, msg.getData().getString("data"), Toast.LENGTH_SHORT).show();
        }
    };
}
