package com.hqf.a1056388105hqf.myfirstapplication.MyMusicService;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.hqf.a1056388105hqf.myfirstapplication.MyActivity.MyElement_java;
import com.hqf.a1056388105hqf.myfirstapplication.MyActivity.MyfirstAppMainActivity;

/**
 * Created by Administrator on 2017/5/12.
 */

//  换个方式，在Service种播放音乐
public class MyMusicService extends Service{
    private MyBinder mBinder = new MyBinder();              //  绑定参数

    /**
     * 初始化三个方法
     */
    //  初始化Service
    @Override
    public void onCreate() {

        super.onCreate();
        Toast.makeText(this, "My Service created", Toast.LENGTH_SHORT).show();
    }
    //  启动Service
    //  传入状态三种：播放，暂停，继续

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
                if(MyElement_java.MyMusic==null){
                    Toast.makeText(MyMusicService.this, "音乐文件不存在", Toast.LENGTH_SHORT).show();
                }else {
                    try {
                        if (MyElement_java.mediaPlayer != null) {
                            MyElement_java.mediaPlayer.stop();
                            MyElement_java.mediaPlayer.release();
                            MyElement_java.mediaPlayer = null;
                        }
                        MyElement_java.mediaPlayer = new MediaPlayer();
                        MyElement_java.mediaPlayer.setOnCompletionListener(new MyCompletionListener());
                        MyElement_java.mediaPlayer.setOnSeekCompleteListener(new MyOnseekcompleteListener());
                        MyElement_java.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                //  获取了歌曲的时长。单位毫秒
                                MyElement_java.mediaPlayer.start();
                            }
                        });
                        // 为mediaplayer赋值
                        MyElement_java.mediaPlayer.setDataSource(MyElement_java.MyMusic.getMusicPath());
                        MyElement_java.mediaPlayer.prepare();
                        MyElement_java.Song_AllLength=MyElement_java.mediaPlayer.getDuration();
                        MyElement_java.Songlength = getSongLength(MyElement_java.Song_AllLength);
                    }
                    catch (Exception e) {

                    }
                    }
                //  当Element类中的Music对象没有实例化，则显示错误
                return super.onStartCommand(intent, flags, startId);
    }

    //  销毁Service
    @Override
    public void onDestroy() {
        Toast.makeText(this,"音乐停止",Toast.LENGTH_SHORT).show();
        //  播放器制空，释放所有资源
        MyElement_java.mediaPlayer.stop();
        MyElement_java.mediaPlayer.release();
        MyElement_java.mediaPlayer=null;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    //  歌曲选择跳转到指定位置播放
    private class MyOnseekcompleteListener implements MediaPlayer.OnSeekCompleteListener {
        @Override
        public void onSeekComplete(MediaPlayer mp) {
            mp.start();
        }
    }

    //  当歌曲播放完毕后触发
    private class MyCompletionListener implements MediaPlayer.OnCompletionListener {
        @Override
        public void onCompletion(MediaPlayer mp) {
            MyElement_java.mediaPlayer.stop();
            MyElement_java.mediaPlayer.release();
            MyElement_java.mediaPlayer=null;
        }
    }
    public class MyBinder extends Binder {
        //  如果绑定成功，输出一个函数
        public void OutPrint() {
            Toast.makeText(MyMusicService.this, "绑定成功......", Toast.LENGTH_SHORT).show();
        }

        public void getMusicname() {
        }

        //  当主界面和该Service绑定成功后，可以调用里面的所有函数
        public void pause_Mediaplayer() { //  暂停播放器
            MyElement_java.mediaPlayer.pause();
        }

        public void stop_Mediaplayer() {  //  停止播放
            MyElement_java.mediaPlayer.stop();
            MyElement_java.mediaPlayer.release();
            MyElement_java.mediaPlayer = null;
        }

        public void continue_Mediaplayer() {  //  暂停播放之后才会被激活，触发后继续播放
            MyElement_java.mediaPlayer.start();
        }

        //  音乐跳转
        public void seek_Mediaplayer(int time) {  //  滑块滑动控制播放
            //  主界面传递数据，Service中跳转歌曲
            MyElement_java.mediaPlayer.seekTo(time);
        }

        public void change_song_Mediaplayer() {  //  音乐的播放格式：循环播放还是单曲播放之类的
        }

        public void Playmusicbypath(String path) {
            try {
                 //  清空当前的mediaplayer资源
                if (MyElement_java.mediaPlayer != null) {
                    MyElement_java.mediaPlayer.stop();
                    MyElement_java.mediaPlayer.release();
                    MyElement_java.mediaPlayer = null;
                }
                //  重新启动一个mediaplayer播放器
                MyElement_java.mediaPlayer = new MediaPlayer();
                MyElement_java.mediaPlayer.setOnCompletionListener(new MyCompletionListener());
                MyElement_java.mediaPlayer.setOnSeekCompleteListener(new MyOnseekcompleteListener());
                MyElement_java.mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        //  获取了歌曲的时长。单位毫秒
                        MyElement_java.mediaPlayer.start();
                    }
                });
                //  为mediaplayer设置一个数据源
                MyElement_java.mediaPlayer.setDataSource(path);
                //  mediaplayer准备
                MyElement_java.mediaPlayer.prepare();
                //  获取歌曲的时长
                MyElement_java.Song_AllLength=MyElement_java.mediaPlayer.getDuration();
                MyElement_java.Songlength = getSongLength(MyElement_java.Song_AllLength);
            }
            catch (Exception e) {
            }
        }
    }
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
}
