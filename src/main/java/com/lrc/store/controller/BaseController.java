package com.lrc.store.controller;

import com.lrc.store.controller.ex.*;
import com.lrc.store.service.ex.*;
import com.lrc.store.util.JsonResult;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

//控制层类的基类
public class BaseController {
    //操作成功状态码
    public static final int FINISH = 200;

    //请求处理方法，这个方法返回值就是需要传递给前端的数据
    /*用于统一处理抛出的异常
    @ExceptionHandler自动将异常对象传递给此方法的参数列表
    若项目产生异常，会被直接拦截到此方法中，该方法作为请求处理方法，返回值直接给到前端
    */
    @ExceptionHandler({ServiceException.class, FileUploadException.class})
    public JsonResult<Void> handleException(Throwable e) {
        JsonResult<Void> result  = new JsonResult<>(e);

        if(e instanceof UsernameDuplicatedException) {
            result.setState(5000);
            result.setMessage("用户名已存在");
        } else if(e instanceof InsertException) {
            result.setState(5001);
            result.setMessage("注册时未知异常");
        } else if(e instanceof UserNotFoundException) {
            result.setState(5002);
            result.setMessage("该用户不存在");
        } else if(e instanceof PasswordNotMatchException) {
            result.setState(5003);
            result.setMessage("密码错误");
        } else if(e instanceof UpdateException) {
            result.setState(5004);
            result.setMessage("更新数据的未知异常");
        } else if(e instanceof AddressCountLimitException) {
            result.setState(5005);
            result.setMessage("收货地址超出上限");
        } else if(e instanceof AddressNotFoundException) {
            result.setState(5006);
            result.setMessage("用户收货地址未找到");
        } else if(e instanceof AccessDeniedException) {
            result.setState(5007);
            result.setMessage("收货地址数据非法访问");
        } else if(e instanceof UpdateException) {
            result.setState(5008);
            result.setMessage("删除数据的未知异常");
        } else if(e instanceof ProductNotFoundException) {
            result.setState(5009);
            result.setMessage("商品数据不存在");
        } else if(e instanceof CartNotFoundException) {
            result.setState(5010);
            result.setMessage("购物车数据不存在");
        } else if(e instanceof FileEmptyException) {
            result.setState(6000);
            result.setMessage("头像文件为空");
        } else if(e instanceof FileSizeException) {
            result.setState(6001);
            result.setMessage("文件大小不匹配");
        } else if(e instanceof FileTypeException) {
            result.setState(6002);
            result.setMessage("文件类型错误");
        } else if(e instanceof FileStateException) {
            result.setState(6003);
            result.setMessage("文件状态异常");
        } else if(e instanceof FileUploadIOException) {
            result.setState(6004);
            result.setMessage("头像文件读取异常");
        }
        return result;
    }

    /**
     * 获取session中的uid
     * @param session session对象
     * @return 当前登录用户uid的值
     */
    protected final Integer getUidFromSession(HttpSession session) {
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 获取session对象中的username
     * @param session session对象
     * @return 当前登录用户的username
     */
    protected final String getUsernameFromSession(HttpSession session) {
        return session.getAttribute("username").toString();
    }
}
