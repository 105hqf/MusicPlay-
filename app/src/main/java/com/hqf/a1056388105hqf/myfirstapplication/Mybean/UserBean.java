package com.hqf.a1056388105hqf.myfirstapplication.Mybean;

/**
 * Created by Administrator on 2017/10/10.
 */

/**
 * 从数据库中读取的用户的参数
 */
public class UserBean {

    /**
     * 用户名
     */

    private String UserName;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    /**
     * 用户密码
     */

    private String UserPWD;
    public String getUserPWD() {
        return UserPWD;
    }

    public void setUserPWD(String userPWD) {
        UserPWD = userPWD;
    }

    /**
     * 用户的头像地址
     */
    private String UserTitleImage;
    public String getUserTitleImage() {
        return UserTitleImage;
    }

    public void setUserTitleImage(String userTitleImage) {
        UserTitleImage = userTitleImage;
    }

    /**
     * y用户等级
     */
    private int UserLevel;

    public int getUserLevel() {
        return UserLevel;
    }

    public void setUserLevel(int userLevel) {
        UserLevel = userLevel;
    }

    /**
     * 构造函数
     * @param userName
     * @param userPWD
     * @param userTitleImage
     * @param userLevel
     */

    public UserBean(String userName, String userPWD, String userTitleImage, int userLevel) {
        UserName = userName;
        UserPWD = userPWD;
        UserTitleImage = userTitleImage;
        UserLevel = userLevel;
    }
}
