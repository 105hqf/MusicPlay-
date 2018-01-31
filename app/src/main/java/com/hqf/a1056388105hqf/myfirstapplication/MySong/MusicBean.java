package com.hqf.a1056388105hqf.myfirstapplication.MySong;
/**
 * Created by Administrator on 2017/5/12.
 */
import android.os.Parcel;
import android.os.Parcelable;


public class MusicBean implements Parcelable{
        private String musicName;                           //  歌曲的歌曲名
        private String musicPath;                           //  歌曲的路径

        private String musicDuration;                       //  歌曲时间的长度

        //  获取音乐的歌名
        public String getMusicName() {
                return musicName;
        }

        //  设置音乐的歌名
        public void setMusicName(String musicName) {
                this.musicName = musicName;
        }

        //  获取音乐的路径
        public String getMusicPath() {
                return musicPath;
        }

        //  设置音乐的路径
        public void setMusicPath(String musicPath) {
                this.musicPath = musicPath;
        }

        public String getMusicDuration() { return musicDuration; }

        public void setMusicDuration(String musicDuration) { this.musicDuration = musicDuration; }
        @Override
        public int describeContents() {
                return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(musicName);
                dest.writeString(musicPath);
                dest.writeString(musicDuration);
        }


        //  网上代码
        /**
         * 必须用 public static final 修饰符
         * 对象必须用 CREATOR
         */
        public static final Creator<MusicBean> CREATOR = new Creator<MusicBean>() {

                @Override
                public MusicBean createFromParcel(Parcel source) {
                        String name = source.readString();
                        String path = source.readString();
                        String duration = source.readString();

                        MusicBean music = new MusicBean();
                        music.setMusicName(name);
                        music.setMusicPath(path);
                        music.setMusicDuration(duration);

                        return music;
                }

                @Override
                public MusicBean[] newArray(int size) {
                        return new MusicBean[size];
                }

        };
}