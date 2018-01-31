package com.hqf.a1056388105hqf.myfirstapplication.MyMethod;

import android.app.Activity;

/**
 * Created by Administrator on 2017/10/12.
 */


//  Activity的栈堆
public class ActivityStack {

    public int top = -1;      //   栈顶指针,置栈空

    public Activity []MyActivityStack;   //  Activity的栈堆

    public int MAXSIZE=20;//  栈的最大存储量为20个

    //  栈的基本操作

    /**
     * 出栈
     */
    public void Pop(Activity outActivity){

        if(top > -1){
            //  出栈，将出栈元素赋值于传入的形参中
            outActivity = MyActivityStack[top];//  出栈之后显示该Activity
            --top;  //  栈顶指针-1
        }
    }

    /**
     * 入栈操作
     * @param pushActivity
     */
    public boolean Push(Activity pushActivity){
        //  入栈
        if(top == MAXSIZE-1){
            //  当栈满，返回错误
            return false;
        }
        else {
            //  入栈
            ++top;
            MyActivityStack[top] = pushActivity;
            return true;
        }
    }

    //  初始栈
    public void InitStack(){
        top=-1;   //栈顶指针指向-1，置空栈
    }
}
