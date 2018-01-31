package com.hqf.a1056388105hqf.myfirstapplication.MyMethod;

import android.view.View;

/**
 * Created by Administrator on 2017/5/19.
 */

public class MyViewPaper {
    //  设置一个视图的容器
    private View mTarget;

    /**
     * 构造函数
     * @param target
     */
    public MyViewPaper(View target) {
        mTarget = target;
    }

    /**
     * 获取视图的宽度属性
     * @return
     */
    public int getWidth() {
        return mTarget.getLayoutParams().width;
    }

    /**
     * 设置视图的宽度
     * @param width
     */
    public void setWidth(int width) {
        mTarget.getLayoutParams().width = width;
        mTarget.requestLayout();
    }
}
