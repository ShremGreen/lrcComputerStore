package com.lrc.store.controller;

import com.lrc.store.entity.Order;
import com.lrc.store.service.IOrderService;
import com.lrc.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("order")
public class OrderController extends BaseController {
    @Autowired
    private IOrderService orderService;

    @RequestMapping("create_order")
    public JsonResult<Order> createOrder(Integer aid, Integer[] cid, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        Order data = orderService.createOrder(aid, uid, username, cid);
        return new JsonResult<>(FINISH, data);
    }
}
