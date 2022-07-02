package com.lrc.store.controller;

import com.lrc.store.VO.CartVO;
import com.lrc.store.service.ICartService;
import com.lrc.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("cart")
public class CartController extends BaseController {
    @Autowired
    private ICartService cartService;

    @RequestMapping("add_product_cart")
    public JsonResult<Void> addToCart(Integer pid, Integer amount, HttpSession session) {
        cartService.addProductToCart(getUidFromSession(session), pid, amount, getUsernameFromSession(session));
        return new JsonResult<>(FINISH);
    }

    @RequestMapping({"","/"})
    public JsonResult<List<CartVO>> getVOByUid(Integer pid, Integer amount, HttpSession session) {
        List<CartVO> voList = cartService.getVOByUid(getUidFromSession(session));

        return new JsonResult<>(FINISH, voList);
    }

    @RequestMapping("{cid}/add_num")
    public JsonResult<Integer> addNum(@PathVariable("cid") Integer cid, HttpSession session) {
        Integer data = cartService.addNum(cid, getUidFromSession(session), getUsernameFromSession(session));
        return new JsonResult<>(FINISH, data);
    }

    @RequestMapping("{cid}/sub_num")
    public JsonResult<Integer> subNum(@PathVariable("cid") Integer cid, HttpSession session) {
        Integer data = cartService.subNum(cid, getUidFromSession(session), getUsernameFromSession(session));
        return new JsonResult<>(FINISH, data);
    }

    @RequestMapping({"list"})
    public JsonResult<List<CartVO>> getVOByCid(Integer[] cid, HttpSession session) {
        List<CartVO> data = cartService.getVOByCid(getUidFromSession(session), cid);
        return new JsonResult<>(FINISH, data);
    }
}
