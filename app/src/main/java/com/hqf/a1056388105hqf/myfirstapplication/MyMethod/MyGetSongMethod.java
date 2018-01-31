package com.hqf.a1056388105hqf.myfirstapplication.MyMethod;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

/**
 * Created by Administrator on 2017/5/12.
 */

public class MyGetSongMethod {

    /**
     * 获取歌曲文件的本地路径
     * @return
     */
    public String getMySongLocation() {
        return MySongLocation;
    }

    public void setMySongLocation(String mySongLocation) {
        MySongLocation = mySongLocation;
    }
    private String MySongLocation;

    /**
     * 获取歌曲的大小
     * @return
     */
    public String getMySongSize() {
        return MySongSize;
    }

    public void setMySongSize(String mySongSize) {
        MySongSize = mySongSize;
    }


    private String MySongSize;

    /**
     * 构造函数
     * @param mysonglocation
     * @param mysongsize
     */
    public MyGetSongMethod(String mysonglocation,String mysongsize)
    {
        setMySongLocation(mysonglocation);
        setMySongSize(mysongsize);
    }
}
