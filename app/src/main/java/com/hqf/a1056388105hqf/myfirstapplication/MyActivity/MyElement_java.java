package com.hqf.a1056388105hqf.myfirstapplication.MyActivity;

/**
 * Created by Administrator on 2017/7/8.
 */


//  可以在里面设计标记，作为页面切换的后台服务

import android.media.MediaPlayer;
import com.hqf.a1056388105hqf.myfirstapplication.MySong.MusicBean;

import java.util.ArrayList;

/**
 * 接受各Activity之间的参数传递的媒介
        */

public class MyElement_java {
    //  标记：音乐参数.设置为静态变量，可以随时操控
    public static MusicBean MyMusic=null;


    //  当前所含有的歌曲总数。当MySongControl中的adpter数据更新后才会触发数据的刷新
    public static ArrayList< MusicBean> mylist = new ArrayList<>();             //  用于存储歌曲的地址

    //  获取歌曲的时间长度
    public static String Songlength;

    //  mediaplayer播放实例
    public static MediaPlayer mediaPlayer;

    //  mediaplayer的时长参数。单位毫秒
    public static int Song_AllLength;

    //  mediaplayer在歌单中是否存在正在播放的音乐
    public static int Now_playSnumber;
}
