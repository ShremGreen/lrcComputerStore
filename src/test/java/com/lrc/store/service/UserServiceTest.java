package com.lrc.store.service;

import com.lrc.store.entity.User;
import com.lrc.store.service.ex.ServiceException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    private IUserService userService;

    @Test
    public void register() {
        try {
            User user = new User();
            user.setUsername("Mr.Smith");
            user.setPassword("mr.smith");

            userService.reg(user);
            System.out.println("finished！");
        } catch (ServiceException e) {
            //获取异常类并获取类的名称
            System.out.println(e.getClass().getSimpleName());
            //获取异常具体信息
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login() {
        User user = userService.login("YuanZiBo","yuanzibo");
        System.out.println(user);
    }

    @Test
    public void changePassword() {
        userService.changePassword(18, "管理员1", "lewiller", "lewiller1");
    }

    @Test
    public void getByUid(){
        System.out.println(userService.getByUid(5));
    }

    @Test
    public void changeInformation() {
        User user = new User();
        user.setPhone("15495784663");
        user.setEmail("asdfghjk@163.com");
        user.setGender(0);
        userService.changeInformation(3,"MrSimth",user);
    }

    @Test
    public void changeAvatar() {
        userService.changeAvatar(5, "D:/relax/happy.png", "管理员1");
    }
}
