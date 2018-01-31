package com.hqf.a1056388105hqf.myfirstapplication.Mybean;

/**
 * Created by Administrator on 2017/10/11.
 */

/**
 * 用于存放歌曲的各种详细的信息
 */
public class musicBean {
    private String MusicName;//  音乐名
    private String MusicAuthor;//  音乐作者
    private String MusicAblum;//  音乐专辑
    private String MusicImage;//  音乐专辑图
    private String MusicPath;//  音乐的地址：目前暂时放于SD卡中,以后将放于服务器中

    /**
     * 各参数的属性
     * @return
     */
    public String getMusicName() {
        return MusicName;
    }

    public void setMusicName(String musicName) {
        MusicName = musicName;
    }

    public String getMusicAuthor() {
        return MusicAuthor;
    }

    public void setMusicAuthor(String musicAuthor) {
        MusicAuthor = musicAuthor;
    }

    public String getMusicAblum() {
        return MusicAblum;
    }

    public void setMusicAblum(String musicAblum) {
        MusicAblum = musicAblum;
    }

    public String getMusicImage() {
        return MusicImage;
    }

    public void setMusicImage(String musicImage) {
        MusicImage = musicImage;
    }

    public String getMusicPath() {
        return MusicPath;
    }

    public void setMusicPath(String musicPath) {
        MusicPath = musicPath;
    }

    //  建立构造函数
    public musicBean(String musicName, String musicAuthor,
                     String musicAblum, String musicImage, String musicPath) {
        MusicName = musicName;
        MusicAuthor = musicAuthor;
        MusicAblum = musicAblum;
        MusicImage = musicImage;
        MusicPath = musicPath;
    }
}
