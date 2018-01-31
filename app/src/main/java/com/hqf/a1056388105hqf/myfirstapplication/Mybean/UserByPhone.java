package com.hqf.a1056388105hqf.myfirstapplication.Mybean;

/**
 * Created by Administrator on 2017/10/12.
 */


//  用户通过手机登录时使用该Bean
public class UserByPhone {


    //  属性
    private String PhoneNumber;    //  手机号码
    private String UserPassword;   //  用户密码
    private String UserName;       //  用户名
    private String UserImage;      //  用户头像
    private String UserTitle;      //  用户状态

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserImage() {
        return UserImage;
    }

    public void setUserImage(String userImage) {
        UserImage = userImage;
    }

    public String getUserTitle() {
        return UserTitle;
    }

    public void setUserTitle(String userTitle) {
        UserTitle = userTitle;
    }

    //  构造函数
    public UserByPhone(String phoneNumber, String userPassword, String userName,
                       String userImage, String userTitle) {
        PhoneNumber = phoneNumber;
        UserPassword = userPassword;
        UserName = userName;
        UserImage = userImage;
        UserTitle = userTitle;
    }
}
