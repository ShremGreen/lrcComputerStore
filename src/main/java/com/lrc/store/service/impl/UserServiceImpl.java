package com.lrc.store.service.impl;

import com.lrc.store.entity.User;
import com.lrc.store.mapper.UserMapper;
import com.lrc.store.service.IUserService;
import com.lrc.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import java.util.Date;
import java.util.UUID;

//用户模块 业务层 实现类
@Service//将当前类交给Spring管理，自动创建对象以及对象的维护
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {
        //获取用户名
        String username = user.getUsername();
        //判断用户名是否存在
        User result = userMapper.findByUsername(username);
        //异常处理
        if(result != null) {
            throw new UsernameDuplicatedException("该用户名已存在！");
        }

        /*
        密码加密：
        md5算法
        (盐值 + password + 盐值) ---- md5算法进行加密，连续加载三次
        盐值：随机的字符串
        */

        String pw = user.getPassword();
        //随机获取盐值
        String salt = UUID.randomUUID().toString().toUpperCase();
        //保存盐值
        user.setSalt(salt);
        //将密码和盐值作为整体进行加密
        String md5Password = getMD5Password(pw, salt);
        user.setPassword(md5Password);

        //补充其他信息
        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedUser(user.getUsername());
        user.setModifiedTime(date);

        //新用户注册(row == 1)
        Integer row = userMapper.insert(user);
        //异常处理
        if(row != 1) {
            throw new InsertException("未知异常 在 用户注册");
        }
    }

    @Override
    public User login(String username, String password) {
        //判断用户是否存在(不存在或者isDelete == 1)
        User result = userMapper.findByUsername(username);
        if(result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("该用户不存在");
        }

        //判断密码是否匹配
        //1.获取数据库相应的密码
        String truePassword = result.getPassword();
        //2.通过md5获取加密的密码
        String salt = result.getSalt();
        String inPassword = getMD5Password(password,salt);
        //判断
        if(!inPassword.equals(truePassword)) {
            throw new PasswordNotMatchException("密码错误");
        }

        //调用mapper层的findByUsername来查询用户数据
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());
        /*
        层与层的数据传输体量变小，
        后台的层与层之间调用，数据量越小效率越高。
        前后端传递信息时，数据量体量越小，响应速度越快。
        (调优)
        相当于变相完成了数据的中转和压缩
         */

        //返回用户信息
        return result;
    }


    /***
     * md5算法加密
     * @param pw 原始密码
     * @param salt 盐值
     * @return 加密后代码
     */
    private String getMD5Password(String pw,String salt) {
        for(int i = 0; i < 3; i ++) {
            pw = DigestUtils.md5DigestAsHex((salt + pw + salt).getBytes()).toUpperCase();
        }
        return pw;
    }

    /**
     * 修改密码
     * @param uid 获取uid
     * @param username 作为修改人信息
     * @param oldPw 老密码
     * @param newPw 新密码
     */
    @Override
    public void changePassword(Integer uid, String username, String oldPw, String newPw) {
        //查询用户信息
        User result = userMapper.findByUid(uid);
        if(result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }

        //比较原始密码
        String inputOldPw = getMD5Password(oldPw, result.getSalt());
        if(! result.getPassword().equals(inputOldPw)) {
            throw new PasswordNotMatchException("原始密码错误");
        }

        //设置新密码
        String inputNewPw = getMD5Password(newPw, result.getSalt());
        Integer rows = userMapper.updatePasswordByUid(uid, inputNewPw, username, new Date());
        if(rows != 1) {
            throw new UpdateException("未知异常 在 更新数据");
        }
    }

    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        if(result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());
        return user;
    }

    @Override
    public void changeInformation(Integer uid, String username, User user) {
        User result = userMapper.findByUid(uid);
        if(result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());

        //转到userMapper中，进而通过sql语句连接数据库，进行数据更新
        Integer row = userMapper.updateInformation(user);//传入user对象，通过
        if(row != 1) {
            throw new UpdateException("未知异常 在 更新数据");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        User result = userMapper.findByUid(uid);
        if(result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        Integer row = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
        if(row != 1) {
            throw new UpdateException("未知异常 在 更新用户头像");
        }
    }

}
