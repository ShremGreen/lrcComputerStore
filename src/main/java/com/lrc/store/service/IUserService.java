package com.lrc.store.service;

import com.lrc.store.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

//用户模块 业务层 接口
//方便解耦
public interface IUserService {
    /***
     * 用户注册方法
     * @param user 用户数据类型
     */
    void reg(User user);

    /**
     * 用户登录方法
     * @param username 用户名
     * @param password 密码
     * @return 返回用户信息，没有则为null
     */
    User login(String username, String password);

    /**
     * 用户个人信息修改
     * @param uid 用户id
     * @param username 用户名
     * @param oldPw 原始密码
     * @param newPw 新密码
     */
    void changePassword(Integer uid, String username, String oldPw, String newPw);

    /**
     * 根据用户查询用户id
     * @param uid 用户id
     * @return 用户数据
     */
    User getByUid(Integer uid);

    /**
     * 更新用户的数据的操作
     * @param uid 用户的id
     * @param username 作为修改者
     * @param user 存放用户的更改数据
     */
    void changeInformation(Integer uid, String username, User user);

    /**
     * 修改用户的头像
     * @param uid 用户id
     * @param avatar 用户头像的路径
     * @param username 作为修改者
     */
    void changeAvatar(Integer uid, String avatar, String username);
}
