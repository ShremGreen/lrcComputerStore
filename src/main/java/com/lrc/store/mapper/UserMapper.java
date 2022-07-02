package com.lrc.store.mapper;

import com.lrc.store.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

//用户模块的持久化模型
public interface UserMapper {
    /**
     * 插入用户注册信息
     * @param user 用户
     * @return 受影响的行数
     */
    Integer insert(User user);

    /**
     * 根据用户名查询用户信息
     * @param username 用户名
     * @return 查询到则返回用户信息，没查到则放回null
     */
    User findByUsername(String username);

    /**
     * 根据用户uid修改用户密码
     * @param uid 用户id
     * @param password 用户输入的新密码
     * @param modifiedUser 修改人信息
     * @param modifiedTime 修改时间信息
     * @return 受影响的行数
     */
    Integer updatePasswordByUid(Integer uid, String password, String modifiedUser, Date modifiedTime);

    /**
     * 根据用户id查询用户数据
     * @param uid
     * @return 如果找到则返回对象反之返回null值
     */
    User findByUid(Integer uid);

    /**
     * 更新用户信息
     * @param user User对象用于存储输入的数据
     * @return 受影响的行数
     */
    Integer updateInformation(User user);

    /**
     * 根据uid修改用户的头像
     * @param uid
     * @param avatar
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    Integer updateAvatarByUid(@Param("uid") Integer uid,
                              @Param("avatar") String avatar,
                              @Param("modifiedUser") String modifiedUser,
                              @Param("modifiedTime") Date modifiedTime);
}
