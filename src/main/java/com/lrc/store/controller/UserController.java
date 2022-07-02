package com.lrc.store.controller;

import com.lrc.store.controller.ex.*;
import com.lrc.store.entity.User;
import com.lrc.store.service.IUserService;
import com.lrc.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
/*@RestController = @Controller + @ResponseBody，
@ResponseBody：表示此方法响应结果以json格式进行数据响应给前端*/
@RequestMapping("users")//将请求和处理请求的控制器方法关联起来，建立映射关系。
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;

    @RequestMapping("reg")
    public JsonResult<Void> reg(User user) {
        userService.reg(user);
        return new JsonResult<Void>(FINISH);
    }

    @RequestMapping("login")
    public JsonResult<User> login(String username, String password, HttpSession session) {
        User data = userService.login(username, password);

        //向session对象中完成数据的绑定(session全局)
        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());
        System.out.println("用户登录 -> uid = " + getUidFromSession(session) + "   username = " + getUsernameFromSession(session));
        return new JsonResult<User>(FINISH, data);
    }

    @RequestMapping("change_password")
    public JsonResult<Void> changePassword(String oldPassword, String newPassword, HttpSession session) {
        Integer uid = getUidFromSession(session);//用户id
        String username = getUsernameFromSession(session);//通过session获取username，作为修改者信息
        userService.changePassword(uid,username,oldPassword,newPassword);
        System.out.println("修改密码 -> old = " + oldPassword + "   new = " + newPassword);
        return new JsonResult<>(FINISH);
    }

    @RequestMapping("get_by_uid")
    public JsonResult<User> getByUid(HttpSession session) {
        User data = userService.getByUid(getUidFromSession(session));
        return new JsonResult<>(FINISH, data);
    }

    @RequestMapping("update_information")
    public JsonResult<Void> changeInformation(User user, HttpSession session) {
        //user对象中有四部分的内容 username,phone,email,gender
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeInformation(uid, username, user);
        return new JsonResult<>(FINISH);
    }

    //上传文件的最大大小 10M
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;
    //上传文件的类型
    public static final List<String> AVATAR_TYPE;
    static {
        AVATAR_TYPE = new ArrayList<>();
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }

    /**
     * MultipartFile是SpringMVC提供的接口，
     * 包装了获取文件类型的数据，可以接收任何类型的文件
     * @param session 会话
     * @param file
     * @return
     */
    @PostMapping("change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session, @RequestParam("file") MultipartFile file) {
        if(file.isEmpty()) {
            throw new FileEmptyException("文件为空");
        } else if(file.getSize() > AVATAR_MAX_SIZE) {
            throw new FileSizeException("文件大小超出限制");
        } else if(! AVATAR_TYPE.contains(file.getContentType())) {
            throw new FileTypeException("文件类型不支持");
        }

        //上传文件的路径，设定为upload
        String parent = session.getServletContext().getRealPath("upload");
        File dir = new File(parent);
        if(! dir.exists()) {//检测目录是否存在
            dir.mkdir();//不存在，则创建目录
        }

        //获取文件的名称，UUID来生成一个新的字符串作为文件名
        String originalFilename = file.getOriginalFilename();
        System.out.println("originalFilename" + originalFilename);

        //生成新的文件名
        int index = originalFilename.lastIndexOf(".");//获取文件名后缀
        String suffix = originalFilename.substring(index);
        String fileName = UUID.randomUUID().toString().toUpperCase() + suffix;

        //创建一个空文件
        File dest = new File(dir, fileName);
        //参数file中数据写入空文件dest
        try {
            file.transferTo(dest);
        } catch(FileStateException e) {
            throw new FileStateException("文件状态异常");
        } catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        }
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        String avatar = "\\upload\\" + fileName;//返回头像路径
        System.out.println("保存路径：" + parent + "\\" + fileName);
        userService.changeAvatar(uid, avatar, username);
        return new JsonResult<String>(FINISH, avatar);
    }
}
